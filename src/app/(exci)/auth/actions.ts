"use server";

import { revalidatePath } from "next/cache";
import { redirect } from "next/navigation";

import { createClient } from "@/lib/supabase/server";

function readCredentials(formData: FormData) {
  const email = formData.get("email");
  const password = formData.get("password");

  if (
    typeof email !== "string" ||
    !email.includes("@") ||
    typeof password !== "string" ||
    password.length < 6
  ) {
    redirect("/auth/error?code=invalid_credentials");
  }

  return { email, password };
}

function getSiteUrl() {
  const siteUrl = process.env.NEXT_PUBLIC_SITE_URL;

  if (!siteUrl) {
    throw new Error("Missing NEXT_PUBLIC_SITE_URL environment variable.");
  }

  return siteUrl.replace(/\/$/, "");
}

export async function login(formData: FormData) {
  const supabase = await createClient();
  const credentials = readCredentials(formData);
  const { error } = await supabase.auth.signInWithPassword(credentials);

  if (error) {
    redirect("/auth/error?code=login_failed");
  }

  revalidatePath("/", "layout");
  redirect("/");
}

export async function signUp(formData: FormData) {
  const supabase = await createClient();
  const credentials = readCredentials(formData);
  const { data, error } = await supabase.auth.signUp({
    ...credentials,
    options: {
      emailRedirectTo: `${getSiteUrl()}/auth/confirm`,
    },
  });

  if (error) {
    redirect("/auth/error?code=sign_up_failed");
  }

  if (data.session) {
    revalidatePath("/", "layout");
    redirect("/");
  }

  redirect("/auth/sign-up?status=check_email");
}
