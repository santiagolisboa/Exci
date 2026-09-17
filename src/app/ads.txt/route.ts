function isExci(request: Request) {
  const host = request.headers.get("host")?.split(":")[0].toLowerCase();
  return host !== "pigeons.click" && host !== "www.pigeons.click";
}

export function GET(request: Request) {
  const client = process.env.NEXT_PUBLIC_ADSENSE_CLIENT;
  const enabled = process.env.NEXT_PUBLIC_ADSENSE_ENABLED === "true";
  const publisher = /^ca-pub-\d+$/.test(client ?? "") ? client?.replace(/^ca-/, "") : undefined;

  if (!isExci(request) || !enabled || !publisher) {
    return new Response("Not found", { status: 404 });
  }

  return new Response(`google.com, ${publisher}, DIRECT, f08c47fec0942fa0\n`, {
    headers: { "Content-Type": "text/plain; charset=utf-8", "Cache-Control": "public, max-age=3600" },
  });
}
