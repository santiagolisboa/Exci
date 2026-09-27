"use server";

import { revalidatePath } from "next/cache";
import { redirect } from "next/navigation";

import {
  authErrorCode,
  authMessage,
  normalizeDisplayName,
  normalizeEmail,
  validateDisplayName,
  validateEmail,
  validateNewPassword,
  type AuthErrorLike,
  type AuthFormState,
} from "@/lib/auth-validation";
import { createClient } from "@/lib/supabase/server";

function technicalError(context: string, error: AuthErrorLike) {
  console.error(`[auth:${context}]`, { code: error.code, status: error.status });
}

function siteOrigin() {
  try {
    return new URL(process.env.NEXT_PUBLIC_SITE_URL ?? "").origin;
  } catch {
    return null;
  }
}

export async function login(
  _previousState: AuthFormState,
  formData: FormData,
): Promise<AuthFormState> {
  const email = normalizeEmail(formData.get("email"));
  const password = formData.get("password");
  const fieldErrors: AuthFormState["fieldErrors"] = {};
  const emailError = validateEmail(email);
  if (emailError) fieldErrors.email = emailError;
  if (typeof password !== "string" || password.length === 0) {
    fieldErrors.password = "Saisissez votre mot de passe.";
  }
  if (Object.keys(fieldErrors).length) return { status: "error", fieldErrors };

  try {
    const supabase = await createClient();
    const { error } = await supabase.auth.signInWithPassword({
      email,
      password: password as string,
    });
    if (error) {
      const code = authErrorCode(error, "login");
      technicalError("login", error);
      return { status: "error", message: authMessage(code) };
    }
  } catch (error) {
    technicalError("login-client", error as AuthErrorLike);
    return { status: "error", message: authMessage("network_error") };
  }

  revalidatePath("/", "layout");
  redirect("/");
}

export async function signUp(
  _previousState: AuthFormState,
  formData: FormData,
): Promise<AuthFormState> {
  const displayName = normalizeDisplayName(formData.get("displayName"));
  const email = normalizeEmail(formData.get("email"));
  const password = formData.get("password");
  const fieldErrors: AuthFormState["fieldErrors"] = {};
  const displayNameError = validateDisplayName(displayName);
  const emailError = validateEmail(email);
  const passwordError = validateNewPassword(password);
  if (displayNameError) fieldErrors.displayName = displayNameError;
  if (emailError) fieldErrors.email = emailError;
  if (passwordError) fieldErrors.password = passwordError;
  if (Object.keys(fieldErrors).length) return { status: "error", fieldErrors };

  const origin = siteOrigin();
  if (!origin) {
    technicalError("signup-config", { code: "missing_site_url" });
    return { status: "error", message: authMessage("configuration_error") };
  }

  let hasSession = false;
  try {
    const supabase = await createClient();
    const { data, error } = await supabase.auth.signUp({
      email,
      password: password as string,
      options: {
        data: { display_name: displayName },
        emailRedirectTo: `${origin}/auth/confirm`,
      },
    });
    if (error) {
      const code = authErrorCode(error, "signup");
      technicalError("signup", error);
      return { status: "error", message: authMessage(code) };
    }
    if (data.user?.identities?.length === 0) {
      return { status: "error", message: authMessage("signup_unavailable") };
    }
    hasSession = Boolean(data.session);
    if (data.session && data.user) {
      const { error: profileError } = await supabase.from("profiles").upsert({
        id: data.user.id,
        display_name: displayName,
        updated_at: new Date().toISOString(),
      });
      if (profileError) technicalError("signup-profile", profileError);
    }
  } catch (error) {
    technicalError("signup-client", error as AuthErrorLike);
    return { status: "error", message: authMessage("network_error") };
  }

  if (hasSession) {
    revalidatePath("/", "layout");
    redirect("/");
  }
  redirect("/auth/sign-up?status=check_email");
}

export async function requestPasswordReset(
  _previousState: AuthFormState,
  formData: FormData,
): Promise<AuthFormState> {
  const email = normalizeEmail(formData.get("email"));
  const emailError = validateEmail(email);
  if (emailError) return { status: "error", fieldErrors: { email: emailError } };

  const origin = siteOrigin();
  if (!origin) {
    technicalError("password-reset-config", { code: "missing_site_url" });
    return { status: "error", message: authMessage("configuration_error") };
  }

  try {
    const supabase = await createClient();
    const { error } = await supabase.auth.resetPasswordForEmail(email, {
      redirectTo: `${origin}/auth/recovery`,
    });
    if (error) {
      const code = authErrorCode(error, "signup");
      technicalError("password-reset", error);
      if (code === "rate_limited") return { status: "error", message: authMessage(code) };
      return { status: "error", message: authMessage("recovery_failed") };
    }
  } catch (error) {
    technicalError("password-reset-client", error as AuthErrorLike);
    return { status: "error", message: authMessage("network_error") };
  }

  redirect("/auth/forgot-password?status=sent");
}

