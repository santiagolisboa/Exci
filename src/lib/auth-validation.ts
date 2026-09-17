export type AuthFormState = {
  status: "idle" | "error";
  message?: string;
  fieldErrors?: Partial<Record<"displayName" | "email" | "password", string>>;
};

export const initialAuthFormState: AuthFormState = { status: "idle" };

export type AuthErrorLike = {
  code?: string;
  message?: string;
  status?: number;
};

export function normalizeDisplayName(value: unknown) {
  return typeof value === "string" ? value.trim().replace(/\s+/g, " ") : "";
}

export function validateDisplayName(value: unknown) {
  const displayName = normalizeDisplayName(value);
  if (displayName.length < 2) return "Saisissez au moins 2 caractères.";
  if (displayName.length > 50) return "Utilisez 50 caractères maximum.";
  if (/[<>\u0000-\u001f\u007f]/u.test(displayName)) return "Ce nom contient des caractères non autorisés.";
  return null;
}

export function normalizeEmail(value: unknown) {
  return typeof value === "string" ? value.trim().toLocaleLowerCase("fr") : "";
}

export function validateEmail(value: unknown) {
  const email = normalizeEmail(value);
  if (!email || email.length > 254 || !/^[^\s@]+@[^\s@]+\.[^\s@]+$/u.test(email)) {
    return "Saisissez une adresse email valide.";
  }
  return null;
}

export function validateNewPassword(value: unknown) {
  if (typeof value !== "string" || value.length < 8) return "Utilisez au moins 8 caractères.";
  if (value.length > 128) return "Utilisez 128 caractères maximum.";
  return null;
}

export function authErrorCode(error: AuthErrorLike, context: "login" | "signup" | "oauth" | "confirmation") {
  const code = error.code?.toLowerCase() ?? "";
  const message = error.message?.toLowerCase() ?? "";
  if (code.includes("over_request_rate_limit") || error.status === 429 || message.includes("rate limit")) return "rate_limited";
  if (context === "login" && (code.includes("email_not_confirmed") || message.includes("email not confirmed"))) return "email_not_confirmed";
  if (context === "confirmation" && (code.includes("otp_expired") || message.includes("expired"))) return "confirmation_expired";
  if (context === "confirmation") return "confirmation_invalid";
  if (context === "oauth") return "oauth_failed";
  return context === "login" ? "login_failed" : "signup_failed";
}

export function authMessage(code: string) {
  const messages: Record<string, string> = {
    configuration_error: "Le service de compte est temporairement indisponible. Vous pouvez continuer sans compte.",
    confirmation_expired: "Ce lien de confirmation a expiré. Recommencez l’inscription pour recevoir un nouveau lien.",
    confirmation_invalid: "Ce lien de confirmation est invalide ou a déjà été utilisé.",
    email_not_confirmed: "Confirmez votre adresse email avec le lien reçu avant de vous connecter.",
    login_failed: "Connexion impossible. Vérifiez vos identifiants et la confirmation de votre adresse.",
    network_error: "Le service est injoignable. Vérifiez votre connexion puis réessayez.",
    oauth_failed: "La connexion avec Google n’a pas abouti. Aucun compte n’a été modifié.",
    rate_limited: "Trop de tentatives ont été effectuées. Patientez quelques minutes avant de réessayer.",
    session_expired: "Votre session a expiré. Connectez-vous de nouveau pour retrouver votre progression synchronisée.",
    signup_failed: "Création impossible pour le moment. Si vous avez déjà un compte, essayez de vous connecter ou de confirmer l’email reçu.",
    signup_unavailable: "La création n’a pas abouti. Si cette adresse est déjà associée à EXCI, essayez de vous connecter.",
  };
  return messages[code] ?? "Une erreur d’authentification est survenue. Vous pouvez réessayer ou continuer sans compte.";
}
