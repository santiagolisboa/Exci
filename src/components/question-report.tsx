"use client";

import { useState } from "react";
import { useApp } from "@/components/app-provider";
import { Icon } from "@/components/icons";

const reasons = [
  ["incorrect_question", "Question incorrecte"],
  ["wrong_answer", "Mauvaise réponse supposée"],
  ["ambiguous", "Formulation ambiguë"],
  ["outdated", "Information obsolète"],
  ["other", "Autre problème"],
];

export function QuestionReportButton({ questionId }: { questionId: string }) {
  const { submitReport, user } = useApp();
  const [open, setOpen] = useState(false);
  const [sent, setSent] = useState(false);
  const [sending, setSending] = useState(false);

  async function submit(formData: FormData) {
    setSending(true);
    await submitReport({ questionId, reason: String(formData.get("reason")), details: String(formData.get("details") ?? "") });
    setSending(false); setSent(true);
  }

  return <>
    <button className="report-trigger" onClick={() => { setSent(false); setOpen(true); }} type="button"><Icon name="flag" width={17} height={17} /> Signaler cette question</button>
    {open ? <div className="modal-backdrop" role="presentation" onMouseDown={(event) => { if (event.target === event.currentTarget) setOpen(false); }}><section aria-labelledby="report-title" aria-modal="true" className="report-modal" role="dialog"><button aria-label="Fermer" className="modal-close" onClick={() => setOpen(false)} type="button"><Icon name="close" width={20} height={20} /></button>{sent ? <div className="report-success"><span><Icon name="flag" width={26} height={26} /></span><h2 id="report-title">Merci pour votre signalement</h2><p>{user ? "Il a été enregistré et sera examiné." : "Il est conservé sur cet appareil. Connectez-vous pour le transmettre à l’équipe."}</p><button className="button" onClick={() => setOpen(false)} type="button">Fermer</button></div> : <><p className="eyebrow">Améliorer EXCI</p><h2 id="report-title">Signaler cette question</h2><p className="modal-help">Choisissez le problème constaté. Aucun compte n’est requis pour conserver votre signalement.</p><form action={(data) => void submit(data)}><label htmlFor="reason">Motif</label><select defaultValue="ambiguous" id="reason" name="reason">{reasons.map(([value,label]) => <option value={value} key={value}>{label}</option>)}</select><label htmlFor="details">Précisions <span>(facultatif)</span></label><textarea id="details" name="details" rows={4} maxLength={1000} placeholder="Expliquez brièvement le problème…" /><button className="button" disabled={sending} type="submit">{sending ? "Envoi…" : "Envoyer le signalement"}</button></form></>}</section></div> : null}
  </>;
}
