import { NextResponse } from "next/server";

import { getQuestion } from "@/lib/questions";
import { createClient } from "@/lib/supabase/server";

const reasons = new Set([
  "incorrect_question",
  "wrong_answer",
  "ambiguous",
  "typo",
  "technical",
  "other",
]);

const reasonLabels: Record<string, string> = {
  incorrect_question: "Question incorrecte",
  wrong_answer: "Mauvaise réponse supposée",
  ambiguous: "Formulation ambiguë",
  typo: "Faute ou coquille",
  technical: "Problème technique",
  other: "Autre",
};

type ReportPayload = {
  id?: unknown;
  questionId?: unknown;
  reason?: unknown;
  details?: unknown;
  createdAt?: unknown;
};

function validUuid(value: unknown): value is string {
  return typeof value === "string" && /^[0-9a-f]{8}-[0-9a-f]{4}-4[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$/iu.test(value);
}

function validDate(value: unknown): value is string {
  return typeof value === "string" && !Number.isNaN(Date.parse(value));
}

function escapeHtml(value: string) {
  return value.replace(/[&<>"']/gu, (character) => ({
    "&": "&amp;",
    "<": "&lt;",
    ">": "&gt;",
    "\"": "&quot;",
    "'": "&#039;",
  })[character] ?? character);
}

async function sendNotification(payload: {
  reporterEmail: string;
  questionId: string;
  questionPrompt: string;
  reason: string;
  details: string;
  createdAt: string;
}) {
  const apiKey = process.env.RESEND_API_KEY;
  const to = process.env.EXCI_REPORT_TO_EMAIL;
  const from = process.env.EXCI_REPORT_FROM_EMAIL;
  if (!apiKey || !to || !from) return false;

  const reason = reasonLabels[payload.reason];
  const details = payload.details || "Aucune précision fournie";
  const reportedAt = new Intl.DateTimeFormat("fr-FR", {
    dateStyle: "long",
    timeStyle: "short",
    timeZone: "Europe/Paris",
  }).format(new Date(payload.createdAt));

  const response = await fetch("https://api.resend.com/emails", {
    method: "POST",
    headers: {
      Authorization: `Bearer ${apiKey}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      from,
      to: [to],
      subject: `Nouveau signalement EXCI : ${reason}`,
      text: [
        "Nouveau signalement EXCI",
        "",
        `Question ${payload.questionId}`,
        payload.questionPrompt,
        "",
        `Motif : ${reason}`,
        `Précisions : ${details}`,
        `Signalé par : ${payload.reporterEmail}`,
        `Reçu le : ${reportedAt}`,
        "",
        "Ce message automatique a été envoyé par EXCI.",
      ].join("\n"),
      html: `<!doctype html>
<html lang="fr">
  <body style="margin:0;background:#f4f7f5;color:#14231b;font-family:Arial,sans-serif">
    <div style="display:none;max-height:0;overflow:hidden">Une question EXCI vient d'être signalée.</div>
    <table role="presentation" width="100%" cellspacing="0" cellpadding="0" style="background:#f4f7f5;padding:32px 12px">
      <tr><td align="center">
        <table role="presentation" width="100%" cellspacing="0" cellpadding="0" style="max-width:600px;background:#ffffff;border:1px solid #dce7e0;border-radius:16px;overflow:hidden">
          <tr><td style="background:#102019;padding:24px 28px;color:#ffffff;font-size:24px;font-weight:700">EXCI</td></tr>
          <tr><td style="padding:30px 28px">
            <div style="color:#2d9b67;font-size:13px;font-weight:700;letter-spacing:.08em;text-transform:uppercase">Signalement de question</div>
            <h1 style="margin:10px 0 22px;font-size:25px;line-height:1.25">Un nouveau signalement a été reçu</h1>
            <div style="margin-bottom:20px;padding:18px;background:#f4f7f5;border-radius:12px">
              <div style="margin-bottom:8px;color:#5b6c62;font-size:13px">Question ${escapeHtml(payload.questionId)}</div>
              <div style="font-size:17px;line-height:1.5;font-weight:600">${escapeHtml(payload.questionPrompt)}</div>
            </div>
            <p style="margin:0 0 10px;line-height:1.5"><strong>Motif :</strong> ${escapeHtml(reason)}</p>
            <p style="margin:0 0 10px;line-height:1.5"><strong>Précisions :</strong> ${escapeHtml(details)}</p>
            <p style="margin:0 0 10px;line-height:1.5"><strong>Signalé par :</strong> ${escapeHtml(payload.reporterEmail)}</p>
            <p style="margin:0;line-height:1.5"><strong>Reçu le :</strong> ${escapeHtml(reportedAt)}</p>
          </td></tr>
          <tr><td style="padding:18px 28px;background:#edf5f0;color:#5b6c62;font-size:12px;line-height:1.5">Message automatique envoyé par EXCI — pigeons.click</td></tr>
        </table>
      </td></tr>
    </table>
  </body>
</html>`,
    }),
    signal: AbortSignal.timeout(8_000),
  });

  if (!response.ok) {
    console.error("[question-report:notification]", { status: response.status });
  }
  return response.ok;
}

export async function POST(request: Request) {
  let body: ReportPayload;
  try {
    body = await request.json() as ReportPayload;
  } catch {
    return NextResponse.json({ error: "invalid_json" }, { status: 400 });
  }

  const details = typeof body.details === "string" ? body.details.trim() : "";
  const question = typeof body.questionId === "string" ? getQuestion(body.questionId) : undefined;
  if (
    !validUuid(body.id) ||
    !question ||
    typeof body.reason !== "string" ||
    !reasons.has(body.reason) ||
    details.length > 1000 ||
    !validDate(body.createdAt)
  ) {
    return NextResponse.json({ error: "invalid_report" }, { status: 400 });
  }

  const supabase = await createClient();
  const { data: { user }, error: userError } = await supabase.auth.getUser();
  if (userError || !user) {
    return NextResponse.json({ error: "authentication_required" }, { status: 401 });
  }

  const { data: existing, error: lookupError } = await supabase
    .from("question_reports")
    .select("id")
    .eq("id", body.id)
    .maybeSingle();
  if (lookupError) {
    console.error("[question-report:lookup]", { code: lookupError.code });
    return NextResponse.json({ error: "storage_failed" }, { status: 500 });
  }
  if (existing) return NextResponse.json({ saved: true, notified: false, duplicate: true });

  const { error: insertError } = await supabase.from("question_reports").insert({
    id: body.id,
    user_id: user.id,
    question_id: question.id,
    reason: body.reason,
    details: details || null,
    created_at: body.createdAt,
  });
  if (insertError) {
    console.error("[question-report:insert]", { code: insertError.code });
    return NextResponse.json({ error: "storage_failed" }, { status: 500 });
  }

  let notified = false;
  try {
    notified = await sendNotification({
      reporterEmail: user.email ?? user.id,
      questionId: question.id,
      questionPrompt: question.prompt,
      reason: body.reason,
      details,
      createdAt: body.createdAt,
    });
  } catch (error) {
    console.error("[question-report:notification]", { error: error instanceof Error ? error.name : "unknown" });
  }

  return NextResponse.json({ saved: true, notified }, { status: 201 });
}
