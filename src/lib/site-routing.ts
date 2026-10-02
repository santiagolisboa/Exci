export const pigeonsLocales = ["fr", "en", "es", "de"] as const;
export function resolvePigeonsPath(pathname: string) {
  if (pathname === "/") return { locale: "fr", pathname: "/pigeons/fr" };
  const locale = pathname.slice(1);
  if (pigeonsLocales.some(item => item === locale)) return { locale, pathname: "/pigeons/" + locale };
  return null;
}
