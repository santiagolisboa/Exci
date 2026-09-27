import assert from "node:assert/strict";
import { readFile } from "node:fs/promises";
import test from "node:test";

const actions = await readFile(new URL("../src/app/(exci)/auth/actions.ts", import.meta.url), "utf8");
const confirmRoute = await readFile(new URL("../src/app/(exci)/auth/confirm/route.ts", import.meta.url), "utf8");

test("la récupération de mot de passe utilise un retour interne explicite", () => {
  assert.match(actions, /resetPasswordForEmail/);
  assert.match(actions, /\/auth\/confirm\?next=\/auth\/update-password/);
  assert.match(confirmRoute, /next === "\/auth\/update-password"/);
});

test("le nouveau mot de passe exige une session et déconnecte après modification", () => {
  assert.match(actions, /auth\.getUser\(\)/);
  assert.match(actions, /auth\.updateUser\(\{ password/);
  assert.match(actions, /auth\.signOut\(\)/);
});
