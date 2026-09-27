"use client";

import Link from "next/link";
import { useRouter } from "next/navigation";
import { useCallback, useEffect, useRef, useState } from "react";

import { useApp } from "@/components/app-provider";
import { Icon } from "@/components/icons";
import { getDisplayQuestion, QuestionView } from "@/components/question-view";
import {
  activeSessionStorageKey,
  preferredActiveSession,
  sessionIdentifier,
  type ActiveSession,
} from "@/lib/active-session";
import { completeQuestionSequence } from "@/lib/question-sequence";
import { questions, shuffledQuestions, type Question } from "@/lib/questions";

const legacySessionKey = "exci-quiz-session-v1";
type QuizState = { questionIds: string[]; index: number; score: number; selected: number | null; validated: boolean };

function sanitizeQuizState(value: unknown): QuizState | null {
  if (!value || typeof value !== "object") return null;
  const candidate = value as Partial<QuizState>;
  if (!Array.isArray(candidate.questionIds) || !candidate.questionIds.length || !candidate.questionIds.every((id) => typeof id === "string")) return null;
  const questionIds = completeQuestionSequence(candidate.questionIds, questions.map(({ id }) => id));
  const restored = questionIds.map((id) => questions.find((question) => question.id === id));
  if (restored.some((question) => !question)) return null;
  const index = Number.isInteger(candidate.index) ? Math.min(Math.max(0, candidate.index as number), questionIds.length - 1) : 0;
  const score = Number.isInteger(candidate.score) ? Math.min(Math.max(0, candidate.score as number), questionIds.length) : 0;
  const selected = Number.isInteger(candidate.selected) && (candidate.selected as number) >= 0 && (candidate.selected as number) < (restored[index]?.answers.length ?? 0)
    ? candidate.selected as number
    : null;
  return { questionIds, index, score, selected, validated: Boolean(candidate.validated) && selected !== null };
}

function sanitizeActiveQuiz(value: unknown): ActiveSession<QuizState> | null {
  if (!value || typeof value !== "object") return null;
  const candidate = value as Partial<ActiveSession<unknown>>;
  const state = sanitizeQuizState(candidate.state);
  if (!state || typeof candidate.sessionId !== "string" || !candidate.sessionId || typeof candidate.updatedAt !== "string" || Number.isNaN(Date.parse(candidate.updatedAt)) || !Number.isInteger(candidate.progressStep)) return null;
  return { sessionId: candidate.sessionId, progressStep: Math.max(0, candidate.progressStep as number), updatedAt: candidate.updatedAt, state };
}

function readStoredQuiz(key: string) {
  try {
    return sanitizeActiveQuiz(JSON.parse(localStorage.getItem(key) ?? "null"));
  } catch {
    localStorage.removeItem(key);
    return null;
  }
}

function progressStep(state: QuizState) {
  return state.index * 2 + (state.validated ? 1 : 0);
}

