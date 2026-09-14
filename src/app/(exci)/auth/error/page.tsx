import Link from "next/link";

const messages: Record<string, string> = {
  confirmation_failed:
    "Le lien de confirmation est invalide ou a expiré. Vous pouvez recommencer l’inscription.",
  invalid_credentials:
    "Veuillez saisir une adresse email valide et un mot de passe d’au moins 6 caractères.",
  login_failed: "L’adresse email ou le mot de passe est incorrect.",
  sign_up_failed:
    "L’inscription n’a pas pu être effectuée. Vérifiez vos informations et réessayez.",
};

type AuthErrorPageProps = {
  searchParams: Promise<{ code?: string | string[] }>;
};

export default async function AuthErrorPage({
  searchParams,
}: AuthErrorPageProps) {
  const { code } = await searchParams;
  const message =
    typeof code === "string" && messages[code]
      ? messages[code]
      : "Une erreur d’authentification est survenue. Veuillez réessayer.";

  return (
    <main className="flex min-h-screen items-center justify-center bg-zinc-50 px-6 py-12 dark:bg-zinc-950">
      <section className="w-full max-w-md rounded-2xl border border-zinc-200 bg-white p-8 text-center shadow-sm dark:border-zinc-800 dark:bg-zinc-900">
        <h1 className="text-2xl font-semibold text-zinc-950 dark:text-zinc-50">
          Authentification impossible
        </h1>
        <p className="mt-4 text-sm leading-6 text-zinc-600 dark:text-zinc-400">
          {message}
        </p>
        <div className="mt-6 space-y-3 text-sm">
          <Link
            className="block rounded-lg bg-zinc-950 px-4 py-2.5 font-medium text-white hover:bg-zinc-800 dark:bg-zinc-50 dark:text-zinc-950 dark:hover:bg-zinc-200"
            href="/auth/login"
          >
            Retour à la connexion
          </Link>
          <Link
            className="block text-zinc-500 underline-offset-4 hover:underline dark:text-zinc-400"
            href="/"
          >
            Continuer sans s’inscrire
          </Link>
        </div>
      </section>
    </main>
  );
}
