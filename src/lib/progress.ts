export type PracticeMode = "quiz" | "exam";

export type AnswerAttempt = {
  id: string;
  questionId: string;
  selectedAnswerIndex: number;
  correct: boolean;
  mode: PracticeMode;
  answeredAt: string;
};

export type PracticeResult = {
  id: string;
  mode: PracticeMode;
  score: number;
  total: number;
  completedAt: string;
};

export type QuestionReport = {
  id: string;
  questionId: string;
  reason: string;
  details: string;
  createdAt: string;
  synced: boolean;
};

export type LocalProgress = {
  favorites: string[];
  favoriteRemovals: string[];
  attempts: AnswerAttempt[];
  results: PracticeResult[];
  reports: QuestionReport[];
};

export const emptyProgress: LocalProgress = {
  favorites: [],
  favoriteRemovals: [],
  attempts: [],
  results: [],
  reports: [],
};

export const progressStorageKey = "exci-progress-v1";

function isRecord(value: unknown): value is Record<string, unknown> {
  return typeof value === "object" && value !== null && !Array.isArray(value);
}

function isMode(value: unknown): value is PracticeMode {
  return value === "quiz" || value === "exam";
}

function validDate(value: unknown): value is string {
  return typeof value === "string" && !Number.isNaN(Date.parse(value));
}

export function sanitizeProgress(value: unknown): LocalProgress {
  if (!isRecord(value)) return { ...emptyProgress };

  const favorites = Array.isArray(value.favorites)
    ? [...new Set(value.favorites.filter((item): item is string => typeof item === "string" && item.length > 0))]
    : [];
  const favoriteRemovals = Array.isArray(value.favoriteRemovals)
    ? [...new Set(value.favoriteRemovals.filter((item): item is string => typeof item === "string" && item.length > 0))]
    : [];
  const attempts = Array.isArray(value.attempts)
    ? value.attempts.filter((item): item is AnswerAttempt =>
        isRecord(item) &&
        typeof item.id === "string" &&
        typeof item.questionId === "string" &&
        Number.isInteger(item.selectedAnswerIndex) &&
        (item.selectedAnswerIndex as number) >= 0 &&
        typeof item.correct === "boolean" &&
        isMode(item.mode) &&
        validDate(item.answeredAt),
      )
    : [];
  const legacyExams = Array.isArray(value.exams)
    ? value.exams.map((item) => isRecord(item) ? { ...item, mode: "exam" } : item)
    : [];
  const resultInput = Array.isArray(value.results) ? value.results : legacyExams;
  const results = resultInput.filter((item): item is PracticeResult =>
    isRecord(item) &&
    typeof item.id === "string" &&
    isMode(item.mode) &&
    Number.isInteger(item.score) &&
    (item.score as number) >= 0 &&
    Number.isInteger(item.total) &&
    (item.total as number) > 0 &&
    (item.score as number) <= (item.total as number) &&
    validDate(item.completedAt),
  );
  const reports = Array.isArray(value.reports)
    ? value.reports.filter((item): item is QuestionReport =>
        isRecord(item) &&
        typeof item.id === "string" &&
        typeof item.questionId === "string" &&
        typeof item.reason === "string" &&
        typeof item.details === "string" &&
        validDate(item.createdAt) &&
        typeof item.synced === "boolean",
      )
    : [];

  return {
    favorites,
    favoriteRemovals,
    attempts: [...new Map(attempts.map((item) => [item.id, item])).values()],
    results: [...new Map(results.map((item) => [item.id, item])).values()],
    reports: [...new Map(reports.map((item) => [item.id, item])).values()],
  };
}

export function mergeProgress(local: LocalProgress, remote: LocalProgress): LocalProgress {
  const favoriteRemovals = [...new Set([...remote.favoriteRemovals, ...local.favoriteRemovals])];
  return {
    favorites: [...new Set([...local.favorites, ...remote.favorites])].filter((id) => !favoriteRemovals.includes(id)),
    favoriteRemovals,
    attempts: [...new Map([...remote.attempts, ...local.attempts].map((item) => [item.id, item])).values()],
    results: [...new Map([...remote.results, ...local.results].map((item) => [item.id, item])).values()],
    reports: [...new Map([...remote.reports, ...local.reports].map((item) => [item.id, item])).values()],
  };
}

export function calculateStatistics(progress: LocalProgress) {
  const correct = progress.attempts.filter((attempt) => attempt.correct).length;
  const answered = progress.attempts.length;
  const uniqueAnswered = new Set(progress.attempts.map(({ questionId }) => questionId)).size;

  return {
    answered,
    correct,
    errors: answered - correct,
    successRate: answered ? Math.round((correct / answered) * 100) : 0,
    quizzes: progress.results.filter(({ mode }) => mode === "quiz").length,
    exams: progress.results.filter(({ mode }) => mode === "exam").length,
    uniqueAnswered,
  };
}
