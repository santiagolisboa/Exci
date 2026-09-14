import type { Metadata } from "next";
import { AppProvider } from "@/components/app-provider";
import { AppShell } from "@/components/app-shell";
import "./globals.css";

export const metadata: Metadata = {
  title: { default: "EXCI — Préparation à l’examen civique", template: "%s — EXCI" },
  description: "Préparez l’examen civique français avec des questions officielles, des quiz et des examens blancs.",
};

export default function RootLayout({ children }: LayoutProps<"/">) {
  return (
    <html
      lang="fr"
      suppressHydrationWarning
      data-scroll-behavior="smooth"
    >
      <body><AppProvider><AppShell>{children}</AppShell></AppProvider></body>
    </html>
  );
}
