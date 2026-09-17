import { readFileSync, writeFileSync, mkdirSync } from "node:fs";
import { dirname, resolve } from "node:path";
import { fileURLToPath } from "node:url";
import { validateQuestions } from "./validate-questions.mjs";

const projectRoot = resolve(dirname(fileURLToPath(import.meta.url)), "..");
const sourcePath = resolve(
  projectRoot,
  "../android/app/src/main/java/com/examen/civique/data/local/QuestionsData.kt",
);
const outputPath = resolve(projectRoot, "src/data/questions.json");
const source = readFileSync(sourcePath, "utf8");

const categoryLabels = {
  PRINCIPES_VALEURS: "Principes et valeurs",
  SYSTEME_INSTITUTIONNEL_POLITIQUE: "Institutions et politique",
  GEOGRAPHIE: "Géographie",
  HISTOIRE: "Histoire et culture",
  SOCIETE: "Vie en société",
  ECONOMIE: "Travail et économie",
};

function decodeKotlinString(value) {
  return JSON.parse(`"${value.replace(/\\'/g, "'")}"`);
}

function splitArguments(value) {
  const parts = [];
  let current = "";
  let quoted = false;
  let escaped = false;

  for (const character of value) {
    if (escaped) {
      current += character;
      escaped = false;
      continue;
    }
    if (character === "\\") {
      current += character;
      escaped = true;
      continue;
    }
    if (character === '"') quoted = !quoted;
    if (character === "," && !quoted) {
      parts.push(current.trim());
      current = "";
    } else {
      current += character;
    }
  }
  parts.push(current.trim());
  return parts;
}

function quotedArgument(value) {
  if (!value.startsWith('"') || !value.endsWith('"')) {
    throw new Error(`Argument texte Kotlin inattendu: ${value}`);
  }
  return decodeKotlinString(value.slice(1, -1));
}

const questions = [];
const explicitPattern = /Question\(\s*id\s*=\s*"((?:\\.|[^"\\])*)",\s*question\s*=\s*"((?:\\.|[^"\\])*)",\s*answers\s*=\s*listOf\((.*?)\),\s*correctAnswerIndex\s*=\s*(\d+),\s*category\s*=\s*QuestionCategory\.([A-Z_]+),\s*type\s*=\s*QuestionType\.([A-Z_]+),\s*difficulty\s*=\s*DifficultyLevel\.([A-Z_]+),\s*explanation\s*=\s*"((?:\\.|[^"\\])*)",\s*lessonId\s*=\s*"((?:\\.|[^"\\])*)",\s*official\s*=\s*(true|false),\s*sourceId\s*=\s*"((?:\\.|[^"\\])*)",\s*verified\s*=\s*(true|false)\s*\)/gs;

for (const match of source.matchAll(explicitPattern)) {
  const answers = [...match[3].matchAll(/"((?:\\.|[^"\\])*)"/g)].map(
    (answer) => decodeKotlinString(answer[1]),
  );
  questions.push({
    id: decodeKotlinString(match[1]),
    prompt: decodeKotlinString(match[2]),
    answers,
    correctAnswerIndex: Number(match[4]),
    category: match[5],
    categoryLabel: categoryLabels[match[5]],
    difficulty: match[7],
    explanation: decodeKotlinString(match[8]),
    official: match[10] === "true",
    sourceId: decodeKotlinString(match[11]),
    verified: match[12] === "true",
  });
}

for (const line of source.split(/\r?\n/)) {
  const trimmed = line.trim();
  if (!trimmed.startsWith("officialQuestion(")) continue;
  const body = trimmed.replace(/^officialQuestion\(/, "").replace(/\),?$/, "");
  const args = splitArguments(body);
  if (args.length !== 8) throw new Error(`Question compacte invalide: ${trimmed}`);
  const category = args[6].replace("QuestionCategory.", "");
  questions.push({
    id: quotedArgument(args[0]),
    prompt: quotedArgument(args[1]),
    answers: args.slice(2, 6).map(quotedArgument),
    correctAnswerIndex: 0,
    category,
    categoryLabel: categoryLabels[category],
    difficulty: "MEDIUM",
    explanation: quotedArgument(args[7]),
    official: true,
    sourceId: "OFFICIAL_NATURALISATION_2026",
    verified: true,
  });
}

const ids = new Set(questions.map(({ id }) => id));
if (questions.length !== 244 || ids.size !== questions.length) {
  throw new Error(
    `Synchronisation incomplète: ${questions.length} questions, ${ids.size} identifiants uniques.`,
  );
}

const validationErrors = validateQuestions(questions);
if (validationErrors.length) {
  throw new Error(`Banque générée invalide:\n${validationErrors.join("\n")}`);
}

mkdirSync(dirname(outputPath), { recursive: true });
writeFileSync(outputPath, `${JSON.stringify(questions, null, 2)}\n`);
console.log(`${questions.length} questions Android synchronisées vers ${outputPath}.`);
