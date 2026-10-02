export function GET(request: Request) {
 const host = request.headers.get("host")?.split(":")[0].toLowerCase();
 const origin = host === "pigeons.click" || host === "www.pigeons.click" ? "https://pigeons.click" : "https://exci.pigeons.click";
 // Private pages use noindex; crawlers must be able to read that directive.
 return new Response("User-agent: *\nAllow: /\nDisallow: /api/\n\nSitemap: " + origin + "/sitemap.xml\n", { headers: { "Content-Type": "text/plain; charset=utf-8", "Cache-Control": "public, max-age=3600" } });
}
