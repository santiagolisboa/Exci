import assert from "node:assert/strict";
import { readFile } from "node:fs/promises";
import test from "node:test";

const nextConfig = await readFile(new URL("../next.config.ts", import.meta.url), "utf8");
const reportRoute = await readFile(new URL("../src/app/api/question-reports/route.ts", import.meta.url), "utf8");

test("les réponses exposent les principaux en-têtes de sécurité", () => {
  for (const header of ["X-Content-Type-Options", "Referrer-Policy", "Permissions-Policy", "X-Frame-Options"]) {
    assert.match(nextConfig, new RegExp(header, "i"));
  }
});

test("les signalements passent par une route authentifiée sans exposer la clé Resend", () => {
  assert.match(reportRoute, /supabase\.auth\.getUser\(\)/);
  assert.match(reportRoute, /process\.env\.RESEND_API_KEY/);
  assert.match(reportRoute, /https:\/\/api\.resend\.com\/emails/);
  assert.doesNotMatch(reportRoute, /re_[A-Za-z0-9]{20,}/);
});
