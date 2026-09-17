import type { Metadata } from "next";
export const metadata: Metadata = { title: "Mes erreurs", robots: { index: false, follow: false } };
export default function ErrorsLayout({ children }: { children: React.ReactNode }) { return children; }
