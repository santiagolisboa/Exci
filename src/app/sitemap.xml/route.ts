const lastModified = "2026-09-17";

function entry(url: string, changeFrequency: string, priority: number) {
  return `<url><loc>${url}</loc><lastmod>${lastModified}</lastmod><changefreq>${changeFrequency}</changefreq><priority>${priority}</priority></url>`;
}

export function GET(request: Request) {
  const host = request.headers.get("host")?.split(":")[0].toLowerCase();
  const pigeons = host === "pigeons.click" || host === "www.pigeons.click";
  const urls = pigeons
    ? [entry("https://pigeons.click/", "monthly", 1), entry("https://pigeons.click/en", "monthly", 0.8), entry("https://pigeons.click/es", "monthly", 0.8), entry("https://pigeons.click/de", "monthly", 0.8)]
    : [entry("https://exci.pigeons.click/", "weekly", 1), entry("https://exci.pigeons.click/quiz", "weekly", 0.8), entry("https://exci.pigeons.click/exam", "weekly", 0.8)];
  return new Response(`<?xml version="1.0" encoding="UTF-8"?><urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">${urls.join("")}</urlset>`, {
    headers: { "Content-Type": "application/xml; charset=utf-8", "Cache-Control": "public, max-age=3600" },
  });
}
