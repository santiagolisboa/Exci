import questionData from "@/data/questions.json";

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
  return [...questions]
    .sort(() => Math.random() - 0.5)
    .slice(0, Math.min(count, questions.length));
}

export const categoryCount = new Set(questions.map(({ category }) => category)).size;
