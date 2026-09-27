export type ActiveSession<T> = {
  sessionId: string;
  progressStep: number;
  updatedAt: string;
  state: T;
};

export function sessionIdentifier() {
  return globalThis.crypto?.randomUUID?.() ?? "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(/[xy]/g, (character) => {
    const value = Math.floor(Math.random() * 16);
    return (character === "x" ? value : (value & 3) | 8).toString(16);
  });
}

export function preferredActiveSession<T>(
  local: ActiveSession<T> | null,
  remote: ActiveSession<T> | null,
) {
  if (!local) return remote;
  if (!remote) return local;
  if (local.progressStep !== remote.progressStep) {
    return local.progressStep > remote.progressStep ? local : remote;
  }
  return Date.parse(local.updatedAt) >= Date.parse(remote.updatedAt) ? local : remote;
}

export function activeSessionStorageKey(mode: "quiz" | "exam", userId?: string) {
  const base = mode === "quiz" ? "exci-quiz-session-v2" : "exci-exam-session-v2";
  return userId ? `${base}:user:${userId}` : base;
}