export async function confirmPasswordRecovery(formData: FormData) {
  const tokenHash = formData.get("tokenHash");
  const type = formData.get("type");
  const validTokenHash =
    typeof tokenHash === "string" &&
    tokenHash.length >= 20 &&
    tokenHash.length <= 512 &&
    /^[A-Za-z0-9._~-]+$/u.test(tokenHash);

  if (!validTokenHash || type !== "recovery") {
    redirect("/auth/error?code=recovery_invalid");
  }

  let verificationError: AuthErrorLike | null = null;
  try {
    const supabase = await createClient();
    const { error } = await supabase.auth.verifyOtp({
      token_hash: tokenHash,
      type: "recovery",
    });
    verificationError = error;
  } catch (error) {
    technicalError("password-recovery-confirm-client", error as AuthErrorLike);
    redirect("/auth/error?code=network_error");
  }

  if (verificationError) {
    technicalError("password-recovery-confirm", verificationError);
    const code = authErrorCode(verificationError, "confirmation");
    redirect(`/auth/error?code=${code === "confirmation_expired" ? "confirmation_expired" : "recovery_invalid"}`);
  }

  redirect("/auth/update-password");
}

export async function updatePassword(
  _previousState: AuthFormState,
  formData: FormData,
): Promise<AuthFormState> {
  const password = formData.get("password");
  const confirmPassword = formData.get("confirmPassword");
  const fieldErrors: AuthFormState["fieldErrors"] = {};
  const passwordError = validateNewPassword(password);
  if (passwordError) fieldErrors.password = passwordError;
  if (typeof confirmPassword !== "string" || confirmPassword !== password) {
    fieldErrors.confirmPassword = "Les deux mots de passe doivent être identiques.";
  }
  if (Object.keys(fieldErrors).length) return { status: "error", fieldErrors };

  try {
    const supabase = await createClient();
    const { data: { user }, error: userError } = await supabase.auth.getUser();
    if (userError || !user) {
      if (userError) technicalError("password-update-session", userError);
      return { status: "error", message: authMessage("recovery_invalid") };
    }
    const { error } = await supabase.auth.updateUser({ password: password as string });
    if (error) {
      technicalError("password-update", error);
      return { status: "error", message: "Le mot de passe n’a pas pu être modifié. Demandez un nouveau lien puis réessayez." };
    }
    const { error: signOutError } = await supabase.auth.signOut();
    if (signOutError) technicalError("password-update-signout", signOutError);
  } catch (error) {
    technicalError("password-update-client", error as AuthErrorLike);
    return { status: "error", message: authMessage("network_error") };
  }

  revalidatePath("/", "layout");
  redirect("/auth/login?status=password_updated");
}

export async function updateProfile(
  _previousState: AuthFormState,
  formData: FormData,
): Promise<AuthFormState> {
  const displayName = normalizeDisplayName(formData.get("displayName"));
  const displayNameError = validateDisplayName(displayName);
  if (displayNameError) {
    return { status: "error", fieldErrors: { displayName: displayNameError } };
  }

  try {
    const supabase = await createClient();
    const { data: { user }, error: userError } = await supabase.auth.getUser();
    if (userError || !user) {
      if (userError) technicalError("profile-session", userError);
      return { status: "error", message: authMessage("session_expired") };
    }
    const { error: profileError } = await supabase.from("profiles").upsert({
      id: user.id,
      display_name: displayName,
      updated_at: new Date().toISOString(),
    });
    if (profileError) {
      technicalError("profile-update", profileError);
      return { status: "error", message: "Le profil n’a pas pu être enregistré. Réessayez." };
    }
    const { error: metadataError } = await supabase.auth.updateUser({
      data: { ...user.user_metadata, display_name: displayName },
    });
    if (metadataError) technicalError("profile-metadata", metadataError);
  } catch (error) {
    technicalError("profile-client", error as AuthErrorLike);
    return { status: "error", message: authMessage("network_error") };
  }

  revalidatePath("/", "layout");
  redirect("/profile?status=updated");
}
