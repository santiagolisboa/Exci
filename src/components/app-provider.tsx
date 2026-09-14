"use client";

import type { User } from "@supabase/supabase-js";
import { usePathname } from "next/navigation";
import {
  createContext,
  useCallback,
  useContext,
  useEffect,
  useMemo,
  useRef,
  useState,
  type ReactNode,
} from "react";

import {
  emptyProgress,
  progressStorageKey,
  type AnswerAttempt,
  type ExamResult,
  type LocalProgress,
  type QuestionReport,
} from "@/lib/progress";
import { createClient, isSupabaseConfigured } from "@/lib/supabase/client";

type Theme = "light" | "dark" | "system";

type AppContextValue = {
  progress: LocalProgress;
  hydrated: boolean;
  authReady: boolean;
  user: User | null;
  theme: Theme;
  toggleFavorite: (questionId: string) => void;
  recordAttempt: (attempt: Omit<AnswerAttempt, "id" | "answeredAt">) => void;
  recordExam: (result: Omit<ExamResult, "id" | "completedAt">) => void;
  submitReport: (report: Omit<QuestionReport, "id" | "createdAt" | "synced">) => Promise<boolean>;
  setTheme: (theme: Theme) => void;
  signOut: () => Promise<void>;
};

const AppContext = createContext<AppContextValue | null>(null);

function identifier() {
  return globalThis.crypto?.randomUUID?.() ?? "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(/[xy]/g, (character) => {
    const value = Math.floor(Math.random() * 16);
    return (character === "x" ? value : (value & 3) | 8).toString(16);
  });
}

