import type { Metadata } from "next";
import Link from "next/link";
import { AuthForm } from "@/components/auth-form";

export const metadata: Metadata = { title: "Créer un compte" };
type Props = { searchParams: Promise<{ status?: string | string[] }> };

export default async function SignUpPage({ searchParams }: Props) {
  const { status } = await searchParams;
  const checkEmail = status === "check_email";
  return <main className="auth-page"><section className="auth-card card"><div className="auth-heading"><p className="eyebrow">Progression synchronisée</p><h1>Créer votre compte</h1><p>Sauvegardez vos favoris, vos erreurs et vos résultats pour continuer sur Android, iPhone ou Web.</p></div>{checkEmail ? <><div className="auth-status" role="status"><strong>Un email vous attend</strong><span>Ouvrez le lien reçu pour confirmer votre inscription, puis revenez vous connecter.</span></div><div className="auth-links"><Link href="/auth/login">Aller à la connexion</Link><Link className="guest-link" href="/">Continuer sans s’inscrire</Link></div></> : <AuthForm mode="signup" />}</section></main>;
}
