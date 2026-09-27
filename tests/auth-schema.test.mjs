import assert from "node:assert/strict";
import { readFile } from "node:fs/promises";
import test from "node:test";

const baseMigration = await readFile(new URL("../supabase/migrations/202609140001_web_v1.sql", import.meta.url), "utf8");
const authMigration = await readFile(new URL("../supabase/migrations/202609180001_auth_profile_hardening.sql", import.meta.url), "utf8");
const sessionMigration = await readFile(new URL("../supabase/migrations/202609270002_active_practice_sessions.sql", import.meta.url), "utf8");
const grantsMigration = await readFile(new URL("../supabase/migrations/202609270003_authenticated_table_grants.sql", import.meta.url), "utf8");
const leastPrivilegeMigration = await readFile(new URL("../supabase/migrations/202609270004_database_least_privilege.sql", import.meta.url), "utf8");

test("les données de compte restent protégées par auth.uid()", () => {
  for (const table of ["profiles", "favorites", "course_progress", "quiz_results", "answer_attempts", "question_reports"]) {
    assert.match(baseMigration, new RegExp(`alter table public\\.${table} enable row level security`, "i"));
  }
  assert.match(baseMigration, /auth\.uid\(\) = id/i);
  assert.match(baseMigration, /auth\.uid\(\) = user_id/i);
});

test("les sessions actives sont privées et liées au compte", () => {
  assert.match(sessionMigration, /create table if not exists public\.active_practice_sessions/i);
  assert.match(sessionMigration, /primary key \(user_id, mode\)/i);
  assert.match(sessionMigration, /alter table public\.active_practice_sessions enable row level security/i);
  assert.match(sessionMigration, /auth\.uid\(\) = user_id/i);
});

test("le rôle authentifié peut utiliser les tables protégées par RLS", () => {
  for (const table of ["profiles", "favorites", "course_progress", "quiz_results", "answer_attempts", "question_reports", "active_practice_sessions"]) {
    assert.match(grantsMigration, new RegExp(`grant [^;]+ on table public\\.${table} to authenticated`, "i"));
  }
  assert.doesNotMatch(grantsMigration, /\bto anon\b/i);
  assert.doesNotMatch(grantsMigration, /service[_ -]?role/i);
});

test("les rôles web n'ont aucun privilège hors RLS", () => {
  for (const table of ["profiles", "favorites", "course_progress", "quiz_results", "answer_attempts", "question_reports", "active_practice_sessions"]) {
    assert.match(leastPrivilegeMigration, new RegExp(`revoke all on table public\\.${table} from anon, authenticated`, "i"));
  }
  assert.doesNotMatch(leastPrivilegeMigration, /grant [^;]*(truncate|trigger|references)/i);
  assert.match(leastPrivilegeMigration, /revoke execute on function public\.handle_new_user\(\) from public, anon, authenticated/i);
  assert.match(leastPrivilegeMigration, /revoke execute on function public\.rls_auto_enable\(\) from public, anon, authenticated/i);
  assert.match(leastPrivilegeMigration, /alter policy "question_reports_insert_own" on public\.question_reports to authenticated/i);
});

test("la migration de profil reprend les métadonnées sans clé privilégiée", () => {
  assert.match(authMigration, /raw_user_meta_data ->> 'display_name'/i);
  assert.match(authMigration, /profiles_display_name_valid/i);
  assert.doesNotMatch(`${baseMigration}\n${authMigration}`, /service[_ -]?role/i);
});
