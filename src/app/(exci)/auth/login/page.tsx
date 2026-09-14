import type { Metadata } from "next";
import Link from "next/link";
import { login } from "@/app/(exci)/auth/actions";

export const metadata: Metadata = { title: "Connexion" };

export default function LoginPage() {
  return <main className="auth-page"><section className="auth-card card"><div className="auth-heading"><p className="eyebrow">Votre espace EXCI</p><h1>Bon retour parmi nous</h1><p>Connectez-vous pour retrouver votre progression sur tous vos appareils.</p></div><form action={login}><label htmlFor="email">Adresse email</label><input autoComplete="email" id="email" name="email" required type="email" placeholder="vous@exemple.fr" /><label htmlFor="password">Mot de passe</label><input autoComplete="current-password" id="password" minLength={6} name="password" required type="password" /><button className="button" type="submit">Se connecter</button></form><div className="auth-links"><span>Pas encore de compte ? <Link href="/auth/sign-up">Créer un compte</Link></span><Link className="guest-link" href="/">Continuer sans s’inscrire</Link></div></section></main>;
}
