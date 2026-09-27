import type { Metadata } from "next";

export const metadata: Metadata = {
  title: "Entraînement gratuit à l’examen civique",
  description: "Parcourez toutes les questions d’entraînement à l’examen civique français et reprenez à tout moment là où vous vous êtes arrêté.",
  alternates: { canonical: "/quiz" },
  openGraph: { title: "Entraînement à l’examen civique — EXCI", description: "Toutes les questions d’entraînement, avec sauvegarde de votre progression.", url: "/quiz" },
};

export default function QuizLayout({ children }: { children: React.ReactNode }) { return children; }
