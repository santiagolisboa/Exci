"use client";

import Link from "next/link";
import { usePathname } from "next/navigation";
import type { ReactNode } from "react";
import { useApp } from "@/components/app-provider";
import { Icon } from "@/components/icons";
import { ThemeControl } from "@/components/theme-control";

const navigation = [
  { href: "/", label: "Accueil", icon: "home" },
  { href: "/quiz", label: "Quiz", icon: "quiz" },
  { href: "/exam", label: "Examen", icon: "exam" },
  { href: "/errors", label: "Erreurs", icon: "error" },
  { href: "/statistics", label: "Statistiques", icon: "chart" },
];

export function AppShell({ children }: { children: ReactNode }) {
  const pathname = usePathname();
  const { user } = useApp();
  const isFocusedFlow = pathname === "/quiz" || pathname === "/exam";
  return <div className="site-shell">
    <header className="site-header"><div className="header-inner">
      <Link className="brand" href="/" aria-label="EXCI — accueil"><span className="brand-mark" aria-hidden="true"><i /><i /><i /></span><span>EXCI</span></Link>
      {!isFocusedFlow ? <nav className="desktop-nav" aria-label="Navigation principale">{navigation.slice(0, 3).map((item) => <Link className={pathname === item.href ? "active" : ""} href={item.href} key={item.href}>{item.label}</Link>)}</nav> : <span className="focus-label">Session en cours</span>}
      <div className="header-actions"><ThemeControl />{user ? <Link className="account-button" href="/profile" title="Ouvrir mon profil"><Icon name="user" width={18} height={18} /><span className="account-email">{user.user_metadata.display_name ?? user.email}</span></Link> : <Link className="account-button" href="/auth/login"><Icon name="user" width={18} height={18} /><span>Connexion</span></Link>}</div>
    </div></header>
    <main className="site-content">{children}</main>
    {!isFocusedFlow ? <footer className="exci-footer"><div><Link className="brand" href="/"><span className="brand-mark" aria-hidden="true"><i /><i /><i /></span><span>EXCI</span></Link><span>Un projet Pigeons — créé par Santiago LISBOA</span></div><div className="footer-links"><Link href="/">Préparation</Link><a href="https://pigeons.click">Pigeons.click</a><span>© 2026 Pigeons</span></div></footer> : null}
    {!isFocusedFlow ? <nav className="mobile-nav" aria-label="Navigation mobile">{navigation.map((item) => <Link className={pathname === item.href ? "active" : ""} href={item.href} key={item.href}><Icon name={item.icon} width={22} height={22} /><span>{item.label}</span></Link>)}</nav> : null}
  </div>;
}
