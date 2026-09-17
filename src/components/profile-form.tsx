"use client";

import { useRouter } from "next/navigation";
import { useActionState, useState } from "react";
import { useFormStatus } from "react-dom";

import { updateProfile } from "@/app/(exci)/auth/actions";
import { useApp } from "@/components/app-provider";
import { initialAuthFormState } from "@/lib/auth-validation";

function SaveButton() {
  const { pending } = useFormStatus();
  return <button className="button" disabled={pending} type="submit">{pending ? "Enregistrement…" : "Enregistrer"}</button>;
}

export function ProfileForm({ displayName, email, saved }: { displayName: string; email: string; saved: boolean }) {
  const [state, formAction] = useActionState(updateProfile, initialAuthFormState);
  const [signingOut, setSigningOut] = useState(false);
  const { signOut } = useApp();
  const router = useRouter();

  const logout = async () => {
    setSigningOut(true);
    await signOut();
    router.push("/");
    router.refresh();
  };

  return <>
    {saved ? <p className="auth-status compact" role="status"><strong>Profil enregistré</strong></p> : null}
    <form action={formAction} className="profile-form" noValidate>
      {state.message ? <p className="form-error" role="alert">{state.message}</p> : null}
      <label htmlFor="displayName">Nom affiché</label>
      <input aria-describedby={state.fieldErrors?.displayName ? "displayName-error" : undefined} aria-invalid={Boolean(state.fieldErrors?.displayName)} autoComplete="name" defaultValue={displayName} id="displayName" maxLength={50} minLength={2} name="displayName" required />
      {state.fieldErrors?.displayName ? <small className="field-error" id="displayName-error">{state.fieldErrors.displayName}</small> : null}
      <label htmlFor="profile-email">Adresse email</label>
      <input autoComplete="email" disabled id="profile-email" value={email} />
      <small>L’adresse email est liée à votre compte Supabase.</small>
      <SaveButton />
    </form>
    <button className="secondary-button profile-signout" disabled={signingOut} onClick={() => void logout()} type="button">{signingOut ? "Déconnexion…" : "Se déconnecter"}</button>
  </>;
}
