"use client";

import Link from "next/link";
import { Icon } from "@/components/icons";
import { localeNames, locales, pigeonsCopy, type PigeonsLocale } from "@/lib/pigeons-i18n";

export function PigeonsLanding({ locale }: { locale: PigeonsLocale }) {
  const copy = pigeonsCopy[locale];
  function toggleTheme() {
    const root = document.documentElement;
    const next = root.dataset.theme === "dark" ? "light" : "dark";
    root.dataset.theme = next; root.style.colorScheme = next;
    localStorage.setItem("pigeons-theme", next);
  }
  return <div className="pigeons-site">
    <header className="pigeons-header"><div className="pigeons-header-inner"><Link className="pigeons-wordmark" href="/" aria-label="Pigeons — home"><span className="pigeon-glyph" aria-hidden="true"><i /></span>Pigeons</Link><nav aria-label="Primary navigation"><a href="#projects">{copy.nav.projects}</a><a href="#approach">{copy.nav.approach}</a><a href="#about">{copy.nav.about}</a></nav><div className="pigeons-controls"><div className="locale-switcher" aria-label={copy.language}>{locales.map((item) => <Link aria-current={item === locale ? "page" : undefined} href={item === "fr" ? "/" : `/${item}`} key={item} title={localeNames[item]}>{item.toUpperCase()}</Link>)}</div><button aria-label={copy.theme} className="pigeons-theme-button" onClick={toggleTheme} type="button"><Icon name="sun" width={19} height={19} /></button></div></div></header>
    <main>
      <section className="pigeons-hero"><div className="pigeons-hero-copy"><p className="pigeons-eyebrow">{copy.hero.eyebrow}</p><h1>{copy.hero.title}</h1><p className="pigeons-intro">{copy.hero.text}</p><div className="pigeons-hero-actions"><a className="pigeons-button" href="#projects">{copy.hero.cta}<Icon name="arrow" width={19} height={19} /></a><span>{copy.hero.note}</span></div></div><div className="pigeon-scene" aria-hidden="true"><div className="scene-orbit"><span className="scene-pigeon"><i className="wing" /><i className="eye" /></span></div><span className="scene-caption">P / 01</span></div></section>
      <section className="pigeons-about" id="about"><p className="pigeons-eyebrow">{copy.about.eyebrow}</p><div><h2>{copy.about.title}</h2><p>{copy.about.text}</p></div></section>
      <section className="pigeons-projects" id="projects"><div className="pigeons-section-heading"><div><p className="pigeons-eyebrow">{copy.projects.eyebrow}</p><h2>{copy.projects.title}</h2></div><p>{copy.projects.intro}</p></div><article className="exci-project-card"><div className="exci-project-top"><span>{copy.projects.current}</span><strong>EXCI</strong></div><div className="exci-project-body"><div><h3>{copy.projects.exciTitle}</h3><p>{copy.projects.exciText}</p></div><div className="project-status"><span><i />{copy.projects.available}</span><span>{copy.projects.soon}</span></div></div><a className="pigeons-button light" href="https://exci.pigeons.click">{copy.projects.cta}<Icon name="arrow" width={19} height={19} /></a></article></section>
      <section className="pigeons-approach" id="approach"><div className="pigeons-section-heading"><div><p className="pigeons-eyebrow">{copy.approach.eyebrow}</p><h2>{copy.approach.title}</h2></div></div><div className="principle-grid">{copy.approach.items.map((item) => <article key={item.n}><span>{item.n}</span><h3>{item.title}</h3><p>{item.text}</p></article>)}</div></section>
    </main>
    <footer className="pigeons-footer"><div><Link className="pigeons-wordmark" href="/">Pigeons</Link><p>{copy.footer.line}</p></div><div><strong>{copy.footer.created}</strong><span>{copy.footer.copyright}</span></div></footer>
  </div>;
}
