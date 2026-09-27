function isExci(request: Request) {
  const host = request.headers.get("host")?.split(":")[0].toLowerCase() ?? new URL(request.url).hostname.toLowerCase();
  return host !== "pigeons.click" && host !== "www.pigeons.click";
}

export function GET(request: Request) {
  const client = process.env.NEXT_PUBLIC_ADSENSE_CLIENT;
  const publisher = /^ca-pub-\d+$/.test(client ?? "") ? client?.replace(/^ca-/, "") : undefined;

  // ads.txt is also an AdSense ownership-verification method. It must therefore
  // be available before ad serving is enabled.
  if (!isExci(request) || !publisher) {
    return new Response("Not found", { status: 404 });
  }

  return new Response(`google.com, ${publisher}, DIRECT, f08c47fec0942fa0\n`, {
    headers: { "Content-Type": "text/plain; charset=utf-8", "Cache-Control": "public, max-age=3600" },
  });
}
