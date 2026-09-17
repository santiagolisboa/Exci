import assert from "node:assert/strict";
import test from "node:test";
import { calculateStatistics, mergeProgress, sanitizeProgress } from "../src/lib/progress.ts";

const attempt = (id, questionId, correct) => ({
  id, questionId, correct, selectedAnswerIndex: 0, mode: "quiz", answeredAt: "2026-09-17T10:00:00.000Z",
});

test("le stockage historique des examens est migré sans perte", () => {
  const progress = sanitizeProgress({
    favorites: ["q1", "q1"], attempts: [], reports: [],
    exams: [{ id: "e1", score: 30, total: 40, completedAt: "2026-09-17T10:00:00.000Z" }],
  });
  assert.deepEqual(progress.favorites, ["q1"]);
  assert.equal(progress.results[0].mode, "exam");
});

test("la fusion déduplique les événements et respecte les suppressions de favoris", () => {
  const local = sanitizeProgress({ favorites: ["local", "removed"], favoriteRemovals: ["removed"], attempts: [attempt("same", "q1", true)], results: [], reports: [] });
  const remote = sanitizeProgress({ favorites: ["remote", "removed"], attempts: [attempt("same", "q1", true), attempt("remote", "q2", false)], results: [], reports: [] });
  const merged = mergeProgress(local, remote);
  assert.deepEqual(new Set(merged.favorites), new Set(["local", "remote"]));
  assert.equal(merged.attempts.length, 2);
});

test("les statistiques reposent uniquement sur les événements réels", () => {
  const progress = sanitizeProgress({
    favorites: [], reports: [],
    attempts: [attempt("a", "q1", true), attempt("b", "q1", false), attempt("c", "q2", true)],
    results: [
      { id: "r1", mode: "quiz", score: 7, total: 10, completedAt: "2026-09-17T10:00:00.000Z" },
      { id: "r2", mode: "exam", score: 30, total: 40, completedAt: "2026-09-17T11:00:00.000Z" },
    ],
  });
  assert.deepEqual(calculateStatistics(progress), { answered: 3, correct: 2, errors: 1, successRate: 67, quizzes: 1, exams: 1, uniqueAnswered: 2 });
});
