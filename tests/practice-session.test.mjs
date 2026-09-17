import assert from "node:assert/strict";
import test from "node:test";
import { createExamDeadline, examDurationMs, formatRemainingTime, remainingExamTime, shuffledCopy } from "../src/lib/practice-session.ts";

test("l’échéance d’examen reste absolue après un rafraîchissement", () => {
  const startedAt = 1_000_000;
  const deadline = createExamDeadline(startedAt);
  assert.equal(deadline, startedAt + 45 * 60 * 1000);
  assert.equal(remainingExamTime(deadline, startedAt + 10 * 60 * 1000), 35 * 60 * 1000);
  assert.equal(remainingExamTime(deadline, deadline + 1), 0);
  assert.equal(examDurationMs, 2_700_000);
});

test("le timer est affiché sans devenir négatif", () => {
  assert.equal(formatRemainingTime(45 * 60 * 1000), "45:00");
  assert.equal(formatRemainingTime(1), "00:01");
  assert.equal(formatRemainingTime(-10), "00:00");
});

test("la randomisation conserve chaque question une seule fois", () => {
  const values = Array.from({ length: 40 }, (_, index) => index);
  const sequence = [0.1, 0.9, 0.3, 0.7];
  let cursor = 0;
  const shuffled = shuffledCopy(values, () => sequence[cursor++ % sequence.length]);
  assert.equal(shuffled.length, 40);
  assert.equal(new Set(shuffled).size, 40);
  assert.deepEqual([...shuffled].sort((a, b) => a - b), values);
  assert.notDeepEqual(shuffled, values);
});
