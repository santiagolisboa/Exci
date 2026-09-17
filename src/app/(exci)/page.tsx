import type { Metadata } from "next";
import Link from "next/link";
import { HomeAdSlot } from "@/components/ad-slot";
import { HomeExperience } from "@/components/home-experience";
import { categoryCount, questions } from "@/lib/questions";

export const metadata: Metadata = {
  title: "Préparation à l’examen civique français",
  description: `Préparez l’examen civique de naturalisation avec ${questions.length} questions d’entraînement, des quiz gratuits et un examen blanc de 40 questions.`,
  alternates: { canonical: "/" },
  keywords: ["examen civique français", "examen civique naturalisation", "quiz examen civique", "questions examen civique", "examen blanc naturalisation"],
  openGraph: {
    title: "EXCI — Préparation à l’examen civique français",
    description: `${questions.length} questions d’entraînement, des quiz rapides et un examen blanc pour préparer l’examen civique.`,
    url: "/",
  },
};

const jsonLd = {
  "@context": "https://schema.org",
  "@graph": [
    { "@type": "WebSite", "@id": "https://exci.pigeons.click/#website", url: "https://exci.pigeons.click/", name: "EXCI", inLanguage: "fr-FR", description: "Outil gratuit de préparation à l’examen civique français." },
    {
      "@type": "WebApplication", "@id": "https://exci.pigeons.click/#application", name: "EXCI", url: "https://exci.pigeons.click/", applicationCategory: "EducationalApplication", operatingSystem: "Web", inLanguage: "fr-FR", isAccessibleForFree: true,
      offers: { "@type": "Offer", price: "0", priceCurrency: "EUR" },
      description: `Préparation à l’examen civique avec ${questions.length} questions, des quiz et des examens blancs.`,
      publisher: { "@type": "Organization", name: "Pigeons", url: "https://pigeons.click/" },
    },
  ],
};

export default function Home() {
  return <>
    <script type="application/ld+json" dangerouslySetInnerHTML={{ __html: JSON.stringify(jsonLd).replace(/</g, "\\u003c") }} />
    <HomeExperience />
    <HomeAdSlot />
    <article className="seo-content" aria-labelledby="prepare-title">
      <header><p className="eyebrow">Réviser avec méthode</p><h2 id="prepare-title">Préparer l’examen civique français en ligne</h2><p>EXCI vous aide à réviser les connaissances utiles à l’examen civique de naturalisation, à votre rythme et sans inscription obligatoire.</p></header>
      <div className="seo-grid">
        <section><span>01</span><h3>Apprendre par petites séries</h3><p>Le quiz de 10 questions permet de travailler régulièrement, de vérifier chaque réponse et de comprendre les notions à revoir.</p></section>
        <section><span>02</span><h3>S’entraîner en conditions d’examen</h3><p>L’examen blanc rassemble 40 questions à traiter en 45 minutes. Vous pouvez naviguer entre les questions avant de valider.</p></section>
        <section><span>03</span><h3>Cibler ses révisions</h3><p>Vos erreurs, favoris et statistiques restent disponibles sur votre appareil. Un compte permet de retrouver votre progression ailleurs.</p></section>
      </div>
      <section className="seo-topics" aria-labelledby="topics-title">
        <div><p className="eyebrow">Programme de révision</p><h2 id="topics-title">{categoryCount} grands thèmes à maîtriser</h2></div>
        <ul><li>Principes et valeurs de la République</li><li>Institutions et vie politique</li><li>Vie en société</li><li>Histoire et culture françaises</li><li>Géographie de la France</li><li>Travail et économie</li></ul>
      </section>
      <section className="seo-faq" aria-labelledby="faq-title">
        <p className="eyebrow">Questions fréquentes</p><h2 id="faq-title">Bien démarrer sa préparation</h2>
        <details><summary>EXCI est-il gratuit ?</summary><p>Oui. Les quiz, l’examen blanc et le suivi local de votre progression sont accessibles gratuitement.</p></details>
        <details><summary>Faut-il créer un compte ?</summary><p>Non. Vous pouvez commencer immédiatement. Le compte sert uniquement à synchroniser votre progression entre plusieurs appareils.</p></details>
        <details><summary>Comment organiser ses révisions ?</summary><p>Commencez par de courtes séries régulières, relisez les explications de vos erreurs, puis utilisez l’examen blanc pour mesurer votre niveau sur une session complète.</p></details>
      </section>
      <div className="seo-cta"><div><h2>Prêt à tester vos connaissances ?</h2><p>Commencez par 10 questions, sans inscription.</p></div><Link className="button" href="/quiz">Lancer un quiz</Link></div>
    </article>
  </>;
}
