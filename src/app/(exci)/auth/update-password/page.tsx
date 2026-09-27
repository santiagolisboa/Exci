import type { Metadata } from "next";
import Link from "next/link";

import { PasswordResetLinks, PasswordUpdateForm } from "@/components/password-reset-form";
import { createClient } from "@/lib/supabase/server";

export const metadata: Metadata = { title: "Choisir un nouveau mot de passe" };

export default async function UpdatePasswordPage() {
  const supabase = await createClient();
  const { data: { user } } = await supabase.auth.getUser();
  return <main className="auth-page"><section className="auth-card card"><div className="auth-heading"><p className="eyebrow">Compte EXCI</p><h1>Choisir un nouveau mot de passe</h1><p>Utilisez au moins 8 caractères, puis reconnectez-vous avec votre nouveau mot de passe.</p></div>{user ? <><PasswordUpdateForm /><PasswordResetLinks /></> : <><div className="form-error" role="alert">Ce lien de réinitialisation est invalide ou a expiré.</div><div className="auth-links"><Link href="/auth/forgot-password">Demander un nouveau lien</Link><Link className="guest-link" href="/">Continuer sans s’inscrire</Link></div></>}</section></main>;
}
