import type { Metadata } from "next";
import { notFound } from "next/navigation";
import { PigeonsLanding } from "@/components/pigeons-landing";
import { isPigeonsLocale, locales, pigeonsCopy } from "@/lib/pigeons-i18n";
import { founder, studio, serializeJsonLd } from "@/lib/seo";
type Props = { params: Promise<{ locale: string }> };
const titles = { fr: "Studio indépendant d’applications utiles", en: "Independent studio for useful apps", es: "Estudio independiente de aplicaciones útiles", de: "Unabhängiges Studio für nützliche Apps" };
const ogLocales = { fr: "fr_FR", en: "en_US", es: "es_ES", de: "de_DE" };
export function generateStaticParams() { return locales.map(locale => ({ locale })); }
export async function generateMetadata({ params }: Props): Promise<Metadata> {
  const { locale } = await params; if (!isPigeonsLocale(locale)) return {};
  const title = "Pigeons — " + titles[locale]; const description = pigeonsCopy[locale].hero.text;
  const canonical = "https://pigeons.click" + (locale === "fr" ? "/" : "/" + locale);
  const image = { url: "https://pigeons.click/social/pigeons", width: 1200, height: 630, alt: "Pigeons — EXCI · Aicha — Santiago LISBOA" };
  return { metadataBase: new URL("https://pigeons.click"), title, description, robots: { index: true, follow: true },
    alternates: { canonical, languages: { fr: "https://pigeons.click/", en: "https://pigeons.click/en", es: "https://pigeons.click/es", de: "https://pigeons.click/de", "x-default": "https://pigeons.click/" } },
    openGraph: { title, description, url: canonical, siteName: "Pigeons", locale: ogLocales[locale], alternateLocale: locales.filter(item => item !== locale).map(item => ogLocales[item]), type: "website", images: [image] },
    twitter: { card: "summary_large_image", title, description, images: [image.url] } };
}
export default async function PigeonsPage({ params }: Props) {
  const { locale } = await params; if (!isPigeonsLocale(locale)) notFound();
  const url = "https://pigeons.click" + (locale === "fr" ? "/" : "/" + locale);
  const jsonLd = { "@context": "https://schema.org", "@graph": [founder, studio,
    { "@type": "WebSite", "@id": "https://pigeons.click/#website", name: "Pigeons", url: "https://pigeons.click/", publisher: { "@id": studio["@id"] }, inLanguage: locales },
    { "@type": "WebPage", "@id": url + "#webpage", url, name: "Pigeons", description: pigeonsCopy[locale].hero.text, inLanguage: locale, isPartOf: { "@id": "https://pigeons.click/#website" }, about: [{ "@id": studio["@id"] }, { "@id": founder["@id"] }], mentions: [{ "@type": "WebApplication", name: "EXCI", url: "https://exci.pigeons.click/", applicationCategory: "EducationalApplication", operatingSystem: "Web" }, { "@type": "WebApplication", name: "Aicha", url: "https://aicha.pigeons.click/", applicationCategory: "EducationalApplication", operatingSystem: "Web" }] } ] };
  return <><script type="application/ld+json" dangerouslySetInnerHTML={{ __html: serializeJsonLd(jsonLd) }} /><PigeonsLanding locale={locale} /></>;
}
