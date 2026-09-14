"use client";

import Link from "next/link";
import { useApp } from "@/components/app-provider";
import { getDisplayQuestion, QuestionView } from "@/components/question-view";
import { getQuestion } from "@/lib/questions";

export default function FavoritesPage() {
  const { progress, hydrated } = useApp();
  const favorites = progress.favorites.map(getQuestion).filter((question) => question !== undefined);
  return <section className="collection-page"><p className="eyebrow">À conserver</p><h1 className="page-title">Favoris</h1><p className="page-lead">Marquez les questions importantes pendant vos entraînements et retrouvez-les ici.</p>{!hydrated ? <div className="loading-state">Chargement…</div> : favorites.length ? <div className="collection-list">{favorites.map((question) => <QuestionView compact key={question.id} question={question} selected={getDisplayQuestion(question).correctIndex} validated onSelect={() => undefined} />)}</div> : <div className="empty-state collection-empty"><h2>Votre liste est vide</h2><p>Utilisez le marque-page sur une question pour la garder à portée de main.</p><Link className="button" href="/quiz">Découvrir les questions</Link></div>}</section>;
}
