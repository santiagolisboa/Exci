import type { Metadata } from "next";

import { PasswordResetLinks, PasswordResetRequestForm } from "@/components/password-reset-form";

export const metadata: Metadata = { title: "Mot de passe oublié" };
type Props = { searchParams: Promise<{ status?: string | string[] }> };

export default async function ForgotPasswordPage({ searchParams }: Props) {
  const { status } = await searchParams;
  const sent = status === "sent";
  return <main className="auth-page"><section className="auth-card card"><div className="auth-heading"><p className="eyebrow">Compte EXCI</p><h1>Réinitialiser votre mot de passe</h1><p>Indiquez l’adresse liée à votre compte. Nous vous enverrons un lien sécurisé.</p></div>{sent ? <div className="auth-status" role="status"><strong>Vérifiez votre boîte mail</strong><span>Si un compte correspond à cette adresse, un lien de réinitialisation vient d’être envoyé. Pensez aussi à vérifier les indésirables.</span></div> : <PasswordResetRequestForm />}<PasswordResetLinks /></section></main>;
}
