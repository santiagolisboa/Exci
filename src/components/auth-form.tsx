"use client";

import Link from "next/link";
import { useActionState, useState } from "react";
import { useFormStatus } from "react-dom";

import { login, signUp } from "@/app/(exci)/auth/actions";
import { initialAuthFormState } from "@/lib/auth-validation";
import { GoogleAuthButton } from "@/components/google-auth-button";

function SubmitButton({ mode }: { mode: "login" | "signup" }) {
  const { pending } = useFormStatus();
  return (
    <button className="button" disabled={pending} type="submit">
      {pending ? "Envoi en cours…" : mode === "login" ? "Se connecter" : "Créer mon compte"}
    </button>
  );
}

export function AuthForm({ mode }: { mode: "login" | "signup" }) {
  const action = mode === "login" ? login : signUp;
  const [state, formAction] = useActionState(action, initialAuthFormState);
  const [showPassword, setShowPassword] = useState(false);
  const isSignup = mode === "signup";

  return (
    <>
      <form action={formAction} noValidate>
        {state.message ? <p className="form-error" role="alert">{state.message}</p> : null}
        {isSignup ? <>
          <label htmlFor="displayName">Nom affiché</label>
          <input
            aria-describedby={state.fieldErrors?.displayName ? "displayName-error" : undefined}
            aria-invalid={Boolean(state.fieldErrors?.displayName)}
            autoComplete="name"
            id="displayName"
            maxLength={50}
            minLength={2}
            name="displayName"
            placeholder="Votre prénom"
            required
          />
          {state.fieldErrors?.displayName ? <small className="field-error" id="displayName-error">{state.fieldErrors.displayName}</small> : null}
        </> : null}
        <label htmlFor="email">Adresse email</label>
        <input
          aria-describedby={state.fieldErrors?.email ? "email-error" : undefined}
          aria-invalid={Boolean(state.fieldErrors?.email)}
          autoComplete="email"
          id="email"
          inputMode="email"
          maxLength={254}
          name="email"
          placeholder="vous@exemple.fr"
          required
          type="email"
        />
        {state.fieldErrors?.email ? <small className="field-error" id="email-error">{state.fieldErrors.email}</small> : null}
        <label htmlFor="password">Mot de passe</label>
        <div className="password-field">
          <input
            aria-describedby={state.fieldErrors?.password ? "password-error" : isSignup ? "password-help" : undefined}
            aria-invalid={Boolean(state.fieldErrors?.password)}
            autoComplete={isSignup ? "new-password" : "current-password"}
            id="password"
            maxLength={128}
            minLength={isSignup ? 8 : undefined}
            name="password"
            required
            type={showPassword ? "text" : "password"}
          />
          <button
            aria-label={showPassword ? "Masquer le mot de passe" : "Afficher le mot de passe"}
            className="password-toggle"
            onClick={() => setShowPassword((visible) => !visible)}
            type="button"
          >
            {showPassword ? "Masquer" : "Afficher"}
          </button>
        </div>
        {state.fieldErrors?.password ? <small className="field-error" id="password-error">{state.fieldErrors.password}</small> : isSignup ? <small id="password-help">8 caractères minimum</small> : null}
        <SubmitButton mode={mode} />
      </form>
      <GoogleAuthButton />
      <div className="auth-links">
        <span>{isSignup ? "Déjà inscrit ? " : "Pas encore de compte ? "}<Link href={isSignup ? "/auth/login" : "/auth/sign-up"}>{isSignup ? "Se connecter" : "Créer un compte"}</Link></span>
        <Link className="guest-link" href="/">Continuer sans s’inscrire</Link>
      </div>
    </>
  );
}
