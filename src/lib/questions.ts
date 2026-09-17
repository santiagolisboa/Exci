import questionData from "@/data/questions.json";
import { shuffledCopy } from "@/lib/practice-session";

export type Question = {
  id: string;
  prompt: string;
  answers: string[];
  correctAnswerIndex: number;
  category: string;
  categoryLabel: string;
  difficulty: "EASY" | "MEDIUM" | "HARD";
  explanation: string;
  official: boolean;
  sourceId: string;
  verified: boolean;
};

export const questions = questionData as Question[];

export function getQuestion(questionId: string) {
  return questions.find(({ id }) => id === questionId);
}

export function shuffledQuestions(count: number) {
  const shuffled = shuffledCopy(questions);
  return shuffled.slice(0, Math.max(0, Math.min(count, shuffled.length)));
}

export const categoryCount = new Set(questions.map(({ category }) => category)).size;
