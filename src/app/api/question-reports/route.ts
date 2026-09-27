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

  const response = await fetch("https://api.resend.com/emails", {
    method: "POST",
    headers: {
      Authorization: `Bearer ${apiKey}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      from,
      to: [to],
      subject: `[EXCI] Signalement — ${reasonLabels[payload.reason]}`,
      text: [
        "Un utilisateur a signalé une question EXCI.",
        "",
        `Question : ${payload.questionId}`,
        payload.questionPrompt,
        "",
        `Motif : ${reasonLabels[payload.reason]}`,
        `Précisions : ${payload.details || "Aucune"}`,
        `Compte : ${payload.reporterEmail}`,
        `Date : ${payload.createdAt}`,
      ].join("\n"),
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
