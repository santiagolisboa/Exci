import assert from "node:assert/strict";
import test from "node:test";

import { prepareQuestionSequence } from "../src/lib/question-sequence.ts";

test("une ancienne série est prolongée sans reproposer les questions déjà faites", () => {
  assert.deepEqual(
    prepareQuestionSequence(["q3", "q1"], ["q1", "q2", "q3", "q4"], 1, ["q2"]),
    { questionIds: ["q3", "q1", "q4"], index: 1 },
  );
});

test("la reprise conserve la question actuelle même si elle vient d’être validée", () => {
  assert.deepEqual(
    prepareQuestionSequence(["q2", "q1", "q3"], ["q1", "q2", "q3", "q4"], 1, ["q1", "q3"]),
    { questionIds: ["q2", "q1", "q4"], index: 1 },
  );
});

test("la séquence ignore les questions supprimées et les doublons", () => {
  assert.deepEqual(
    prepareQuestionSequence(["q2", "old", "q2"], ["q1", "q2", "q3"], 2, []),
    { questionIds: ["q2", "q1", "q3"], index: 0 },
  );
});
