import type { Metadata } from "next";
import { AuthForm } from "@/components/auth-form";

export const metadata: Metadata = { title: "Connexion" };

export default function LoginPage() {
  return <main className="auth-page"><section className="auth-card card"><div className="auth-heading"><p className="eyebrow">Votre espace EXCI</p><h1>Bon retour parmi nous</h1><p>Connectez-vous pour retrouver votre progression sur tous vos appareils.</p></div><AuthForm mode="login" /></section></main>;
}
