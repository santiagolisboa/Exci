"use client";

import Link from "next/link";
import { useEffect, useRef, useState, type KeyboardEvent } from "react";
import { Icon } from "@/components/icons";
import { useApp } from "@/components/app-provider";

const dismissalKey = "exci-account-prompt-dismissed-at";

export function AccountOnboarding() {
  const { authReady, hydrated, user } = useApp();
  const [open, setOpen] = useState(false);
  const modalRef = useRef<HTMLElement>(null);

  useEffect(() => {
    if (!hydrated || !authReady || user || localStorage.getItem(dismissalKey)) return;
    const timer = window.setTimeout(() => setOpen(true), 900);
    return () => window.clearTimeout(timer);
  }, [authReady, hydrated, user]);

  useEffect(() => {
    if (!open) return;
    const previousOverflow = document.body.style.overflow;
    document.body.style.overflow = "hidden";
    return () => { document.body.style.overflow = previousOverflow; };
  }, [open]);

  function dismiss() {
    localStorage.setItem(dismissalKey, new Date().toISOString());
    setOpen(false);
  }

  function handleKeyDown(event: KeyboardEvent<HTMLElement>) {
    if (event.key === "Escape") {
      dismiss();
      return;
    }
    if (event.key !== "Tab") return;
    const focusable = modalRef.current?.querySelectorAll<HTMLElement>("a[href], button:not([disabled])");
    if (!focusable?.length) return;
    const first = focusable[0];
    const last = focusable[focusable.length - 1];
    if (event.shiftKey && document.activeElement === first) {
      event.preventDefault();
      last.focus();
    } else if (!event.shiftKey && document.activeElement === last) {
      event.preventDefault();
      first.focus();
    }
  }

  if (!open || user) return null;
  return <div className="modal-backdrop onboarding-backdrop" role="presentation" onMouseDown={(event) => { if (event.target === event.currentTarget) dismiss(); }}><section aria-labelledby="onboarding-title" aria-modal="true" className="onboarding-modal" onKeyDown={handleKeyDown} ref={modalRef} role="dialog"><button aria-label="Fermer et continuer sans compte" autoFocus className="modal-close" onClick={dismiss} type="button"><Icon name="close" width={20} height={20} /></button><div className="onboarding-mark" aria-hidden="true"><span className="brand-mark"><i /><i /><i /></span></div><p className="eyebrow">Votre progression, partout</p><h2 id="onboarding-title">Envie de garder une trace de vos efforts ?</h2><p className="onboarding-lead">Un compte gratuit vous permettra de retrouver votre préparation sur le Web, Android et bientôt iPhone.</p><ul><li><Icon name="chart" width={19} height={19} />Conserver votre progression et vos résultats</li><li><Icon name="heart" width={19} height={19} />Retrouver vos favoris et vos erreurs</li><li><Icon name="user" width={19} height={19} />Synchroniser votre avancement entre vos appareils</li></ul><div className="onboarding-actions"><Link className="button" href="/auth/sign-up">Créer un compte</Link><Link className="button secondary" href="/auth/login">J’ai déjà un compte</Link><button className="onboarding-skip" onClick={dismiss} type="button">Continuer sans compte</button></div></section></div>;
}
