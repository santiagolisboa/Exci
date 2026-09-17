"use client";

import { useState } from "react";

import { authMessage } from "@/lib/auth-validation";
import { createClient } from "@/lib/supabase/client";

export function GoogleAuthButton() {
  const [pending, setPending] = useState(false);
  const [error, setError] = useState("");

  if (process.env.NEXT_PUBLIC_GOOGLE_AUTH_ENABLED !== "true") return null;

  const continueWithGoogle = async () => {
    setPending(true);
    setError("");
    try {
      const { error: oauthError } = await createClient().auth.signInWithOAuth({
        provider: "google",
        options: { redirectTo: `${window.location.origin}/auth/confirm` },
      });
      if (oauthError) {
        console.error("[auth:oauth]", { code: oauthError.code, status: oauthError.status });
        setError(authMessage("oauth_failed"));
        setPending(false);
      }
    } catch {
      setError(authMessage("network_error"));
      setPending(false);
    }
  };

  return (
    <div className="oauth-block">
      <div className="auth-divider"><span>ou</span></div>
      {error ? <p className="form-error" role="alert">{error}</p> : null}
      <button className="google-button" disabled={pending} onClick={() => void continueWithGoogle()} type="button">
        <span aria-hidden="true">G</span>{pending ? "Redirection…" : "Continuer avec Google"}
      </button>
    </div>
  );
}
