"use client";

import Link from "next/link";
import { useApp } from "@/components/app-provider";
import { getDisplayQuestion, QuestionView } from "@/components/question-view";
import { getQuestion } from "@/lib/questions";

export default function ErrorsPage() {
  const { progress, hydrated } = useApp();
  const latest = new Map(progress.attempts.map((attempt) => [attempt.questionId, attempt]));
  const errors = [...latest.values()].filter(({ correct }) => !correct).reverse();
  return <section className="collection-page"><p className="eyebrow">Révision ciblée</p><h1 className="page-title">Mes erreurs</h1><p className="page-lead">Les questions de votre dernière tentative incorrecte sont réunies ici pour vous aider à progresser.</p>{!hydrated ? <div className="loading-state">Chargement…</div> : errors.length ? <div className="collection-list">{errors.map((attempt) => { const question = getQuestion(attempt.questionId); if (!question) return null; const display = getDisplayQuestion(question); const selected = display.choices.findIndex(({originalIndex}) => originalIndex === attempt.selectedAnswerIndex); return <QuestionView compact key={question.id} question={question} selected={selected} validated onSelect={() => undefined} />; })}</div> : <div className="empty-state collection-empty"><h2>Aucune erreur à revoir</h2><p>Répondez à quelques questions : les notions à renforcer apparaîtront automatiquement ici.</p><Link className="button" href="/quiz">Lancer un quiz</Link></div>}</section>;
}
