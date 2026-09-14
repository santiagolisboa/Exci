"use client";

import Link from "next/link";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { useApp } from "@/components/app-provider";
import { Icon } from "@/components/icons";
import { getDisplayQuestion, QuestionView } from "@/components/question-view";
import { questions, shuffledQuestions, type Question } from "@/lib/questions";

const sessionKey = "exci-quiz-session-v1";
type SavedSession = { questionIds: string[]; index: number; score: number };

export default function QuizPage() {
  const router = useRouter();
  const { recordAttempt } = useApp();
  const [sessionQuestions, setSessionQuestions] = useState<Question[]>([]);
  const [index, setIndex] = useState(0);
  const [score, setScore] = useState(0);
  const [selected, setSelected] = useState<number | null>(null);
  const [validated, setValidated] = useState(false);
  const [finished, setFinished] = useState(false);

  useEffect(() => {
    const timer = window.setTimeout(() => {
      try {
        const saved = JSON.parse(localStorage.getItem(sessionKey) ?? "null") as SavedSession | null;
        if (saved?.questionIds.length) {
          const restored = saved.questionIds.map((id) => questions.find((question) => question.id === id)).filter((question): question is Question => Boolean(question));
          if (restored.length) { setSessionQuestions(restored); setIndex(Math.min(saved.index, restored.length - 1)); setScore(saved.score); return; }
        }
      } catch { localStorage.removeItem(sessionKey); }
      setSessionQuestions(shuffledQuestions(10));
    }, 0);
    return () => window.clearTimeout(timer);
  }, []);

  useEffect(() => {
    if (sessionQuestions.length && !finished) localStorage.setItem(sessionKey, JSON.stringify({ questionIds: sessionQuestions.map(({ id }) => id), index, score }));
  }, [sessionQuestions, index, score, finished]);

  if (!sessionQuestions.length) return <div className="loading-state" role="status">Préparation du quiz…</div>;
  const question = sessionQuestions[index];
  const display = getDisplayQuestion(question);
  const isCorrect = selected === display.correctIndex;

  function validate() {
    if (selected === null || validated) return;
    setValidated(true);
    if (isCorrect) setScore((value) => value + 1);
    recordAttempt({ questionId: question.id, selectedAnswerIndex: display.choices[selected].originalIndex, correct: isCorrect, mode: "quiz" });
  }
  function next() {
    if (index === sessionQuestions.length - 1) { localStorage.removeItem(sessionKey); setFinished(true); return; }
    setIndex((value) => value + 1); setSelected(null); setValidated(false);
  }
  function exitQuiz() {
    if (index === 0 && !validated || window.confirm("Quitter le quiz ? Votre progression actuelle sera conservée sur cet appareil.")) router.push("/");
  }
  function restart() { localStorage.removeItem(sessionKey); setSessionQuestions(shuffledQuestions(10)); setIndex(0); setScore(0); setSelected(null); setValidated(false); setFinished(false); }

  if (finished) return <section className="result-card card"><span className="result-kicker">Quiz terminé</span><h1>{score} <small>/ {sessionQuestions.length}</small></h1><p>{score >= 8 ? "Très bon résultat. Continuez pour consolider vos acquis." : score >= 5 ? "Vous progressez. Une nouvelle série vous aidera à renforcer les points fragiles." : "Chaque essai compte. Consultez vos erreurs puis recommencez à votre rythme."}</p><div className="result-actions"><button className="button" onClick={restart} type="button">Nouveau quiz</button><Link className="button secondary" href="/errors">Revoir mes erreurs</Link><Link className="text-link" href="/">Retour à l’accueil</Link></div></section>;

  return <section className="practice-layout">
    <div className="practice-header"><button className="exit-button" onClick={exitQuiz} type="button"><Icon name="close" width={19} height={19} /> Quitter</button><div className="progress-wrap"><div className="progress-label"><span>Quiz rapide</span><strong>{index + 1} / {sessionQuestions.length}</strong></div><div className="progress-bar"><i style={{ width: `${((index + (validated ? 1 : 0)) / sessionQuestions.length) * 100}%` }} /></div></div><span className="score-label">{score} bonne{score > 1 ? "s" : ""}</span></div>
    <QuestionView question={question} selected={selected} validated={validated} onSelect={setSelected} />
    <div className="practice-actions">{validated ? <button className="button" onClick={next} type="button">{index === sessionQuestions.length - 1 ? "Voir le résultat" : "Question suivante"}<Icon name="arrow" width={18} height={18} /></button> : <button className="button" disabled={selected === null} onClick={validate} type="button">Valider ma réponse</button>}</div>
  </section>;
}
