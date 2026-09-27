import type { EmailOtpType } from "@supabase/supabase-js";
import { type NextRequest, NextResponse } from "next/server";

import { authErrorCode } from "@/lib/auth-validation";
import { createClient } from "@/lib/supabase/server";

export async function GET(request: NextRequest) {
  const tokenHash = request.nextUrl.searchParams.get("token_hash");
  const code = request.nextUrl.searchParams.get("code");
  const type = request.nextUrl.searchParams.get("type") as EmailOtpType | null;
  const next = request.nextUrl.searchParams.get("next");
  const successPath = next === "/auth/update-password" ? next : "/";
  const providerError = request.nextUrl.searchParams.get("error");

  if (providerError) {
    return NextResponse.redirect(new URL("/auth/error?code=oauth_failed", request.url));
  }

  if (code) {
    const supabase = await createClient();
    const { error } = await supabase.auth.exchangeCodeForSession(code);
    if (!error) return NextResponse.redirect(new URL(successPath, request.url));
    const errorCode = authErrorCode(error, "confirmation");
    return NextResponse.redirect(new URL(`/auth/error?code=${errorCode}`, request.url));
  } else if (tokenHash && type) {
    const supabase = await createClient();
    const { error } = await supabase.auth.verifyOtp({
      token_hash: tokenHash,
      type,
    });

    if (!error) {
      return NextResponse.redirect(new URL(type === "recovery" ? "/auth/update-password" : successPath, request.url));
    }
    const errorCode = authErrorCode(error, "confirmation");
    return NextResponse.redirect(new URL(`/auth/error?code=${errorCode}`, request.url));
  }

  return NextResponse.redirect(new URL("/auth/error?code=confirmation_invalid", request.url));
}
