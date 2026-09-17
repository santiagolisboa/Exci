import Link from "next/link";
import { authMessage } from "@/lib/auth-validation";

type AuthErrorPageProps = {
  searchParams: Promise<{ code?: string | string[] }>;
};

export default async function AuthErrorPage({
  searchParams,
}: AuthErrorPageProps) {
  const { code } = await searchParams;
  const message = authMessage(typeof code === "string" ? code : "unknown");

  return (
    <main className="auth-page">
      <section className="auth-card card auth-error-card">
        <p className="eyebrow">Compte EXCI</p>
        <h1>Authentification impossible</h1>
        <p>{message}</p>
        <div className="auth-error-actions">
          <Link className="button" href="/auth/login">Retour à la connexion</Link>
          <Link className="guest-link" href="/">Continuer sans s’inscrire</Link>
        </div>
      </section>
    </main>
  );
}
