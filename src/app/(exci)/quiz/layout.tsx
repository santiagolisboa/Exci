import type { Metadata } from "next";

export const metadata: Metadata = {
  title: "Quiz gratuit de l’examen civique",
  description: "Testez vos connaissances avec un quiz gratuit de 10 questions pour préparer l’examen civique français de naturalisation.",
  alternates: { canonical: "/quiz" },
  openGraph: { title: "Quiz de l’examen civique — EXCI", description: "10 questions pour tester gratuitement vos connaissances.", url: "/quiz" },
};

export default function QuizLayout({ children }: { children: React.ReactNode }) { return children; }
