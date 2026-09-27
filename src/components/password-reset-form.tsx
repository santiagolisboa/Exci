"use client";

import Link from "next/link";
import { useActionState, useState } from "react";
import { useFormStatus } from "react-dom";

import { requestPasswordReset, updatePassword } from "@/app/(exci)/auth/actions";
import { initialAuthFormState } from "@/lib/auth-validation";

function SubmitButton({ mode }: { mode: "request" | "update" }) {
  const { pending } = useFormStatus();
  return <button className="button" disabled={pending} type="submit">{pending ? "Envoi en cours…" : mode === "request" ? "Recevoir le lien" : "Modifier mon mot de passe"}</button>;
}

export function PasswordResetRequestForm() {
  const [state, formAction] = useActionState(requestPasswordReset, initialAuthFormState);
  return <form action={formAction} noValidate>
    {state.message ? <p className="form-error" role="alert">{state.message}</p> : null}
    <label htmlFor="email">Adresse email du compte</label>
    <input aria-describedby={state.fieldErrors?.email ? "email-error" : undefined} aria-invalid={Boolean(state.fieldErrors?.email)} autoComplete="email" id="email" inputMode="email" maxLength={254} name="email" placeholder="vous@exemple.fr" required type="email" />
    {state.fieldErrors?.email ? <small className="field-error" id="email-error">{state.fieldErrors.email}</small> : null}
    <SubmitButton mode="request" />
  </form>;
}

export function PasswordUpdateForm() {
  const [state, formAction] = useActionState(updatePassword, initialAuthFormState);
  const [showPassword, setShowPassword] = useState(false);
  return <form action={formAction} noValidate>
    {state.message ? <p className="form-error" role="alert">{state.message}</p> : null}
    <label htmlFor="password">Nouveau mot de passe</label>
    <div className="password-field">
      <input aria-describedby={state.fieldErrors?.password ? "password-error" : "password-help"} aria-invalid={Boolean(state.fieldErrors?.password)} autoComplete="new-password" id="password" maxLength={128} minLength={8} name="password" required type={showPassword ? "text" : "password"} />
      <button aria-label={showPassword ? "Masquer le mot de passe" : "Afficher le mot de passe"} className="password-toggle" onClick={() => setShowPassword((visible) => !visible)} type="button">{showPassword ? "Masquer" : "Afficher"}</button>
    </div>
    {state.fieldErrors?.password ? <small className="field-error" id="password-error">{state.fieldErrors.password}</small> : <small id="password-help">8 caractères minimum</small>}
    <label htmlFor="confirmPassword">Confirmer le mot de passe</label>
    <input aria-describedby={state.fieldErrors?.confirmPassword ? "confirmPassword-error" : undefined} aria-invalid={Boolean(state.fieldErrors?.confirmPassword)} autoComplete="new-password" id="confirmPassword" maxLength={128} minLength={8} name="confirmPassword" required type={showPassword ? "text" : "password"} />
    {state.fieldErrors?.confirmPassword ? <small className="field-error" id="confirmPassword-error">{state.fieldErrors.confirmPassword}</small> : null}
    <SubmitButton mode="update" />
  </form>;
}

export function PasswordResetLinks() {
  return <div className="auth-links"><Link href="/auth/login">Retour à la connexion</Link><Link className="guest-link" href="/">Continuer sans s’inscrire</Link></div>;
}
