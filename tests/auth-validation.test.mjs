import assert from "node:assert/strict";
import test from "node:test";
import { authErrorCode, authMessage, normalizeDisplayName, normalizeEmail, validateDisplayName, validateEmail, validateNewPassword } from "../src/lib/auth-validation.ts";

test("le nom affiché est normalisé et validé sans collecter d’information sensible", () => {
  assert.equal(normalizeDisplayName("  Marie   L.  "), "Marie L.");
  assert.equal(validateDisplayName("A"), "Saisissez au moins 2 caractères.");
  assert.equal(validateDisplayName("<script>"), "Ce nom contient des caractères non autorisés.");
  assert.equal(validateDisplayName("Santiago"), null);
});

test("l’email est normalisé et validé", () => {
  assert.equal(normalizeEmail("  TEST@EXAMPLE.FR "), "test@example.fr");
  assert.match(validateEmail("invalide") ?? "", /valide/);
  assert.equal(validateEmail("test@example.fr"), null);
});

test("un nouveau mot de passe exige 8 caractères sans règle de complexité arbitraire", () => {
  assert.match(validateNewPassword("1234567") ?? "", /8 caractères/);
  assert.equal(validateNewPassword("phrase longue"), null);
});

test("les erreurs Supabase sont traduites sans exposer de détail technique", () => {
  assert.equal(authErrorCode({ code: "email_not_confirmed" }, "login"), "email_not_confirmed");
  assert.equal(authErrorCode({ status: 429 }, "signup"), "rate_limited");
  assert.equal(authErrorCode({ code: "otp_expired" }, "confirmation"), "confirmation_expired");
  assert.doesNotMatch(authMessage("signup_failed"), /utilisateur existe/i);
});
