"use client";

import Link from "next/link";
import { useRouter } from "next/navigation";
import { useCallback, useEffect, useRef, useState } from "react";
import { useApp } from "@/components/app-provider";
import { Icon } from "@/components/icons";
import { getDisplayQuestion, QuestionView } from "@/components/question-view";
import { activeSessionStorageKey, preferredActiveSession, sessionIdentifier, type ActiveSession } from "@/lib/active-session";
import { questions, shuffledQuestions, type Question } from "@/lib/questions";
import { createExamDeadline, examDurationMs, formatRemainingTime, remainingExamTime } from "@/lib/practice-session";

const legacyExamKey = "exci-exam-session-v1";
type ExamSession = { questionIds: string[]; answers: Record<string, number>; index: number; deadline: number };

function sanitizeExamState(value: unknown): ExamSession | null {
  if (!value || typeof value !== "object") return null;
  const candidate = value as Partial<ExamSession>;
  if (!Array.isArray(candidate.questionIds) || candidate.questionIds.length !== 40 || !candidate.questionIds.every((id) => typeof id === "string")) return null;
  const restored = candidate.questionIds.map((id) => questions.find((question) => question.id === id));
  if (restored.some((question) => !question) || typeof candidate.deadline !== "number" || !Number.isFinite(candidate.deadline)) return null;
  const answers = candidate.answers && typeof candidate.answers === "object"
    ? Object.fromEntries(Object.entries(candidate.answers).filter(([questionId, answer]) => candidate.questionIds?.includes(questionId) && Number.isInteger(answer) && answer >= 0 && answer < 4))
    : {};
  const index = Number.isInteger(candidate.index) ? Math.min(Math.max(0, candidate.index as number), 39) : 0;
  return { questionIds: candidate.questionIds, answers, index, deadline: candidate.deadline };
}

function sanitizeActiveExam(value: unknown): ActiveSession<ExamSession> | null {
  if (!value || typeof value !== "object") return null;
  const candidate = value as Partial<ActiveSession<unknown>>;
  const state = sanitizeExamState(candidate.state);
  if (!state || typeof candidate.sessionId !== "string" || !candidate.sessionId || typeof candidate.updatedAt !== "string" || Number.isNaN(Date.parse(candidate.updatedAt)) || !Number.isInteger(candidate.progressStep)) return null;
  return { sessionId: candidate.sessionId, progressStep: Math.max(0, candidate.progressStep as number), updatedAt: candidate.updatedAt, state };
}

function readStoredExam(key: string) {
  try {
    return sanitizeActiveExam(JSON.parse(localStorage.getItem(key) ?? "null"));
  } catch {
    localStorage.removeItem(key);
    return null;
  }
}

