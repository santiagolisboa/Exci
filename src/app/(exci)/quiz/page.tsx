"use client";

import Link from "next/link";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { useApp } from "@/components/app-provider";
import { Icon } from "@/components/icons";
import { getDisplayQuestion, QuestionView } from "@/components/question-view";
import { questions, shuffledQuestions, type Question } from "@/lib/questions";

const sessionKey = "exci-quiz-session-v1";
type SavedSession = { questionIds: string[]; index: number; score: number; selected?: number | null; validated?: boolean };

export default function QuizPage() {
  const router = useRouter();
  const { recordAttempt, recordResult } = useApp();
  const [sessionQuestions, setSessionQuestions] = useState<Question[]>([]);
  const [index, setIndex] = useState(0);
  const [score, setScore] = useState(0);
  const [selected, setSelected] = useState<number | null>(null);
  const [validated, setValidated] = useState(false);
  const [finished, setFinished] = useState(false);
  const [resumeSession, setResumeSession] = useState<SavedSession | null>(null);
  const [loaded, setLoaded] = useState(false);

  useEffect(() => {
    const timer = window.setTimeout(() => {
      try {
        const saved = JSON.parse(localStorage.getItem(sessionKey) ?? "null") as SavedSession | null;
        if (saved?.questionIds.length) {
          const restored = saved.questionIds.map((id) => questions.find((question) => question.id === id)).filter((question): question is Question => Boolean(question));
          if (restored.length === saved.questionIds.length) { setResumeSession(saved); setLoaded(true); return; }
        }
      } catch { localStorage.removeItem(sessionKey); }
      startNewQuiz();
      setLoaded(true);
    }, 0);
    return () => window.clearTimeout(timer);
  }, []);

  useEffect(() => {
    if (sessionQuestions.length && !finished) localStorage.setItem(sessionKey, JSON.stringify({ questionIds: sessionQuestions.map(({ id }) => id), index, score, selected, validated }));
  }, [sessionQuestions, index, score, selected, validated, finished]);

  function startNewQuiz() {
    localStorage.removeItem(sessionKey);
    setResumeSession(null);
    setSessionQuestions(shuffledQuestions(10));
    setIndex(0);
    setScore(0);
    setSelected(null);
    setValidated(false);
    setFinished(false);
  }

  function resumeQuiz() {
    if (!resumeSession) return;
    const restored = resumeSession.questionIds.map((id) => questions.find((question) => question.id === id)).filter((question): question is Question => Boolean(question));
    setSessionQuestions(restored);
    const restoredIndex = Number.isInteger(resumeSession.index) ? Math.min(Math.max(0, resumeSession.index), restored.length - 1) : 0;
    const restoredSelected = Number.isInteger(resumeSession.selected) && (resumeSession.selected as number) >= 0 && (resumeSession.selected as number) < restored[restoredIndex].answers.length ? resumeSession.selected ?? null : null;
    setIndex(restoredIndex);
    setScore(Number.isInteger(resumeSession.score) ? Math.min(Math.max(0, resumeSession.score), restored.length) : 0);
    setSelected(restoredSelected);
    setValidated(Boolean(resumeSession.validated) && restoredSelected !== null);
    setResumeSession(null);
  }

  if (!loaded) return <div className="loading-state" role="status">Préparation du quiz…</div>;
  if (resumeSession) return <section className="resume-card card"><p className="eyebrow">Quiz en cours</p><h1>Reprendre votre série ?</h1><p>Vous étiez à la question {Math.min(resumeSession.index + 1, resumeSession.questionIds.length)} sur {resumeSession.questionIds.length}. Votre score actuel est conservé.</p><div className="result-actions"><button className="button" onClick={resumeQuiz} type="button">Continuer le quiz</button><button className="button secondary" onClick={startNewQuiz} type="button">Recommencer</button></div></section>;
  if (!sessionQuestions.length) return <section className="empty-state"><h1>Quiz indisponible</h1><p>Aucune question valide n’est disponible pour le moment.</p><Link className="button" href="/">Retour à l’accueil</Link></section>;
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
    if (index === sessionQuestions.length - 1) { localStorage.removeItem(sessionKey); recordResult({ mode: "quiz", score, total: sessionQuestions.length }); setFinished(true); return; }
    setIndex((value) => value + 1); setSelected(null); setValidated(false);
  }
  function exitQuiz() {
    if (index === 0 && !validated || window.confirm("Quitter le quiz ? Votre progression actuelle sera conservée sur cet appareil.")) router.push("/");
  }
  function restart() { startNewQuiz(); }

  if (finished) return <section className="result-card card"><span className="result-kicker">Quiz terminé</span><h1>{score} <small>/ {sessionQuestions.length}</small></h1><p>{score >= 8 ? "Très bon résultat. Continuez pour consolider vos acquis." : score >= 5 ? "Vous progressez. Une nouvelle série vous aidera à renforcer les points fragiles." : "Chaque essai compte. Consultez vos erreurs puis recommencez à votre rythme."}</p><div className="result-actions"><button className="button" onClick={restart} type="button">Nouveau quiz</button><Link className="button secondary" href="/errors">Revoir mes erreurs</Link><Link className="text-link" href="/">Retour à l’accueil</Link></div></section>;

  return <section className="practice-layout">
    <div className="practice-header"><button className="exit-button" onClick={exitQuiz} type="button"><Icon name="close" width={19} height={19} /> Quitter</button><div className="progress-wrap"><div className="progress-label"><span>Quiz rapide</span><strong>{index + 1} / {sessionQuestions.length}</strong></div><div aria-label="Progression du quiz" aria-valuemax={sessionQuestions.length} aria-valuemin={0} aria-valuenow={index + (validated ? 1 : 0)} className="progress-bar" role="progressbar"><i style={{ width: `${((index + (validated ? 1 : 0)) / sessionQuestions.length) * 100}%` }} /></div></div><span className="score-label">{score} bonne{score > 1 ? "s" : ""}</span></div>
    <QuestionView question={question} selected={selected} validated={validated} onSelect={setSelected} />
    <div className="practice-actions">{validated ? <button className="button" onClick={next} type="button">{index === sessionQuestions.length - 1 ? "Voir le résultat" : "Question suivante"}<Icon name="arrow" width={18} height={18} /></button> : <button className="button" disabled={selected === null} onClick={validate} type="button">Valider ma réponse</button>}</div>
  </section>;
}
