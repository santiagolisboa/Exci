"use client";

import Script from "next/script";
import { useEffect, useRef } from "react";

declare global {
  interface Window { adsbygoogle?: Record<string, unknown>[] }
}

const client = process.env.NEXT_PUBLIC_ADSENSE_CLIENT;
const slot = process.env.NEXT_PUBLIC_ADSENSE_HOME_SLOT;

export function HomeAdSlot() {
  const requested = useRef(false);
  const enabled = process.env.NEXT_PUBLIC_ADSENSE_ENABLED === "true" && /^ca-pub-\d+$/.test(client ?? "") && /^\d+$/.test(slot ?? "");

  useEffect(() => {
    if (!enabled || requested.current) return;
    requested.current = true;
    try {
      (window.adsbygoogle = window.adsbygoogle ?? []).push({});
    } catch {
      requested.current = false;
    }
  }, [enabled]);

  if (!enabled) return null;

  return <aside className="ad-placement" aria-label="Publicité">
    <span className="ad-label">Publicité</span>
    <Script id="exci-adsense" src={`https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=${client}`} strategy="lazyOnload" crossOrigin="anonymous" />
    <ins className="adsbygoogle exci-ad-unit" data-ad-client={client} data-ad-slot={slot} data-ad-format="horizontal" data-full-width-responsive="true" />
  </aside>;
}
