import Link from "next/link";
import { PigeonsThemeButton } from "@/components/pigeons-theme-button";

import { Icon } from "@/components/icons";
import { localeNames, locales, pigeonsCopy, type PigeonsLocale } from "@/lib/pigeons-i18n";

export function PigeonsLanding({ locale }: { locale: PigeonsLocale }) {
  const copy = pigeonsCopy[locale];

  return <div className="pigeons-site">
    <header className="pigeons-header"><div className="pigeons-header-inner">
      <Link className="pigeons-wordmark" href="/" aria-label="Pigeons — home"><span className="pigeon-glyph" aria-hidden="true"><i /></span>Pigeons</Link>
      <nav aria-label={copy.nav.label}><a href="#projects">{copy.nav.projects}</a><a href="#story">{copy.nav.story}</a><a href="#approach">{copy.nav.approach}</a></nav>
      <div className="pigeons-controls"><div className="locale-switcher" aria-label={copy.language}>{locales.map((item) => <Link aria-current={item === locale ? "page" : undefined} href={item === "fr" ? "/" : `/${item}`} key={item} title={localeNames[item]}>{item.toUpperCase()}</Link>)}</div><PigeonsThemeButton label={copy.theme} /></div>
    </div></header>

    <main>
      <section className="pigeons-hero"><div className="pigeons-hero-copy"><p className="pigeons-eyebrow">{copy.hero.eyebrow}</p><h1>{copy.hero.title}</h1><p className="pigeons-intro">{copy.hero.text}</p><div className="pigeons-hero-actions"><a className="pigeons-button" href="#story">{copy.hero.cta}<Icon name="arrow" width={19} height={19} /></a><span>{copy.hero.note}</span></div></div><div className="pigeon-scene" aria-hidden="true"><div className="scene-orbit"><span className="scene-pigeon"><i className="wing" /><i className="eye" /></span></div><span className="scene-caption">SANTIAGO / PIGEONS</span></div></section>

      <section className="pigeons-about" id="about"><p className="pigeons-eyebrow">{copy.about.eyebrow}</p><div><h2>{copy.about.title}</h2><p>{copy.about.text}</p><a className="founder-signature" href="https://www.linkedin.com/in/santiago-lisboa/" target="_blank" rel="noopener noreferrer">Santiago LISBOA</a></div></section>

      <section className="pigeons-projects" id="projects"><div className="pigeons-section-heading"><div><p className="pigeons-eyebrow">{copy.projects.eyebrow}</p><h2>{copy.projects.title}</h2></div><p>{copy.projects.intro}</p></div><article className="exci-project-card"><div className="exci-project-top"><span>{copy.projects.first}</span><strong>EXCI</strong></div><div className="exci-project-body"><div><h3>{copy.projects.exciTitle}</h3><p>{copy.projects.exciText}</p></div><div className="project-status"><span><i />{copy.projects.available}</span><span>{copy.projects.soon}</span></div></div><a className="pigeons-button light" href="https://exci.pigeons.click">{copy.projects.cta}<Icon name="arrow" width={19} height={19} /></a></article>
        <article className="exci-project-card aicha-project-card" aria-labelledby="aicha-project-title">
          <div className="exci-project-top"><span>{copy.projects.aicha.label}</span><strong>{copy.projects.aicha.name}</strong></div>
          <div className="exci-project-body"><div><h3 id="aicha-project-title">{copy.projects.aicha.title}</h3><p>{copy.projects.aicha.text}</p></div><div className="project-status"><span>{copy.projects.aicha.status}</span><span>{copy.projects.aicha.mobile}</span></div></div>
          <p className="aicha-project-note">{copy.projects.aicha.note}</p>
          <a className="pigeons-button light" href="https://aicha.pigeons.click">{copy.projects.aicha.cta}</a>
        </article>
      </section>

      <section className="pigeons-story" id="story" aria-labelledby="story-title">
        <header className="story-opening"><p className="pigeons-eyebrow">{copy.story.eyebrow}</p><h2 id="story-title">{copy.story.title}</h2><p>{copy.story.intro}</p></header>
        <blockquote>{copy.story.turningPoint}</blockquote>
        <div className="story-timeline">{copy.story.chapters.map((chapter, index) => <article className="story-chapter" key={chapter.kicker}><div className="story-marker"><span>{String(index + 1).padStart(2, "0")}</span><i /></div><div className="story-chapter-heading"><p>{chapter.kicker}</p><h3>{chapter.title}</h3></div><div className="story-chapter-copy">{chapter.paragraphs.map((paragraph) => <p key={paragraph}>{paragraph}</p>)}{"quote" in chapter && <strong>{chapter.quote}</strong>}</div></article>)}</div>
        <div className="sandbox-statement"><p className="pigeons-eyebrow">{copy.story.sandboxEyebrow}</p><h2>{copy.story.sandboxTitle}</h2><div><p>{copy.story.sandboxText}</p><ul>{copy.story.reasons.map((reason) => <li key={reason}>{reason}</li>)}</ul></div></div>
        <p className="story-closing">{copy.story.closing}</p>
      </section>

      <section className="pigeons-approach" id="approach"><div className="pigeons-section-heading"><div><p className="pigeons-eyebrow">{copy.approach.eyebrow}</p><h2>{copy.approach.title}</h2></div></div><div className="principle-grid">{copy.approach.items.map((item) => <article key={item.n}><span>{item.n}</span><h3>{item.title}</h3><p>{item.text}</p></article>)}</div></section>
    </main>
    <footer className="pigeons-footer"><div><Link className="pigeons-wordmark" href="/">Pigeons</Link><p>{copy.footer.line}</p></div><div><strong>{copy.footer.created.split("Santiago LISBOA")[0]}<a href="https://www.linkedin.com/in/santiago-lisboa/" target="_blank" rel="noopener noreferrer">Santiago LISBOA</a></strong><a href="https://exci.pigeons.click/mentions-legales">{copy.footer.legal}</a><span>{copy.footer.copyright}</span></div></footer>
  </div>;
}
