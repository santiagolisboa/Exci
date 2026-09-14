"use client";

import Link from "next/link";
import { useApp } from "@/components/app-provider";
import { Icon } from "@/components/icons";
import { calculateStatistics } from "@/lib/progress";
import { categoryCount, questions } from "@/lib/questions";

const tools = [
  { href: "/exam", title: "Examen blanc", detail: "40 questions · 45 minutes", icon: "exam", tone: "blue" },
  { href: "/errors", title: "Mes erreurs", detail: "Revoir les notions à renforcer", icon: "error", tone: "red" },
  { href: "/favorites", title: "Favoris", detail: "Retrouver les questions marquées", icon: "heart", tone: "green" },
  { href: "/statistics", title: "Statistiques", detail: "Mesurer votre régularité", icon: "chart", tone: "ochre" },
];

export default function Home() {
  const { progress, hydrated, user } = useApp();
  const stats = calculateStatistics(progress);
  return <>
    <section className="home-hero">
      <div className="hero-copy"><p className="eyebrow">Préparation à l’examen civique</p><h1>Comprendre la France.<br />Réussir sereinement.</h1><p>Entraînez-vous avec la banque de questions de l’examen de naturalisation, suivez vos progrès et ciblez les notions à revoir.</p><div className="hero-actions"><Link className="button" href="/quiz">{stats.answered ? "Continuer l’entraînement" : "Commencer à apprendre"}<Icon name="arrow" width={19} height={19} /></Link><span>{questions.length} questions vérifiées · {categoryCount} thèmes</span></div></div>
      <Link className="quick-card" href="/quiz" aria-label="Démarrer un quiz rapide"><div className="quick-top"><span className="quick-icon"><Icon name="quiz" width={27} height={27} /></span><span>10 questions</span></div><div><p>Quiz rapide</p><h2>Testez vos connaissances</h2></div><span className="quick-link">Démarrer <Icon name="arrow" width={18} height={18} /></span></Link>
    </section>
    <section className="home-section" aria-labelledby="tools-title"><div className="section-heading"><div><p className="eyebrow">Votre préparation</p><h2 id="tools-title">Choisissez votre rythme</h2></div>{hydrated && stats.answered > 0 ? <span>{stats.successRate}% de réussite</span> : null}</div><div className="tool-grid">{tools.map((tool) => <Link className="tool-card" href={tool.href} key={tool.href}><span className={`tool-icon ${tool.tone}`}><Icon name={tool.icon} width={24} height={24} /></span><span><strong>{tool.title}</strong><small>{tool.detail}</small></span><Icon className="tool-arrow" name="arrow" width={19} height={19} /></Link>)}</div></section>
    {!user ? <aside className="account-prompt"><div><p className="eyebrow">Votre progression vous suit</p><h2>Vous voulez vous inscrire ?</h2><p>Retrouvez vos favoris, vos résultats et votre progression sur Android, iPhone ou Web. Vous pouvez aussi continuer sans compte : vos données restent sur cet appareil.</p></div><div className="account-prompt-actions"><Link className="button" href="/auth/sign-up">Créer un compte</Link><Link className="button secondary" href="/auth/login">Se connecter</Link><span>Vous êtes déjà en mode invité.</span></div></aside> : <aside className="sync-banner"><Icon name="user" width={22} height={22} /><div><strong>Compte connecté</strong><span>Votre session est active sur cet appareil.</span></div></aside>}
  </>;
}
