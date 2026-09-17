import type { Metadata } from "next";

export const metadata: Metadata = {
  title: "Examen blanc civique — 40 questions en 45 minutes",
  description: "Entraînez-vous gratuitement avec un examen blanc civique de 40 questions à réaliser en 45 minutes.",
  alternates: { canonical: "/exam" },
  openGraph: { title: "Examen blanc civique — EXCI", description: "40 questions en 45 minutes pour mesurer votre niveau.", url: "/exam" },
};

export default function ExamLayout({ children }: { children: React.ReactNode }) { return children; }