export function AppProvider({ children }: { children: ReactNode }) {
  const [progress, setProgress] = useState<LocalProgress>(emptyProgress);
  const [hydrated, setHydrated] = useState(false);
  const [user, setUser] = useState<User | null>(null);
  const [authReady, setAuthReady] = useState(!isSupabaseConfigured());
  const [theme, setThemeState] = useState<Theme>("system");
  const pathname = usePathname();
  const syncedUser = useRef<string | null>(null);

  useEffect(() => {
    try {
      const saved = localStorage.getItem(progressStorageKey);
      if (saved) setProgress({ ...emptyProgress, ...JSON.parse(saved) });
      const savedTheme = localStorage.getItem("exci-theme");
      if (savedTheme === "light" || savedTheme === "dark" || savedTheme === "system") {
        setThemeState(savedTheme);
      }
    } finally {
      setHydrated(true);
    }
  }, []);

  useEffect(() => {
    if (hydrated) localStorage.setItem(progressStorageKey, JSON.stringify(progress));
  }, [hydrated, progress]);

  useEffect(() => {
    const root = document.documentElement;
    const dark =
      theme === "dark" ||
      (theme === "system" && matchMedia("(prefers-color-scheme: dark)").matches);
    root.dataset.theme = dark ? "dark" : "light";
    root.style.colorScheme = dark ? "dark" : "light";
    if (hydrated) localStorage.setItem("exci-theme", theme);
  }, [hydrated, theme]);

  useEffect(() => {
    if (!isSupabaseConfigured()) return;
    const supabase = createClient();
    void supabase.auth.getUser().then(({ data }) => { setUser(data.user); setAuthReady(true); });
    const { data } = supabase.auth.onAuthStateChange((_event, session) => {
      setUser(session?.user ?? null);
      setAuthReady(true);
    });
    return () => data.subscription.unsubscribe();
  }, [pathname]);

  useEffect(() => {
    if (!hydrated || !user || !isSupabaseConfigured() || syncedUser.current === user.id) return;
    syncedUser.current = user.id;
    let cancelled = false;
    const supabase = createClient();
    void Promise.all([
      supabase.from("favorites").select("question_id"),
      supabase.from("answer_attempts").select("id,question_id,selected_answer_index,correct,mode,answered_at"),
      supabase.from("quiz_results").select("id,score,total,completed_at").eq("mode", "exam"),
    ]).then(async ([favoriteResult, attemptResult, examResult]) => {
      if (cancelled) return;
      const remoteFavorites = (favoriteResult.data ?? []).map((row) => row.question_id as string);
      const remoteAttempts: AnswerAttempt[] = (attemptResult.data ?? []).map((row) => ({
        id: row.id as string, questionId: row.question_id as string,
        selectedAnswerIndex: row.selected_answer_index as number, correct: row.correct as boolean,
        mode: row.mode as AnswerAttempt["mode"], answeredAt: row.answered_at as string,
      }));
      const remoteExams: ExamResult[] = (examResult.data ?? []).map((row) => ({
        id: row.id as string, score: row.score as number, total: row.total as number, completedAt: row.completed_at as string,
      }));
      const favorites = [...new Set([...progress.favorites, ...remoteFavorites])];
      const attempts = [...new Map([...remoteAttempts, ...progress.attempts].map((item) => [item.id, item])).values()];
      const exams = [...new Map([...remoteExams, ...progress.exams].map((item) => [item.id, item])).values()];
      setProgress((current) => ({ ...current, favorites, attempts, exams }));
      await Promise.all([
        favorites.length ? supabase.from("favorites").upsert(favorites.map((questionId) => ({ user_id: user.id, question_id: questionId }))) : Promise.resolve(),
        progress.attempts.length ? supabase.from("answer_attempts").upsert(progress.attempts.map((attempt) => ({ id: attempt.id, user_id: user.id, question_id: attempt.questionId, selected_answer_index: attempt.selectedAnswerIndex, correct: attempt.correct, mode: attempt.mode, answered_at: attempt.answeredAt }))) : Promise.resolve(),
        progress.exams.length ? supabase.from("quiz_results").upsert(progress.exams.map((exam) => ({ id: exam.id, user_id: user.id, mode: "exam", score: exam.score, total: exam.total, completed_at: exam.completedAt }))) : Promise.resolve(),
      ]);
    });
    return () => { cancelled = true; };
  }, [hydrated, user, progress]);

  const toggleFavorite = useCallback((questionId: string) => {
    const selected = progress.favorites.includes(questionId);
    setProgress((current) => {
      return {
        ...current,
        favorites: selected
          ? current.favorites.filter((id) => id !== questionId)
          : [...current.favorites, questionId],
      };
    });
    if (user && isSupabaseConfigured()) {
      const query = createClient().from("favorites");
      if (selected) void query.delete().eq("user_id", user.id).eq("question_id", questionId);
      else void query.upsert({ user_id: user.id, question_id: questionId });
    }
  }, [progress.favorites, user]);

  const recordAttempt = useCallback(
    (attempt: Omit<AnswerAttempt, "id" | "answeredAt">) => {
      const saved = { ...attempt, id: identifier(), answeredAt: new Date().toISOString() };
      setProgress((current) => ({
        ...current,
        attempts: [...current.attempts, saved],
      }));
      if (user && isSupabaseConfigured()) void createClient().from("answer_attempts").insert({ id: saved.id, user_id: user.id, question_id: saved.questionId, selected_answer_index: saved.selectedAnswerIndex, correct: saved.correct, mode: saved.mode, answered_at: saved.answeredAt });
    },
    [user],
  );

  const recordExam = useCallback(
    (result: Omit<ExamResult, "id" | "completedAt">) => {
      const saved = { ...result, id: identifier(), completedAt: new Date().toISOString() };
      setProgress((current) => ({
        ...current,
        exams: [...current.exams, saved],
      }));
      if (user && isSupabaseConfigured()) void createClient().from("quiz_results").insert({ id: saved.id, user_id: user.id, mode: "exam", score: saved.score, total: saved.total, completed_at: saved.completedAt });
    },
    [user],
  );

  const submitReport = useCallback(
    async (report: Omit<QuestionReport, "id" | "createdAt" | "synced">) => {
      const localReport: QuestionReport = {
        ...report,
        id: identifier(),
        createdAt: new Date().toISOString(),
        synced: false,
      };
      let synced = false;
      if (user && isSupabaseConfigured()) {
        const supabase = createClient();
        const { error } = await supabase.from("question_reports").insert({
          id: localReport.id,
          user_id: user.id,
          question_id: report.questionId,
          reason: report.reason,
          details: report.details || null,
        });
        synced = !error;
      }
      setProgress((current) => ({
        ...current,
        reports: [...current.reports, { ...localReport, synced }],
      }));
      return synced;
    },
    [user],
  );

  const setTheme = useCallback((value: Theme) => setThemeState(value), []);
  const signOut = useCallback(async () => {
    if (!isSupabaseConfigured()) return;
    await createClient().auth.signOut();
    setUser(null);
  }, []);

  const value = useMemo(
    () => ({
      progress,
      hydrated,
      authReady,
      user,
      theme,
      toggleFavorite,
      recordAttempt,
      recordExam,
      submitReport,
      setTheme,
      signOut,
    }),
    [progress, hydrated, authReady, user, theme, toggleFavorite, recordAttempt, recordExam, submitReport, setTheme, signOut],
  );

  return <AppContext.Provider value={value}>{children}</AppContext.Provider>;
}

export function useApp() {
  const context = useContext(AppContext);
  if (!context) throw new Error("useApp doit être utilisé dans AppProvider.");
  return context;
}
