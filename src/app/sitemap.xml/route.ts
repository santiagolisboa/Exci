const pigeonsUrls = ["/", "/en", "/es", "/de"];
const exciUrls = ["/", "/quiz", "/exam", "/preparer-examen-civique"];
export function GET(request: Request) {
 const host = request.headers.get("host")?.split(":")[0].toLowerCase();
 const pigeons = host === "pigeons.click" || host === "www.pigeons.click";
 const origin = pigeons ? "https://pigeons.click" : "https://exci.pigeons.click";
 const alternate = pigeons ? ["fr", "en", "es", "de", "x-default"].map(locale => '<xhtml:link rel="alternate" hreflang="' + locale + '" href="https://pigeons.click' + (locale === "fr" || locale === "x-default" ? "/" : "/" + locale) + '"/>').join("") : "";
 const urls = (pigeons ? pigeonsUrls : exciUrls).map(path => "<url><loc>" + origin + path + "</loc>" + alternate + "</url>").join("");
 return new Response('<?xml version="1.0" encoding="UTF-8"?><urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9" xmlns:xhtml="http://www.w3.org/1999/xhtml">' + urls + '</urlset>', { headers: { "Content-Type": "application/xml; charset=utf-8", "Cache-Control": "public, max-age=3600" } });
}
