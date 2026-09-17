import { readFileSync } from "node:fs";
import { dirname, resolve } from "node:path";
import { fileURLToPath } from "node:url";

const expectedKeys = [
  "answers", "category", "categoryLabel", "correctAnswerIndex", "difficulty",
  "explanation", "id", "official", "prompt", "sourceId", "verified",
];
const difficulties = new Set(["EASY", "MEDIUM", "HARD"]);

function normalized(value) {
  return value.normalize("NFD").replace(/[\u0300-\u036f]/g, "").toLocaleLowerCase("fr")
    .replace(/[^a-z0-9]+/g, " ").trim().replace(/\s+/g, " ");
}

export function validateQuestions(value) {
  const errors = [];
  if (!Array.isArray(value)) return ["La racine JSON doit être un tableau."];
  const ids = new Map();
  const prompts = new Map();

  value.forEach((question, index) => {
    const at = `Question #${index + 1}`;
    if (!question || typeof question !== "object" || Array.isArray(question)) {
      errors.push(`${at}: structure invalide.`);
      return;
    }
    const keys = Object.keys(question).sort();
    if (JSON.stringify(keys) !== JSON.stringify(expectedKeys)) errors.push(`${at}: champs JSON inattendus ou manquants.`);
    if (typeof question.id !== "string" || !question.id.trim()) errors.push(`${at}: ID manquant.`);
    else {
      if (ids.has(question.id)) errors.push(`${at}: ID dupliqué « ${question.id} » (déjà vu #${ids.get(question.id)}).`);
      ids.set(question.id, index + 1);
    }
    if (typeof question.prompt !== "string" || !question.prompt.trim()) errors.push(`${at}: question vide.`);
    else {
      const key = normalized(question.prompt);
      if (prompts.has(key)) errors.push(`${at}: texte de question dupliqué après normalisation (${prompts.get(key)}).`);
      prompts.set(key, question.id || at);
    }
    if (typeof question.category !== "string" || !question.category.trim()) errors.push(`${at}: catégorie vide.`);
    if (typeof question.categoryLabel !== "string" || !question.categoryLabel.trim()) errors.push(`${at}: label de catégorie vide.`);
    if (!Array.isArray(question.answers)) errors.push(`${at}: réponses invalides.`);
    else {
      if (question.answers.length !== 4) errors.push(`${at}: 4 réponses sont attendues, ${question.answers.length} reçues.`);
      const answers = question.answers.map((answer) => typeof answer === "string" ? normalized(answer) : "");
      if (answers.some((answer) => !answer)) errors.push(`${at}: réponse vide ou invalide.`);
      if (new Set(answers).size !== answers.length) errors.push(`${at}: réponses dupliquées (la bonne réponse ne peut pas aussi être une mauvaise réponse).`);
      if (!Number.isInteger(question.correctAnswerIndex) || question.correctAnswerIndex < 0 || question.correctAnswerIndex >= question.answers.length) errors.push(`${at}: index de bonne réponse invalide.`);
      else if (!answers[question.correctAnswerIndex]) errors.push(`${at}: bonne réponse vide.`);
      if (question.answers.length < 2) errors.push(`${at}: mauvaises réponses manquantes.`);
    }
    if (!difficulties.has(question.difficulty)) errors.push(`${at}: difficulté invalide.`);
    if (typeof question.explanation !== "string" || !question.explanation.trim()) errors.push(`${at}: explication vide.`);
    if (typeof question.sourceId !== "string" || !question.sourceId.trim()) errors.push(`${at}: identifiant de provenance vide.`);
    if (typeof question.official !== "boolean" || typeof question.verified !== "boolean") errors.push(`${at}: indicateurs de provenance invalides.`);
  });
  return errors;
}

const scriptPath = fileURLToPath(import.meta.url);
if (resolve(process.argv[1] ?? "") === scriptPath) {
  const projectRoot = resolve(dirname(scriptPath), "..");
  const questions = JSON.parse(readFileSync(resolve(projectRoot, "src/data/questions.json"), "utf8"));
  const errors = validateQuestions(questions);
  if (errors.length) {
    console.error(errors.join("\n"));
    process.exitCode = 1;
  } else {
    const categories = Object.fromEntries(Object.entries(Object.groupBy(questions, ({ category }) => category)).map(([category, items]) => [category, items.length]));
    console.log(JSON.stringify({ valid: true, total: questions.length, uniqueIds: new Set(questions.map(({ id }) => id)).size, categories }, null, 2));
  }
}
