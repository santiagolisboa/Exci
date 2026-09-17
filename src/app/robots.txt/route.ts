function isPigeons(request: Request) {
  const host = request.headers.get("host")?.split(":")[0].toLowerCase();
  return host === "pigeons.click" || host === "www.pigeons.click";
}

export function GET(request: Request) {
  const pigeons = isPigeons(request);
  const origin = pigeons ? "https://pigeons.click" : "https://exci.pigeons.click";
  const disallow = pigeons ? "" : "Disallow: /auth/\nDisallow: /errors\nDisallow: /favorites\nDisallow: /statistics\n";
  return new Response(`User-agent: *\nAllow: /\n${disallow}\nSitemap: ${origin}/sitemap.xml\n`, {
    headers: { "Content-Type": "text/plain; charset=utf-8", "Cache-Control": "public, max-age=3600" },
  });
}
