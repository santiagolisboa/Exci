import { NextResponse, type NextRequest } from "next/server";
import { updateSession } from "@/lib/supabase/proxy";
import { resolvePigeonsPath } from "@/lib/site-routing";
export async function proxy(request: NextRequest) {
  const hostname = request.headers.get("host")?.split(":")[0].toLowerCase();
  const pathname = request.nextUrl.pathname;
  const isPigeons = hostname === "pigeons.click" || hostname === "www.pigeons.click";
  if (hostname === "www.pigeons.click") {
    const url = request.nextUrl.clone(); url.hostname = "pigeons.click"; url.protocol = "https:"; url.port = "";
    return NextResponse.redirect(url, 308);
  }
  if (isPigeons) {
    if (pathname === "/fr") return NextResponse.redirect(new URL("/", request.url), 308);
    if (/^\/pigeons\/(fr|en|es|de)$/.test(pathname)) {
      const locale = pathname.split("/")[2];
      return NextResponse.redirect(new URL(locale === "fr" ? "/" : "/" + locale, request.url), 308);
    }
    if (pathname === "/social/pigeons" || pathname === "/pigeons/icon.svg") return NextResponse.next();
    const route = resolvePigeonsPath(pathname);
    const url = request.nextUrl.clone(); url.pathname = route?.pathname ?? "/pigeons/not-found";
    const headers = new Headers(request.headers); headers.set("x-site-locale", route?.locale ?? "fr");
    return NextResponse.rewrite(url, { request: { headers } });
  }
  if (pathname.startsWith("/pigeons/")) {
    const locale = pathname.split("/")[2];
    if (["fr", "en", "es", "de"].includes(locale)) return NextResponse.redirect(new URL(locale === "fr" ? "/" : "/" + locale, "https://pigeons.click"), 308);
  }
  return updateSession(request);
}
export const config = { matcher: ["/((?!_next/static|_next/image|favicon.ico|robots.txt|sitemap.xml|ads.txt|.*\\.(?:svg|png|jpg|jpeg|gif|webp)$).*)"] };
