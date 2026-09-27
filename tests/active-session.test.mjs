import assert from "node:assert/strict";
import test from "node:test";

import { activeSessionStorageKey, preferredActiveSession } from "../src/lib/active-session.ts";

const session = (sessionId, progressStep, updatedAt) => ({
  sessionId,
  progressStep,
  updatedAt,
  state: { index: progressStep },
});

test("la session la plus avancée gagne entre deux appareils", () => {
  const phone = session("phone", 9, "2026-09-27T18:00:00.000Z");
  const computer = session("computer", 3, "2026-09-27T18:01:00.000Z");
  assert.equal(preferredActiveSession(computer, phone), phone);
});

test("à avancement égal, la modification la plus récente gagne", () => {
  const older = session("same", 4, "2026-09-27T18:00:00.000Z");
  const newer = session("same", 4, "2026-09-27T18:01:00.000Z");
  assert.equal(preferredActiveSession(older, newer), newer);
});

test("le stockage local est isolé par compte", () => {
  assert.equal(activeSessionStorageKey("quiz", "user-a"), "exci-quiz-session-v2:user:user-a");
  assert.notEqual(activeSessionStorageKey("exam", "user-a"), activeSessionStorageKey("exam", "user-b"));
});
