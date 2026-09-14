"use client";

import { useMemo } from "react";
import { useApp } from "@/components/app-provider";
import { Icon } from "@/components/icons";
import { QuestionReportButton } from "@/components/question-report";
import type { Question } from "@/lib/questions";

function hash(value: string) { return [...value].reduce((total, char) => ((total << 5) - total + char.charCodeAt(0)) | 0, 0); }

export function getDisplayQuestion(question: Question) {
  const choices = question.answers.map((answer, originalIndex) => ({ answer, originalIndex })).sort((a,b) => hash(`${question.id}-${a.answer}`) - hash(`${question.id}-${b.answer}`));
  return { choices, correctIndex: choices.findIndex(({ originalIndex }) => originalIndex === question.correctAnswerIndex) };
}

export function useDisplayQuestion(question: Question) {
  return useMemo(() => {
    return getDisplayQuestion(question);
  }, [question]);
}

type Props = { question: Question; selected: number | null; validated: boolean; onSelect: (index: number) => void; compact?: boolean };

export function QuestionView({ question, selected, validated, onSelect, compact = false }: Props) {
  const { choices, correctIndex } = useDisplayQuestion(question);
  const { progress, toggleFavorite } = useApp();
  const favorite = progress.favorites.includes(question.id);
  return <article className={`question-card card ${compact ? "compact" : ""}`}>
    <div className="question-meta"><span>{question.categoryLabel}</span><button aria-pressed={favorite} className={favorite ? "favorite active" : "favorite"} onClick={() => toggleFavorite(question.id)} type="button"><Icon name="bookmark" width={18} height={18} />{favorite ? "Enregistrée" : "Ajouter aux favoris"}</button></div>
    <h2>{question.prompt}</h2>
    <div className="answers" role="radiogroup" aria-label="Réponses proposées">{choices.map(({ answer }, index) => {
      const state = validated ? index === correctIndex ? "correct" : index === selected ? "incorrect" : "muted" : index === selected ? "selected" : "";
      return <button aria-checked={selected === index} className={`answer ${state}`} disabled={validated} key={`${question.id}-${index}`} onClick={() => onSelect(index)} role="radio" type="button"><span className="answer-letter">{String.fromCharCode(65 + index)}</span><span>{answer}</span>{validated && index === correctIndex ? <strong>Correct</strong> : validated && index === selected ? <strong>Votre choix</strong> : null}</button>;
    })}</div>
    {validated ? <div className={selected === correctIndex ? "feedback correct" : "feedback incorrect"} role="status"><strong>{selected === correctIndex ? "Bonne réponse" : "À revoir"}</strong><p>{question.explanation}</p></div> : null}
    <QuestionReportButton questionId={question.id} />
  </article>;
}
