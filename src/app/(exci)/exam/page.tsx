"use client";

import Link from "next/link";
import { useRouter } from "next/navigation";
import { useCallback, useEffect, useRef, useState } from "react";
import { useApp } from "@/components/app-provider";
import { Icon } from "@/components/icons";
import { getDisplayQuestion, QuestionView } from "@/components/question-view";
import { questions, shuffledQuestions, type Question } from "@/lib/questions";
import { createExamDeadline, examDurationMs, formatRemainingTime, remainingExamTime } from "@/lib/practice-session";

const examKey = "exci-exam-session-v1";
type ExamSession = { questionIds: string[]; answers: Record<string, number>; index: number; deadline: number };

export default function ExamPage() {
  const router = useRouter();
  const { recordAttempt, recordResult } = useApp();
  const [phase, setPhase] = useState<"intro"|"active"|"result">("intro");
  const [examQuestions, setExamQuestions] = useState<Question[]>([]);
  const [answers, setAnswers] = useState<Record<string, number>>({});
  const [index, setIndex] = useState(0);
  const [deadline, setDeadline] = useState(0);
  const [remaining, setRemaining] = useState(examDurationMs);
  const [review, setReview] = useState(false);
  const [finalScore, setFinalScore] = useState(0);
  const finishing = useRef(false);

  useEffect(() => {
    const timer = window.setTimeout(() => {
      try {
        const saved = JSON.parse(localStorage.getItem(examKey) ?? "null") as ExamSession | null;
        if (saved?.deadline && Number.isFinite(saved.deadline) && saved.questionIds.length === 40) {
          const restored = saved.questionIds.map((id) => questions.find((question) => question.id === id)).filter((question): question is Question => Boolean(question));
          if (restored.length === 40) {
            const validAnswers = Object.fromEntries(Object.entries(saved.answers ?? {}).filter(([questionId, answer]) => restored.some(({ id }) => id === questionId) && Number.isInteger(answer) && answer >= 0 && answer < 4));
            setExamQuestions(restored); setAnswers(validAnswers); setIndex(Number.isInteger(saved.index) ? Math.min(Math.max(0, saved.index), 39) : 0); setDeadline(saved.deadline); setRemaining(remainingExamTime(saved.deadline)); setPhase("active");
          }
        } else if (saved) localStorage.removeItem(examKey);
      } catch { localStorage.removeItem(examKey); }
    }, 0);
    return () => window.clearTimeout(timer);
  }, []);

  useEffect(() => {
    if (phase === "active") localStorage.setItem(examKey, JSON.stringify({ questionIds: examQuestions.map(({id}) => id), answers, index, deadline }));
  }, [phase, examQuestions, answers, index, deadline]);

  const finishExam = useCallback(() => {
    if (finishing.current || !examQuestions.length) return;
    finishing.current = true;
    let score = 0;
    for (const question of examQuestions) {
      const selected = answers[question.id];
      if (selected === undefined) continue;
      const display = getDisplayQuestion(question);
      if (!Number.isInteger(selected) || !display.choices[selected]) continue;
      const correct = selected === display.correctIndex;
      if (correct) score += 1;
      recordAttempt({ questionId: question.id, selectedAnswerIndex: display.choices[selected].originalIndex, correct, mode: "exam" });
    }
    recordResult({ mode: "exam", score, total: examQuestions.length });
    setFinalScore(score); setPhase("result"); localStorage.removeItem(examKey);
  }, [answers, examQuestions, recordAttempt, recordResult]);

  useEffect(() => {
    if (phase !== "active" || !deadline) return;
    const update = () => { const value = remainingExamTime(deadline); setRemaining(value); if (value <= 0) finishExam(); };
    update(); const timer = window.setInterval(update, 1000); return () => clearInterval(timer);
  }, [phase, deadline, finishExam]);

  function startExam() { const selected = shuffledQuestions(40); if (selected.length !== 40) return; const end = createExamDeadline(); finishing.current = false; setExamQuestions(selected); setAnswers({}); setIndex(0); setDeadline(end); setRemaining(examDurationMs); setReview(false); setPhase("active"); }
  function exitExam() { if (window.confirm("Quitter l’examen blanc ? Votre session sera conservée jusqu’à la fin du temps imparti.")) router.push("/"); }

  if (phase === "intro") return <section className="exam-intro"><p className="eyebrow">Conditions réelles</p><h1 className="page-title">Examen blanc</h1><p className="page-lead">Mesurez votre niveau sur une série complète. Vous pouvez naviguer entre les questions et modifier vos réponses jusqu’à la validation finale.</p><div className="exam-rules card"><div><strong>40</strong><span>questions</span></div><div><strong>45</strong><span>minutes</span></div><div><strong>1</strong><span>réponse par question</span></div></div><div className="exam-notice"><Icon name="clock" width={22} height={22} /><p><strong>Le chronomètre continue si vous changez de page.</strong><br />Votre session est reprise sur cet appareil, même après un rafraîchissement. Si le délai expire en votre absence, les réponses déjà enregistrées sont automatiquement validées à votre retour.</p></div><button className="button" disabled={questions.length < 40} onClick={startExam} type="button">Commencer l’examen <Icon name="arrow" width={19} height={19} /></button>{questions.length < 40 ? <p role="alert">La banque ne contient pas assez de questions pour créer un examen.</p> : null}</section>;

  if (phase === "result") {
    const incorrect = examQuestions.filter((question) => answers[question.id] !== undefined && answers[question.id] !== getDisplayQuestion(question).correctIndex);
    const unanswered = examQuestions.length - Object.keys(answers).length;
    return <section className="exam-result"><div className="result-card card"><span className="result-kicker">Examen terminé</span><h1>{finalScore} <small>/ 40</small></h1><p>{finalScore >= 32 ? "Un résultat solide. Poursuivez vos révisions pour maintenir ce niveau." : "Utilisez la revue pour comprendre vos erreurs et cibler votre prochaine session."}</p><div className="result-breakdown"><span><strong>{finalScore}</strong> bonnes réponses</span><span><strong>{incorrect.length}</strong> erreurs</span><span><strong>{unanswered}</strong> sans réponse</span></div><div className="result-actions"><button className="button" onClick={() => setReview((value) => !value)} type="button">{review ? "Masquer la revue" : "Revoir mes erreurs"}</button><button className="button secondary" onClick={startExam} type="button">Recommencer</button><Link className="text-link" href="/">Accueil</Link></div></div>{review ? <div className="review-list"><h2>Questions à revoir</h2>{incorrect.length ? incorrect.map((question) => <QuestionView compact key={question.id} question={question} selected={answers[question.id]} validated onSelect={() => undefined} />) : <div className="empty-state"><h2>Aucune erreur</h2><p>Bravo, toutes vos réponses enregistrées sont correctes.</p></div>}</div> : null}</section>;
  }

  const question = examQuestions[index];
  const answered = Object.keys(answers).length;
  return <section className="practice-layout exam-active"><div className="practice-header exam-practice-header"><button className="exit-button" onClick={exitExam} type="button"><Icon name="close" width={19} height={19} /> Quitter</button><div className="progress-wrap"><div className="progress-label"><span>Examen blanc</span><strong>{answered} / 40 répondues</strong></div><div aria-label="Questions répondues" aria-valuemax={40} aria-valuemin={0} aria-valuenow={answered} className="progress-bar" role="progressbar"><i style={{width:`${answered/40*100}%`}} /></div></div><span aria-label={`${formatRemainingTime(remaining)} restantes`} className={remaining < 5*60*1000 ? "exam-timer urgent" : "exam-timer"} role="timer"><Icon name="clock" width={18} height={18} />{formatRemainingTime(remaining)}</span></div><div className="question-navigator" aria-label="Navigation entre les questions">{examQuestions.map((item, itemIndex) => <button aria-current={itemIndex === index ? "step" : undefined} aria-label={`Question ${itemIndex+1}${answers[item.id] !== undefined ? ", répondue" : ""}`} className={`${itemIndex === index ? "current" : ""} ${answers[item.id] !== undefined ? "answered" : ""}`} key={item.id} onClick={() => setIndex(itemIndex)} type="button">{itemIndex+1}</button>)}</div><QuestionView question={question} selected={answers[question.id] ?? null} validated={false} onSelect={(selected) => setAnswers((current) => ({...current,[question.id]:selected}))} /><div className="exam-actions"><button className="button secondary small" disabled={index === 0} onClick={() => setIndex((value) => value-1)} type="button">Précédente</button>{index < 39 ? <button className="button small" onClick={() => setIndex((value) => value+1)} type="button">Suivante <Icon name="arrow" width={17} height={17} /></button> : <button className="button small" onClick={() => { if(window.confirm(`Terminer l’examen avec ${answered} réponse${answered>1?"s":""} enregistrée${answered>1?"s":""} ?`)) finishExam(); }} type="button">Terminer l’examen</button>}</div></section>;
}
