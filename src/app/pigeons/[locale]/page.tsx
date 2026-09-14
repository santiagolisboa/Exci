import type { Metadata } from "next";
import { notFound } from "next/navigation";
import { PigeonsLanding } from "@/components/pigeons-landing";
import { isPigeonsLocale, locales, pigeonsCopy } from "@/lib/pigeons-i18n";

type Props = { params: Promise<{ locale: string }> };
export function generateStaticParams() { return locales.map((locale) => ({ locale })); }

export async function generateMetadata({ params }: Props): Promise<Metadata> {
  const { locale } = await params;
  if (!isPigeonsLocale(locale)) return {};
  const copy = pigeonsCopy[locale];
  const canonical = locale === "fr" ? "https://pigeons.click" : `https://pigeons.click/${locale}`;
  return {
    metadataBase: new URL("https://pigeons.click"), title: `Pigeons — ${copy.hero.title}`,
    description: copy.hero.text, alternates: { canonical, languages: { "fr-FR": "/", "en-US": "/en", "es-ES": "/es", "de-DE": "/de" } },
    openGraph: { title: "Pigeons", description: copy.hero.text, url: canonical, siteName: "Pigeons", locale, type: "website" },
  };
}

export default async function PigeonsPage({ params }: Props) {
  const { locale } = await params;
  if (!isPigeonsLocale(locale)) notFound();
  return <PigeonsLanding locale={locale} />;
}
