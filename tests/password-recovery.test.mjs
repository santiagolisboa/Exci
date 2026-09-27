import assert from "node:assert/strict";
import { readFile } from "node:fs/promises";
import test from "node:test";

const actions = await readFile(new URL("../src/app/(exci)/auth/actions.ts", import.meta.url), "utf8");
const confirmRoute = await readFile(new URL("../src/app/(exci)/auth/confirm/route.ts", import.meta.url), "utf8");
const recoveryPage = await readFile(new URL("../src/app/(exci)/auth/recovery/page.tsx", import.meta.url), "utf8");

test("la récupération de mot de passe passe par une page de confirmation explicite", () => {
  assert.match(actions, /resetPasswordForEmail/);
  assert.match(actions, /redirectTo: `\$\{origin\}\/auth\/recovery`/);
  assert.match(recoveryPage, /action=\{confirmPasswordRecovery\}/);
  assert.match(recoveryPage, /type="hidden" value=\{tokenHash\}/);
  assert.match(actions, /auth\.verifyOtp\(\{/);
  assert.match(actions, /type: "recovery"/);
});

test("la route historique de confirmation limite toujours les redirections internes", () => {
  assert.match(confirmRoute, /next === "\/auth\/update-password"/);
});

test("le nouveau mot de passe exige une session et déconnecte après modification", () => {
  assert.match(actions, /auth\.getUser\(\)/);
  assert.match(actions, /auth\.updateUser\(\{ password/);
  assert.match(actions, /auth\.signOut\(\)/);
});
