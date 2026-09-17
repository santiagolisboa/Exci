import { NextResponse, type NextRequest } from "next/server";

import { updateSession } from "@/lib/supabase/proxy";

export async function proxy(request: NextRequest) {
  const hostname = request.headers.get("host")?.split(":")[0].toLowerCase();
  const pathname = request.nextUrl.pathname;
  const locales = new Set(["fr", "en", "es", "de"]);
  const isPigeonsHost = hostname === "pigeons.click" || hostname === "www.pigeons.click";

  if (isPigeonsHost || pathname.startsWith("/pigeons/")) {
    const pathLocale = pathname.split("/").filter(Boolean).at(-1);
    const locale = pathLocale && locales.has(pathLocale)
      ? pathLocale
      : request.cookies.get("pigeons-locale")?.value ?? "fr";
    const headers = new Headers(request.headers);
    headers.set("x-site-locale", locales.has(locale) ? locale : "fr");
    const url = request.nextUrl.clone();
    if (isPigeonsHost) url.pathname = `/pigeons/${locales.has(locale) ? locale : "fr"}`;
    const response = isPigeonsHost
      ? NextResponse.rewrite(url, { request: { headers } })
      : NextResponse.next({ request: { headers } });
    response.cookies.set("pigeons-locale", locales.has(locale) ? locale : "fr", {
      maxAge: 60 * 60 * 24 * 365,
      path: "/",
      sameSite: "lax",
    });
    return response;
  }
  return updateSession(request);
}

export const config = {
  matcher: [
    "/((?!_next/static|_next/image|favicon.ico|robots.txt|sitemap.xml|ads.txt|.*\\.(?:svg|png|jpg|jpeg|gif|webp)$).*)",
  ],
};
