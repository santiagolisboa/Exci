import type { Metadata } from "next";
import Link from "next/link";

import { confirmPasswordRecovery } from "@/app/(exci)/auth/actions";

export const metadata: Metadata = {
  title: "Confirmer la réinitialisation",
  robots: { index: false, follow: false },
  referrer: "no-referrer",
};

type Props = {
  searchParams: Promise<{
    token_hash?: string | string[];
    type?: string | string[];
  }>;
};

export default async function PasswordRecoveryPage({ searchParams }: Props) {
  const params = await searchParams;
  const tokenHash = typeof params.token_hash === "string" ? params.token_hash : "";
  const isRecovery = params.type === "recovery";
  const hasValidRequest = tokenHash.length >= 20 && tokenHash.length <= 512 && isRecovery;

  return (
    <main className="auth-page">
      <section className="auth-card card">
        <div className="auth-heading">
          <p className="eyebrow">Compte EXCI</p>
          <h1>Confirmer la réinitialisation</h1>
          <p>
            Appuyez sur le bouton ci-dessous pour ouvrir l’écran de choix du nouveau mot de passe.
          </p>
        </div>
        {hasValidRequest ? (
          <form action={confirmPasswordRecovery}>
            <input name="tokenHash" type="hidden" value={tokenHash} />
            <input name="type" type="hidden" value="recovery" />
            <button className="button" type="submit">Continuer</button>
          </form>
        ) : (
          <div className="form-error" role="alert">
            Ce lien de réinitialisation est incomplet ou invalide.
          </div>
        )}
        <div className="auth-links">
          <Link href="/auth/forgot-password">Demander un nouveau lien</Link>
          <Link className="guest-link" href="/">Continuer sans s’inscrire</Link>
        </div>
      </section>
    </main>
  );
}
