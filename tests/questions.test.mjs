import assert from "node:assert/strict";
import { readFileSync } from "node:fs";
import test from "node:test";
import { validateQuestions } from "../scripts/validate-questions.mjs";

const questions = JSON.parse(readFileSync(new URL("../src/data/questions.json", import.meta.url), "utf8"));

test("la banque Web contient les 244 questions Android", () => {
  assert.equal(questions.length, 244);
  assert.equal(new Set(questions.map(({ id }) => id)).size, 244);
});

test("la banque respecte intégralement le schéma et les invariants de réponses", () => {
  assert.deepEqual(validateQuestions(questions), []);
});

test("le validateur détecte les corruptions critiques", () => {
  const invalid = structuredClone(questions.slice(0, 2));
  invalid[1].id = invalid[0].id;
  invalid[1].prompt = "";
  invalid[1].answers[1] = invalid[1].answers[0];
  invalid[1].correctAnswerIndex = 9;
  const errors = validateQuestions(invalid).join("\n");
  assert.match(errors, /ID dupliqué/);
  assert.match(errors, /question vide/);
  assert.match(errors, /réponses dupliquées/);
  assert.match(errors, /index de bonne réponse invalide/);
});
