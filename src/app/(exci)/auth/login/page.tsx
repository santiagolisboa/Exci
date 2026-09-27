import type { Metadata } from "next";
import { AuthForm } from "@/components/auth-form";

export const metadata: Metadata = { title: "Connexion" };

type Props = { searchParams: Promise<{ status?: string | string[] }> };

export default async function LoginPage({ searchParams }: Props) {
  const { status } = await searchParams;
  return <main className="auth-page"><section className="auth-card card"><div className="auth-heading"><p className="eyebrow">Votre espace EXCI</p><h1>Bon retour parmi nous</h1><p>Connectez-vous pour retrouver votre progression sur tous vos appareils.</p></div>{status === "password_updated" ? <div className="auth-status" role="status"><strong>Mot de passe modifié</strong><span>Vous pouvez maintenant vous connecter avec votre nouveau mot de passe.</span></div> : null}<AuthForm mode="login" /></section></main>;
}
