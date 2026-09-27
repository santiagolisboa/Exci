"use client";

import { useEffect, useRef, useState, type KeyboardEvent } from "react";
import { useApp } from "@/components/app-provider";
import { Icon } from "@/components/icons";

const reasons = [
  ["incorrect_question", "Question incorrecte"],
  ["wrong_answer", "Mauvaise réponse supposée"],
  ["ambiguous", "Formulation ambiguë"],
  ["typo", "Faute ou coquille"],
  ["technical", "Problème technique"],
  ["other", "Autre"],
];

export function QuestionReportButton({ questionId }: { questionId: string }) {
  const { submitReport, user } = useApp();
  const [open, setOpen] = useState(false);
  const [sent, setSent] = useState(false);
  const [synced, setSynced] = useState(false);
  const [sending, setSending] = useState(false);
  const modalRef = useRef<HTMLElement>(null);
  const triggerRef = useRef<HTMLButtonElement>(null);

  useEffect(() => {
    if (!open) return;
    const trigger = triggerRef.current;
    const previousOverflow = document.body.style.overflow;
    document.body.style.overflow = "hidden";
    return () => {
      document.body.style.overflow = previousOverflow;
      trigger?.focus();
    };
  }, [open]);

  function handleKeyDown(event: KeyboardEvent<HTMLElement>) {
    if (event.key === "Escape") {
      setOpen(false);
      return;
    }
    if (event.key !== "Tab") return;
    const focusable = modalRef.current?.querySelectorAll<HTMLElement>("button:not([disabled]), select:not([disabled]), textarea:not([disabled])");
    if (!focusable?.length) return;
    const first = focusable[0];
    const last = focusable[focusable.length - 1];
    if (event.shiftKey && document.activeElement === first) { event.preventDefault(); last.focus(); }
    else if (!event.shiftKey && document.activeElement === last) { event.preventDefault(); first.focus(); }
  }

  async function submit(formData: FormData) {
    setSending(true);
    const wasSynced = await submitReport({ questionId, reason: String(formData.get("reason")), details: String(formData.get("details") ?? "") });
    setSynced(wasSynced); setSending(false); setSent(true);
  }

  return <>
    <button className="report-trigger" onClick={() => { setSent(false); setSynced(false); setOpen(true); }} ref={triggerRef} type="button"><Icon name="flag" width={17} height={17} /> Signaler cette question</button>
    {open ? <div className="modal-backdrop" role="presentation" onMouseDown={(event) => { if (event.target === event.currentTarget) setOpen(false); }}><section aria-labelledby="report-title" aria-modal="true" className="report-modal" onKeyDown={handleKeyDown} ref={modalRef} role="dialog"><button aria-label="Fermer" autoFocus className="modal-close" onClick={() => setOpen(false)} type="button"><Icon name="close" width={20} height={20} /></button>{sent ? <div className="report-success"><span><Icon name="flag" width={26} height={26} /></span><h2 id="report-title">Merci pour votre signalement</h2><p>{synced ? "Il a bien été transmis et enregistré." : user ? "Il reste conservé sur cet appareil et sera retransmis lors d’une prochaine connexion à votre compte." : "Il reste conservé sur cet appareil et sera transmis lorsque vous connecterez un compte."}</p><button className="button" onClick={() => setOpen(false)} type="button">Fermer</button></div> : <><p className="eyebrow">Améliorer EXCI</p><h2 id="report-title">Signaler cette question</h2><p className="modal-help">Avec un compte connecté, le signalement est transmis immédiatement. Sinon, il reste sur cet appareil jusqu’à votre prochaine connexion à un compte.</p><form action={(data) => void submit(data)}><label htmlFor={`reason-${questionId}`}>Motif</label><select defaultValue="ambiguous" id={`reason-${questionId}`} name="reason" required>{reasons.map(([value,label]) => <option value={value} key={value}>{label}</option>)}</select><label htmlFor={`details-${questionId}`}>Précisions <span>(facultatif)</span></label><textarea id={`details-${questionId}`} name="details" rows={4} maxLength={1000} placeholder="Expliquez brièvement le problème…" /><button className="button" disabled={sending} type="submit">{sending ? "Envoi…" : "Envoyer le signalement"}</button></form></>}</section></div> : null}
  </>;
}
