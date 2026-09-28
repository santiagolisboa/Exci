import assert from "node:assert/strict";
import test from "node:test";

import { locales, pigeonsCopy } from "../src/lib/pigeons-i18n.ts";

test("l’histoire Pigeons existe dans les quatre langues", () => {
  assert.deepEqual(locales, ["fr", "en", "es", "de"]);
  for (const locale of locales) {
    const copy = pigeonsCopy[locale];
    assert.ok(copy.hero.title.length > 20);
    assert.equal(copy.story.chapters.length, 4);
    assert.equal(copy.story.reasons.length, 4);
    assert.match(copy.projects.exciText, /EXCI/);
    assert.match(copy.footer.created, /Santiago LISBOA/);
  }
});

test("le français conserve les repères personnels du récit", () => {
  const copy = pigeonsCopy.fr;
  assert.match(copy.hero.text, /mon espace personnel/);
  assert.match(copy.story.chapters[2].title, /quand je ne suis pas là/);
  assert.match(copy.story.sandboxTitle, /mon bac à sable/);
});
