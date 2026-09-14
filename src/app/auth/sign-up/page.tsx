import type { Metadata } from "next";
import Link from "next/link";
import { signUp } from "@/app/auth/actions";

export const metadata: Metadata = { title: "Créer un compte" };
type Props = { searchParams: Promise<{ status?: string | string[] }> };

export default async function SignUpPage({ searchParams }: Props) {
  const { status } = await searchParams;
  const checkEmail = status === "check_email";
  return <main className="auth-page"><section className="auth-card card"><div className="auth-heading"><p className="eyebrow">Progression synchronisée</p><h1>Créer votre compte</h1><p>Sauvegardez vos favoris, vos erreurs et vos résultats pour continuer sur Android, iPhone ou Web.</p></div>{checkEmail ? <div className="auth-status" role="status"><strong>Un email vous attend</strong><span>Ouvrez le lien reçu pour confirmer votre inscription.</span></div> : <form action={signUp}><label htmlFor="email">Adresse email</label><input autoComplete="email" id="email" name="email" required type="email" placeholder="vous@exemple.fr" /><label htmlFor="password">Mot de passe</label><input autoComplete="new-password" id="password" minLength={6} name="password" required type="password" /><small>6 caractères minimum</small><button className="button" type="submit">Créer mon compte</button></form>}<div className="auth-links"><span>Déjà inscrit ? <Link href="/auth/login">Se connecter</Link></span><Link className="guest-link" href="/">Continuer sans s’inscrire</Link></div></section></main>;
}
