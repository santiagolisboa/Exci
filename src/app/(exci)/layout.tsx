import type { Metadata } from "next";
import { AppProvider } from "@/components/app-provider";
import { AppShell } from "@/components/app-shell";

export const metadata: Metadata = {
  metadataBase: new URL("https://exci.pigeons.click"),
  title: { default: "EXCI — Préparation à l’examen civique", template: "%s — EXCI" },
  description: "Préparez l’examen civique français avec des questions d’entraînement, des quiz et des examens blancs.",
  openGraph: {
    title: "EXCI — Préparation à l’examen civique",
    description: "Questions d’entraînement, quiz rapides et examens blancs pour préparer votre démarche sereinement.",
    url: "https://exci.pigeons.click",
    siteName: "EXCI",
    locale: "fr_FR",
    type: "website",
  },
  robots: { index: true, follow: true },
  category: "education",
};

export default function ExciLayout({ children }: { children: React.ReactNode }) {
  return <AppProvider><AppShell>{children}</AppShell></AppProvider>;
}