export default function QuizPage() {
  const router = useRouter();
  const { authReady, user, recordAttempt, recordResult, loadActiveSession, saveActiveSession, clearActiveSession } = useApp();
  const [sessionQuestions, setSessionQuestions] = useState<Question[]>([]);
  const [sessionId, setSessionId] = useState("");
  const [index, setIndex] = useState(0);
  const [score, setScore] = useState(0);
  const [selected, setSelected] = useState<number | null>(null);
  const [validated, setValidated] = useState(false);
  const [finished, setFinished] = useState(false);
  const [resumeSession, setResumeSession] = useState<ActiveSession<QuizState> | null>(null);
  const [loaded, setLoaded] = useState(false);
  const activeSession = useRef<ActiveSession<QuizState> | null>(null);

  const applyState = useCallback((session: ActiveSession<QuizState>) => {
    const restored = session.state.questionIds.map((id) => questions.find((question) => question.id === id)).filter((question): question is Question => Boolean(question));
    if (restored.length !== session.state.questionIds.length) return;
    activeSession.current = session;
    setSessionId(session.sessionId);
    setSessionQuestions(restored);
    setIndex(session.state.index);
    setScore(session.state.score);
    setSelected(session.state.selected);
    setValidated(session.state.validated);
    setFinished(false);
  }, []);

  const startNewQuiz = useCallback((clearPrevious = true) => {
    const key = activeSessionStorageKey("quiz", user?.id);
    localStorage.removeItem(key);
    localStorage.removeItem(legacySessionKey);
    if (clearPrevious) void clearActiveSession("quiz");
    activeSession.current = null;
    setResumeSession(null);
    setSessionId(sessionIdentifier());
    setSessionQuestions(shuffledQuestions(questions.length));
    setIndex(0);
    setScore(0);
    setSelected(null);
    setValidated(false);
    setFinished(false);
  }, [clearActiveSession, user?.id]);

  useEffect(() => {
    if (!authReady) return;
    let cancelled = false;
    void (async () => {
      const key = activeSessionStorageKey("quiz", user?.id);
      let local = readStoredQuiz(key);
      if (!local) {
        try {
          const legacyState = sanitizeQuizState(JSON.parse(localStorage.getItem(legacySessionKey) ?? "null"));
          if (legacyState) local = { sessionId: sessionIdentifier(), progressStep: progressStep(legacyState), updatedAt: new Date().toISOString(), state: legacyState };
        } catch {
          localStorage.removeItem(legacySessionKey);
        }
      }
      const remote = user ? sanitizeActiveQuiz(await loadActiveSession<QuizState>("quiz")) : null;
      const preferred = preferredActiveSession(local, remote);
      if (cancelled) return;
      if (preferred) {
        activeSession.current = preferred;
        localStorage.setItem(key, JSON.stringify(preferred));
        localStorage.removeItem(legacySessionKey);
        setResumeSession(preferred);
        setSessionId(preferred.sessionId);
        if (user && preferred !== remote) void saveActiveSession("quiz", preferred);
      } else {
        startNewQuiz(false);
      }
      setLoaded(true);
    })();
    return () => { cancelled = true; };
  }, [authReady, loadActiveSession, saveActiveSession, startNewQuiz, user]);

  useEffect(() => {
    if (!loaded || !sessionId || !sessionQuestions.length || finished || resumeSession) return;
    const state: QuizState = { questionIds: sessionQuestions.map(({ id }) => id), index, score, selected, validated };
    const session: ActiveSession<QuizState> = { sessionId, progressStep: progressStep(state), updatedAt: new Date().toISOString(), state };
    activeSession.current = session;
    localStorage.setItem(activeSessionStorageKey("quiz", user?.id), JSON.stringify(session));
    if (user) void saveActiveSession("quiz", session);
  }, [finished, index, loaded, resumeSession, saveActiveSession, score, selected, sessionId, sessionQuestions, user, validated]);

  useEffect(() => {
    if (!loaded || !user || resumeSession || finished || !sessionQuestions.length) return;
    let cancelled = false;
    const reconcile = async () => {
      const remote = sanitizeActiveQuiz(await loadActiveSession<QuizState>("quiz"));
      if (cancelled || !remote) return;
      const local = activeSession.current;
      const preferred = preferredActiveSession(local, remote);
      if (preferred === remote && remote !== local) applyState(remote);
      else if (preferred === local && local && local !== remote) void saveActiveSession("quiz", local);
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
  }, [applyState, finished, loadActiveSession, loaded, resumeSession, saveActiveSession, sessionQuestions.length, user]);

  function resumeQuiz() {
    if (!resumeSession) return;
    applyState(resumeSession);
    setResumeSession(null);
  }

  if (!loaded) return <div className="loading-state" role="status">Préparation du quiz…</div>;
  if (resumeSession) return <section className="resume-card card"><p className="eyebrow">Entraînement en cours</p><h1>Reprendre où vous vous êtes arrêté ?</h1><p>Vous étiez à la question {Math.min(resumeSession.state.index + 1, resumeSession.state.questionIds.length)} sur {resumeSession.state.questionIds.length}. {user ? "Votre position et votre score sont conservés et synchronisés avec votre compte." : "Votre position et votre score sont conservés sur cet appareil."}</p><div className="result-actions"><button className="button" onClick={resumeQuiz} type="button">Continuer l’entraînement</button><button className="button secondary" onClick={() => startNewQuiz()} type="button">Recommencer depuis le début</button></div></section>;
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
    if (index === sessionQuestions.length - 1) {
      localStorage.removeItem(activeSessionStorageKey("quiz", user?.id));
      void clearActiveSession("quiz");
      activeSession.current = null;
      recordResult({ mode: "quiz", score, total: sessionQuestions.length });
      setFinished(true);
      return;
    }
    setIndex((value) => value + 1);
    setSelected(null);
    setValidated(false);
  }
  function exitQuiz() {
    const storageMessage = user ? "conservée et synchronisée avec votre compte" : "conservée sur cet appareil";
    if (index === 0 && !validated || window.confirm(`Quitter le quiz ? Votre progression actuelle sera ${storageMessage}.`)) router.push("/");
  }

  if (finished) {
    const successRate = Math.round(score / sessionQuestions.length * 100);
    return <section className="result-card card"><span className="result-kicker">Entraînement terminé</span><h1>{score} <small>/ {sessionQuestions.length}</small></h1><p>{successRate >= 80 ? "Très bon résultat. Continuez pour consolider vos acquis." : successRate >= 50 ? "Vous progressez. Revoir vos erreurs vous aidera à renforcer les points fragiles." : "Chaque réponse compte. Consultez vos erreurs puis recommencez à votre rythme."}</p><div className="result-actions"><button className="button" onClick={() => startNewQuiz()} type="button">Recommencer l’entraînement</button><Link className="button secondary" href="/errors">Revoir mes erreurs</Link><Link className="text-link" href="/">Retour à l’accueil</Link></div></section>;
  }

  return <section className="practice-layout">
    <div className="practice-header"><button className="exit-button" onClick={exitQuiz} type="button"><Icon name="close" width={19} height={19} /> Quitter</button><div className="progress-wrap"><div className="progress-label"><span>Entraînement complet</span><strong>{index + 1} / {sessionQuestions.length}</strong></div><div aria-label="Progression de l’entraînement" aria-valuemax={sessionQuestions.length} aria-valuemin={0} aria-valuenow={index + (validated ? 1 : 0)} className="progress-bar" role="progressbar"><i style={{ width: `${((index + (validated ? 1 : 0)) / sessionQuestions.length) * 100}%` }} /></div></div><span className="score-label">{score} bonne{score > 1 ? "s" : ""}</span></div>
    <QuestionView question={question} selected={selected} validated={validated} onSelect={setSelected} />
    <div className="practice-actions">{validated ? <button className="button" onClick={next} type="button">{index === sessionQuestions.length - 1 ? "Voir le résultat" : "Question suivante"}<Icon name="arrow" width={18} height={18} /></button> : <button className="button" disabled={selected === null} onClick={validate} type="button">Valider ma réponse</button>}</div>
  </section>;
}
