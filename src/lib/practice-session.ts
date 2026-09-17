export const examDurationMs = 45 * 60 * 1000;

export function createExamDeadline(now = Date.now()) {
  return now + examDurationMs;
}

export function remainingExamTime(deadline: number, now = Date.now()) {
  if (!Number.isFinite(deadline)) return 0;
  return Math.max(0, deadline - now);
}

export function formatRemainingTime(milliseconds: number) {
  const seconds = Math.max(0, Math.ceil(milliseconds / 1000));
  return `${String(Math.floor(seconds / 60)).padStart(2, "0")}:${String(seconds % 60).padStart(2, "0")}`;
}

export function shuffledCopy<T>(items: readonly T[], random = Math.random) {
  const shuffled = [...items];
  for (let index = shuffled.length - 1; index > 0; index -= 1) {
    const target = Math.floor(random() * (index + 1));
    [shuffled[index], shuffled[target]] = [shuffled[target], shuffled[index]];
  }
  return shuffled;
}
