"use client";

import Link from "next/link";
import { useApp } from "@/components/app-provider";
import { calculateStatistics } from "@/lib/progress";
import { questions } from "@/lib/questions";

export default function StatisticsPage() {
  const { progress, hydrated } = useApp();
  const stats = calculateStatistics(progress);
  const lastExam = progress.results.filter(({ mode }) => mode === "exam").at(-1);
  const recent = progress.attempts.slice(-30);
  const categories = [...new Set(questions.map(({categoryLabel}) => categoryLabel))].map((label) => {
    const ids = new Set(questions.filter(({categoryLabel}) => categoryLabel === label).map(({id}) => id));
    const attempts = recent.filter(({questionId}) => ids.has(questionId));
    const correct = attempts.filter((attempt) => attempt.correct).length;
    return { label, count: attempts.length, rate: attempts.length ? Math.round(correct / attempts.length * 100) : 0 };
  });
  if (!hydrated) return <div className="loading-state">Chargement des statistiques…</div>;
  return <section className="stats-page"><p className="eyebrow">Votre progression</p><h1 className="page-title">Statistiques</h1><p className="page-lead">Des chiffres fondés uniquement sur vos réponses enregistrées, localement ou synchronisées avec votre compte.</p>{stats.answered ? <><div className="stat-grid"><article><span>Réponses enregistrées</span><strong>{stats.answered}</strong><small>{stats.uniqueAnswered} questions différentes</small></article><article><span>Taux de réussite</span><strong>{stats.successRate}%</strong><small>{stats.correct} bonnes réponses</small></article><article><span>Réponses incorrectes</span><strong>{stats.errors}</strong><small>sur toutes les tentatives</small></article><article><span>Sessions terminées</span><strong>{stats.quizzes + stats.exams}</strong><small>{stats.quizzes} quiz · {stats.exams} examen{stats.exams > 1 ? "s" : ""}{lastExam ? ` · dernier ${lastExam.score}/${lastExam.total}` : ""}</small></article></div><div className="stats-detail card"><div className="stats-detail-heading"><div><h2>Résultats par thème</h2><p>Sur vos 30 réponses les plus récentes</p></div><span>{Math.round(stats.uniqueAnswered/questions.length*100)}% de la banque explorée</span></div><div className="category-bars">{categories.map((category) => <div className="category-row" key={category.label}><div><span>{category.label}</span><small>{category.count ? `${category.count} réponse${category.count>1?"s":""}` : "Pas encore abordé"}</small></div><div className="category-track"><i style={{width:`${category.rate}%`}} /></div><strong>{category.count ? `${category.rate}%` : "—"}</strong></div>)}</div></div></> : <div className="empty-state collection-empty"><h2>Votre parcours commence ici</h2><p>Aucune donnée fictive : vos indicateurs apparaîtront après votre première réponse.</p><Link className="button" href="/quiz">Faire mon premier quiz</Link></div>}</section>;
}