export default function ExamPage() {
  const router = useRouter();
  const { authReady, user, recordAttempt, recordResult, loadActiveSession, saveActiveSession, clearActiveSession } = useApp();
  const [phase, setPhase] = useState<"intro"|"active"|"result">("intro");
  const [examQuestions, setExamQuestions] = useState<Question[]>([]);
  const [answers, setAnswers] = useState<Record<string, number>>({});
  const [index, setIndex] = useState(0);
  const [deadline, setDeadline] = useState(0);
  const [remaining, setRemaining] = useState(examDurationMs);
  const [review, setReview] = useState(false);
  const [finalScore, setFinalScore] = useState(0);
  const [sessionId, setSessionId] = useState("");
  const [loaded, setLoaded] = useState(false);
  const finishing = useRef(false);
  const activeSession = useRef<ActiveSession<ExamSession> | null>(null);

  const applySession = useCallback((session: ActiveSession<ExamSession>) => {
    const restored = session.state.questionIds.map((id) => questions.find((question) => question.id === id)).filter((question): question is Question => Boolean(question));
    if (restored.length !== 40) return;
    activeSession.current = session;
    setSessionId(session.sessionId);
    setExamQuestions(restored);
    setAnswers(session.state.answers);
    setIndex(session.state.index);
    setDeadline(session.state.deadline);
    setRemaining(remainingExamTime(session.state.deadline));
    setPhase("active");
  }, []);

  useEffect(() => {
    if (!authReady) return;
    let cancelled = false;
    void (async () => {
      const key = activeSessionStorageKey("exam", user?.id);
      let local = readStoredExam(key);
      if (!local) {
        try {
          const legacyState = sanitizeExamState(JSON.parse(localStorage.getItem(legacyExamKey) ?? "null"));
          if (legacyState) local = { sessionId: sessionIdentifier(), progressStep: Object.keys(legacyState.answers).length, updatedAt: new Date().toISOString(), state: legacyState };
        } catch { localStorage.removeItem(legacyExamKey); }
      }
      const remote = user ? sanitizeActiveExam(await loadActiveSession<ExamSession>("exam")) : null;
      const preferred = preferredActiveSession(local, remote);
      if (cancelled) return;
      if (preferred) {
        localStorage.setItem(key, JSON.stringify(preferred));
        localStorage.removeItem(legacyExamKey);
        applySession(preferred);
        if (user && preferred !== remote) void saveActiveSession("exam", preferred);
      }
      setLoaded(true);
    })();
    return () => { cancelled = true; };
  }, [applySession, authReady, loadActiveSession, saveActiveSession, user]);

  useEffect(() => {
    if (!loaded || phase !== "active" || !sessionId || examQuestions.length !== 40) return;
    const state = { questionIds: examQuestions.map(({id}) => id), answers, index, deadline };
    const session: ActiveSession<ExamSession> = { sessionId, progressStep: Object.keys(answers).length, updatedAt: new Date().toISOString(), state };
    activeSession.current = session;
    localStorage.setItem(activeSessionStorageKey("exam", user?.id), JSON.stringify(session));
    if (user) void saveActiveSession("exam", session);
  }, [phase, examQuestions, answers, index, deadline, loaded, saveActiveSession, sessionId, user]);

  useEffect(() => {
    if (!loaded || !user || phase !== "active") return;
    let cancelled = false;
    const reconcile = async () => {
      const remote = sanitizeActiveExam(await loadActiveSession<ExamSession>("exam"));
      if (cancelled || !remote) return;
      const local = activeSession.current;
      const preferred = preferredActiveSession(local, remote);
      if (preferred === remote && remote !== local) applySession(remote);
      else if (preferred === local && local && local !== remote) void saveActiveSession("exam", local);
    };
    const onVisible = () => { if (document.visibilityState === "visible") void reconcile(); };
    const timer = window.setInterval(() => void reconcile(), 4000);
    window.addEventListener("focus", reconcile);
    document.addEventListener("visibilitychange", onVisible);
    return () => {
      cancelled = true;
      window.clearInterval(timer);
      window.removeEventListener("focus", reconcile);
      document.removeEventListener("visibilitychange", onVisible);
    };
  }, [applySession, loadActiveSession, loaded, phase, saveActiveSession, user]);

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
    setFinalScore(score); setPhase("result"); localStorage.removeItem(activeSessionStorageKey("exam", user?.id)); void clearActiveSession("exam"); activeSession.current = null;
  }, [answers, clearActiveSession, examQuestions, recordAttempt, recordResult, user?.id]);

  useEffect(() => {
    if (phase !== "active" || !deadline) return;
    const update = () => { const value = remainingExamTime(deadline); setRemaining(value); if (value <= 0) finishExam(); };
    update(); const timer = window.setInterval(update, 1000); return () => clearInterval(timer);
  }, [phase, deadline, finishExam]);

  function startExam() { const selected = shuffledQuestions(40); if (selected.length !== 40) return; const end = createExamDeadline(); void clearActiveSession("exam"); localStorage.removeItem(activeSessionStorageKey("exam", user?.id)); localStorage.removeItem(legacyExamKey); activeSession.current = null; setSessionId(sessionIdentifier()); finishing.current = false; setExamQuestions(selected); setAnswers({}); setIndex(0); setDeadline(end); setRemaining(examDurationMs); setReview(false); setPhase("active"); }
  function exitExam() { if (window.confirm("Quitter l’examen blanc ? Votre session sera conservée jusqu’à la fin du temps imparti.")) router.push("/"); }

  if (!loaded) return <div className="loading-state" role="status">Chargement de votre examen…</div>;
  if (phase === "intro") return <section className="exam-intro"><p className="eyebrow">Conditions réelles</p><h1 className="page-title">Examen blanc</h1><p className="page-lead">Mesurez votre niveau sur une série complète. Vous pouvez naviguer entre les questions et modifier vos réponses jusqu’à la validation finale.</p><div className="exam-rules card"><div><strong>40</strong><span>questions</span></div><div><strong>45</strong><span>minutes</span></div><div><strong>1</strong><span>réponse par question</span></div></div><div className="exam-notice"><Icon name="clock" width={22} height={22} /><p><strong>Le chronomètre continue si vous changez de page.</strong><br />Avec un compte connecté, votre session est reprise et synchronisée entre vos appareils. Si le délai expire en votre absence, les réponses déjà enregistrées sont automatiquement validées à votre retour.</p></div><button className="button" disabled={questions.length < 40} onClick={startExam} type="button">Commencer l’examen <Icon name="arrow" width={19} height={19} /></button>{questions.length < 40 ? <p role="alert">La banque ne contient pas assez de questions pour créer un examen.</p> : null}</section>;

  if (phase === "result") {
    const incorrect = examQuestions.filter((question) => answers[question.id] !== undefined && answers[question.id] !== getDisplayQuestion(question).correctIndex);
    const unanswered = examQuestions.length - Object.keys(answers).length;
    return <section className="exam-result"><div className="result-card card"><span className="result-kicker">Examen terminé</span><h1>{finalScore} <small>/ 40</small></h1><p>{finalScore >= 32 ? "Un résultat solide. Poursuivez vos révisions pour maintenir ce niveau." : "Utilisez la revue pour comprendre vos erreurs et cibler votre prochaine session."}</p><div className="result-breakdown"><span><strong>{finalScore}</strong> bonnes réponses</span><span><strong>{incorrect.length}</strong> erreurs</span><span><strong>{unanswered}</strong> sans réponse</span></div><div className="result-actions"><button className="button" onClick={() => setReview((value) => !value)} type="button">{review ? "Masquer la revue" : "Revoir mes erreurs"}</button><button className="button secondary" onClick={startExam} type="button">Recommencer</button><Link className="text-link" href="/">Accueil</Link></div></div>{review ? <div className="review-list"><h2>Questions à revoir</h2>{incorrect.length ? incorrect.map((question) => <QuestionView compact key={question.id} question={question} selected={answers[question.id]} validated onSelect={() => undefined} />) : <div className="empty-state"><h2>Aucune erreur</h2><p>Bravo, toutes vos réponses enregistrées sont correctes.</p></div>}</div> : null}</section>;
  }

  const question = examQuestions[index];
  const answered = Object.keys(answers).length;
  return <section className="practice-layout exam-active"><div className="practice-header exam-practice-header"><button className="exit-button" onClick={exitExam} type="button"><Icon name="close" width={19} height={19} /> Quitter</button><div className="progress-wrap"><div className="progress-label"><span>Examen blanc</span><strong>{answered} / 40 répondues</strong></div><div aria-label="Questions répondues" aria-valuemax={40} aria-valuemin={0} aria-valuenow={answered} className="progress-bar" role="progressbar"><i style={{width:`${answered/40*100}%`}} /></div></div><span aria-label={`${formatRemainingTime(remaining)} restantes`} className={remaining < 5*60*1000 ? "exam-timer urgent" : "exam-timer"} role="timer"><Icon name="clock" width={18} height={18} />{formatRemainingTime(remaining)}</span></div><div className="question-navigator" aria-label="Navigation entre les questions">{examQuestions.map((item, itemIndex) => <button aria-current={itemIndex === index ? "step" : undefined} aria-label={`Question ${itemIndex+1}${answers[item.id] !== undefined ? ", répondue" : ""}`} className={`${itemIndex === index ? "current" : ""} ${answers[item.id] !== undefined ? "answered" : ""}`} key={item.id} onClick={() => setIndex(itemIndex)} type="button">{itemIndex+1}</button>)}</div><QuestionView question={question} selected={answers[question.id] ?? null} validated={false} onSelect={(selected) => setAnswers((current) => ({...current,[question.id]:selected}))} /><div className="exam-actions"><button className="button secondary small" disabled={index === 0} onClick={() => setIndex((value) => value-1)} type="button">Précédente</button>{index < 39 ? <button className="button small" onClick={() => setIndex((value) => value+1)} type="button">Suivante <Icon name="arrow" width={17} height={17} /></button> : <button className="button small" onClick={() => { if(window.confirm(`Terminer l’examen avec ${answered} réponse${answered>1?"s":""} enregistrée${answered>1?"s":""} ?`)) finishExam(); }} type="button">Terminer l’examen</button>}</div></section>;
}
