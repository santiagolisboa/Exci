import assert from "node:assert/strict";
import { readFile } from "node:fs/promises";
import test from "node:test";

const baseMigration = await readFile(new URL("../supabase/migrations/202609140001_web_v1.sql", import.meta.url), "utf8");
const authMigration = await readFile(new URL("../supabase/migrations/202609180001_auth_profile_hardening.sql", import.meta.url), "utf8");

test("les données de compte restent protégées par auth.uid()", () => {
  for (const table of ["profiles", "favorites", "course_progress", "quiz_results", "answer_attempts", "question_reports"]) {
    assert.match(baseMigration, new RegExp(`alter table public\\.${table} enable row level security`, "i"));
  }
  assert.match(baseMigration, /auth\.uid\(\) = id/i);
  assert.match(baseMigration, /auth\.uid\(\) = user_id/i);
});

test("la migration de profil reprend les métadonnées sans clé privilégiée", () => {
  assert.match(authMigration, /raw_user_meta_data ->> 'display_name'/i);
  assert.match(authMigration, /profiles_display_name_valid/i);
  assert.doesNotMatch(`${baseMigration}\n${authMigration}`, /service[_ -]?role/i);
});
