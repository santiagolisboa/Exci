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
