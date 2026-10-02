import type { Metadata } from "next";
export const founder = { "@type": "Person", "@id": "https://pigeons.click/#santiago-lisboa", name: "Santiago LISBOA", url: "https://pigeons.click/#about", sameAs: ["https://www.linkedin.com/in/santiago-lisboa/"] };
export const studio = { "@type": "Organization", "@id": "https://pigeons.click/#organization", name: "Pigeons", url: "https://pigeons.click/", founder: { "@id": founder["@id"] } };
export function exciMetadata(title: string, description: string, pathname: string): Metadata {
  const url = "https://exci.pigeons.click" + pathname;
  const image = { url: "https://exci.pigeons.click/social/exci", width: 1200, height: 630, alt: "EXCI — Préparation à l’examen civique français" };
  return { title: pathname === "/" ? { absolute: title + " | EXCI | Pigeons" } : title, description, metadataBase: new URL("https://exci.pigeons.click"), alternates: { canonical: url }, robots: { index: true, follow: true },
    openGraph: { title: title + " | EXCI | Pigeons", description, url, siteName: "EXCI", locale: "fr_FR", type: "website", images: [image] },
    twitter: { card: "summary_large_image", title: title + " | EXCI", description, images: [image.url] } };
}
export function serializeJsonLd(value: unknown) { return JSON.stringify(value).replace(/</g, "\\u003c"); }
