import assert from "node:assert/strict";
import { readFileSync } from "node:fs";
import test from "node:test";

const questions = JSON.parse(readFileSync(new URL("../src/data/questions.json", import.meta.url), "utf8"));

test("la banque Web contient les 244 questions Android", () => {
  assert.equal(questions.length, 244);
  assert.equal(new Set(questions.map(({ id }) => id)).size, 244);
});

test("chaque question possède une réponse correcte et des métadonnées", () => {
  for (const question of questions) {
    assert.ok(question.id);
    assert.ok(question.prompt);
    assert.equal(question.answers.length, 4);
    assert.ok(question.correctAnswerIndex >= 0 && question.correctAnswerIndex < question.answers.length);
    assert.ok(question.answers[question.correctAnswerIndex]);
    assert.ok(question.categoryLabel);
    assert.ok(question.explanation);
    assert.equal(question.verified, true);
  }
});
