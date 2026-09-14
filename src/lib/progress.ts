export type PracticeMode = "quiz" | "exam";

export type AnswerAttempt = {
  id: string;
  questionId: string;
  selectedAnswerIndex: number;
  correct: boolean;
  mode: PracticeMode;
  answeredAt: string;
};

export type ExamResult = {
  id: string;
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
  attempts: AnswerAttempt[];
  exams: ExamResult[];
  reports: QuestionReport[];
};

export const emptyProgress: LocalProgress = {
  favorites: [],
  attempts: [],
  exams: [],
  reports: [],
};

export const progressStorageKey = "exci-progress-v1";

export function calculateStatistics(progress: LocalProgress) {
  const correct = progress.attempts.filter((attempt) => attempt.correct).length;
  const answered = progress.attempts.length;
  const uniqueAnswered = new Set(progress.attempts.map(({ questionId }) => questionId)).size;

  return {
    answered,
    correct,
    errors: answered - correct,
    successRate: answered ? Math.round((correct / answered) * 100) : 0,
    exams: progress.exams.length,
    uniqueAnswered,
  };
}
