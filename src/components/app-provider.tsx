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
  mergeProgress,
  progressStorageKey,
  sanitizeProgress,
  type AnswerAttempt,
  type LocalProgress,
  type PracticeResult,
  type QuestionReport,
} from "@/lib/progress";
import { createClient, isSupabaseConfigured } from "@/lib/supabase/client";

type Theme = "light" | "dark" | "system";

type AppContextValue = {
  progress: LocalProgress;
  hydrated: boolean;
  authReady: boolean;
  user: User | null;
  syncStatus: "local" | "syncing" | "synced" | "offline" | "error";
  theme: Theme;
  toggleFavorite: (questionId: string) => void;
  recordAttempt: (attempt: Omit<AnswerAttempt, "id" | "answeredAt">) => void;
  recordResult: (result: Omit<PracticeResult, "id" | "completedAt">) => void;
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

function storedProgress(key: string) {
  try {
    return sanitizeProgress(JSON.parse(localStorage.getItem(key) ?? "null"));
  } catch {
    localStorage.removeItem(key);
    return { ...emptyProgress };
  }
}

function userProgressKey(userId: string) {
  return `${progressStorageKey}:user:${userId}`;
}

export function AppProvider({ children }: { children: ReactNode }) {
  const [progress, setProgress] = useState<LocalProgress>(emptyProgress);
  const [hydrated, setHydrated] = useState(false);
  const [user, setUser] = useState<User | null>(null);
  const [syncStatus, setSyncStatus] = useState<AppContextValue["syncStatus"]>("local");
  const [syncVersion, setSyncVersion] = useState(0);
  const [authReady, setAuthReady] = useState(!isSupabaseConfigured());
  const [theme, setThemeState] = useState<Theme>("system");
  const pathname = usePathname();
  const syncedUser = useRef<string | null>(null);
  const progressRef = useRef(progress);

  useEffect(() => {
    progressRef.current = progress;
  }, [progress]);

  useEffect(() => {
    try {
      setProgress(storedProgress(progressStorageKey));
      const savedTheme = localStorage.getItem("exci-theme");
      if (savedTheme === "light" || savedTheme === "dark" || savedTheme === "system") {
        setThemeState(savedTheme);
      }
    } finally {
      setHydrated(true);
    }
  }, []);

  useEffect(() => {
    if (hydrated) localStorage.setItem(user ? userProgressKey(user.id) : progressStorageKey, JSON.stringify(progress));
  }, [hydrated, progress, user]);

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
    void supabase.auth.getUser().then(({ data }) => {
      if (!data.user) {
        setProgress(storedProgress(progressStorageKey));
        setSyncStatus("local");
      }
      setUser(data.user);
      setAuthReady(true);
    });
    const { data } = supabase.auth.onAuthStateChange((_event, session) => {
      if (!session?.user) {
        setProgress(storedProgress(progressStorageKey));
        setSyncStatus("local");
      }
      setUser(session?.user ?? null);
      setAuthReady(true);
    });
    return () => data.subscription.unsubscribe();
  }, [pathname]);

  useEffect(() => {
    const retry = () => {
      syncedUser.current = null;
      setSyncVersion((value) => value + 1);
    };
    window.addEventListener("online", retry);
    return () => window.removeEventListener("online", retry);
  }, []);

  useEffect(() => {
    if (!hydrated || !isSupabaseConfigured()) return;
    if (!user) {
      syncedUser.current = null;
      return;
    }
    if (syncedUser.current === user.id) return;
    if (!navigator.onLine) {
      queueMicrotask(() => setSyncStatus("offline"));
      return;
    }
    let cancelled = false;
    const supabase = createClient();
    const local = mergeProgress(progressRef.current, storedProgress(userProgressKey(user.id)));
    queueMicrotask(() => setSyncStatus("syncing"));
    void Promise.all([
      supabase.from("favorites").select("question_id"),
      supabase.from("answer_attempts").select("id,question_id,selected_answer_index,correct,mode,answered_at"),
      supabase.from("quiz_results").select("id,mode,score,total,completed_at"),
      supabase.from("question_reports").select("id,question_id,reason,details,created_at"),
    ]).then(async ([favoriteResult, attemptResult, practiceResult, reportResult]) => {
      if (cancelled) return;
      const readError = favoriteResult.error ?? attemptResult.error ?? practiceResult.error ?? reportResult.error;
      if (readError) throw readError;
      const remoteFavorites = (favoriteResult.data ?? []).map((row) => row.question_id as string);
      const remoteAttempts: AnswerAttempt[] = (attemptResult.data ?? []).map((row) => ({
        id: row.id as string, questionId: row.question_id as string,
        selectedAnswerIndex: row.selected_answer_index as number, correct: row.correct as boolean,
        mode: row.mode as AnswerAttempt["mode"], answeredAt: row.answered_at as string,
      }));
      const remoteResults: PracticeResult[] = (practiceResult.data ?? []).map((row) => ({
        id: row.id as string, mode: row.mode as PracticeResult["mode"], score: row.score as number, total: row.total as number, completedAt: row.completed_at as string,
      }));
      const remoteReports: QuestionReport[] = (reportResult.data ?? []).map((row) => ({
        id: row.id as string, questionId: row.question_id as string, reason: row.reason as string,
        details: (row.details as string | null) ?? "", createdAt: row.created_at as string, synced: true,
      }));
      const merged = mergeProgress(local, {
        favorites: remoteFavorites,
        favoriteRemovals: [],
        attempts: remoteAttempts,
        results: remoteResults,
        reports: remoteReports,
      });
      const writes = await Promise.all([
        merged.favorites.length ? supabase.from("favorites").upsert(merged.favorites.map((questionId) => ({ user_id: user.id, question_id: questionId }))) : Promise.resolve({ error: null }),
        local.attempts.length ? supabase.from("answer_attempts").upsert(local.attempts.map((attempt) => ({ id: attempt.id, user_id: user.id, question_id: attempt.questionId, selected_answer_index: attempt.selectedAnswerIndex, correct: attempt.correct, mode: attempt.mode, answered_at: attempt.answeredAt }))) : Promise.resolve({ error: null }),
        local.results.length ? supabase.from("quiz_results").upsert(local.results.map((result) => ({ id: result.id, user_id: user.id, mode: result.mode, score: result.score, total: result.total, completed_at: result.completedAt }))) : Promise.resolve({ error: null }),
        local.reports.filter(({ synced }) => !synced).length ? supabase.from("question_reports").upsert(local.reports.filter(({ synced }) => !synced).map((report) => ({ id: report.id, user_id: user.id, question_id: report.questionId, reason: report.reason, details: report.details || null, created_at: report.createdAt })), { onConflict: "id", ignoreDuplicates: true }) : Promise.resolve({ error: null }),
        local.favoriteRemovals.length ? supabase.from("favorites").delete().eq("user_id", user.id).in("question_id", local.favoriteRemovals) : Promise.resolve({ error: null }),
      ]);
      const writeError = writes.find((result) => result.error)?.error;
      if (writeError) throw writeError;
      const syncedReportIds = new Set(local.reports.map(({ id }) => id));
      setProgress({
        ...merged,
        favoriteRemovals: [],
        reports: merged.reports.map((report) => syncedReportIds.has(report.id) ? { ...report, synced: true } : report),
      });
      localStorage.removeItem(progressStorageKey);
      syncedUser.current = user.id;
      setSyncStatus("synced");
    }).catch(() => {
      if (!cancelled) setSyncStatus(navigator.onLine ? "error" : "offline");
    });
    return () => { cancelled = true; };
  }, [hydrated, user, syncVersion]);

  const toggleFavorite = useCallback((questionId: string) => {
    const selected = progress.favorites.includes(questionId);
    setProgress((current) => {
      return {
        ...current,
        favorites: selected
          ? current.favorites.filter((id) => id !== questionId)
          : [...current.favorites, questionId],
        favoriteRemovals: selected
          ? [...new Set([...current.favoriteRemovals, questionId])]
          : current.favoriteRemovals.filter((id) => id !== questionId),
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

  const recordResult = useCallback(
    (result: Omit<PracticeResult, "id" | "completedAt">) => {
      const saved = { ...result, id: identifier(), completedAt: new Date().toISOString() };
      setProgress((current) => ({
        ...current,
        results: [...current.results, saved],
      }));
      if (user && isSupabaseConfigured()) void createClient().from("quiz_results").insert({ id: saved.id, user_id: user.id, mode: saved.mode, score: saved.score, total: saved.total, completed_at: saved.completedAt }).then(({ error }) => { if (error) setSyncStatus(navigator.onLine ? "error" : "offline"); });
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
      syncStatus,
      theme,
      toggleFavorite,
      recordAttempt,
      recordResult,
      submitReport,
      setTheme,
      signOut,
    }),
    [progress, hydrated, authReady, user, syncStatus, theme, toggleFavorite, recordAttempt, recordResult, submitReport, setTheme, signOut],
  );

  return <AppContext.Provider value={value}>{children}</AppContext.Provider>;
}

export function useApp() {
  const context = useContext(AppContext);
  if (!context) throw new Error("useApp doit être utilisé dans AppProvider.");
  return context;
}
