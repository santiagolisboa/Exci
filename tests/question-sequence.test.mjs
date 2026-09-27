import assert from "node:assert/strict";
import test from "node:test";

import { completeQuestionSequence } from "../src/lib/question-sequence.ts";

test("une ancienne série est prolongée avec toutes les questions restantes", () => {
  assert.deepEqual(
    completeQuestionSequence(["q3", "q1"], ["q1", "q2", "q3", "q4"]),
    ["q3", "q1", "q2", "q4"],
  );
});

test("la séquence ignore les questions supprimées et les doublons", () => {
  assert.deepEqual(
    completeQuestionSequence(["q2", "old", "q2"], ["q1", "q2", "q3"]),
    ["q2", "q1", "q3"],
  );
});
