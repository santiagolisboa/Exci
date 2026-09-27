import assert from "node:assert/strict";
import test from "node:test";
import { GET } from "../src/app/ads.txt/route.ts";

test("ads.txt permet la vérification avant l'activation des annonces", async () => {
  const previousClient = process.env.NEXT_PUBLIC_ADSENSE_CLIENT;
  const previousEnabled = process.env.NEXT_PUBLIC_ADSENSE_ENABLED;
  process.env.NEXT_PUBLIC_ADSENSE_CLIENT = "ca-pub-1234567890123456";
  process.env.NEXT_PUBLIC_ADSENSE_ENABLED = "false";

  try {
    const response = GET(new Request("https://exci.pigeons.click/ads.txt"));
    assert.equal(response.status, 200);
    assert.equal(await response.text(), "google.com, pub-1234567890123456, DIRECT, f08c47fec0942fa0\n");
  } finally {
    if (previousClient === undefined) delete process.env.NEXT_PUBLIC_ADSENSE_CLIENT;
    else process.env.NEXT_PUBLIC_ADSENSE_CLIENT = previousClient;
    if (previousEnabled === undefined) delete process.env.NEXT_PUBLIC_ADSENSE_ENABLED;
    else process.env.NEXT_PUBLIC_ADSENSE_ENABLED = previousEnabled;
  }
});

test("ads.txt est aussi publié sur le domaine racine vérifié par AdSense", async () => {
  const previousClient = process.env.NEXT_PUBLIC_ADSENSE_CLIENT;
  process.env.NEXT_PUBLIC_ADSENSE_CLIENT = "ca-pub-1234567890123456";

  try {
    const response = GET(new Request("https://pigeons.click/ads.txt"));
    assert.equal(response.status, 200);
    assert.equal(await response.text(), "google.com, pub-1234567890123456, DIRECT, f08c47fec0942fa0\n");
  } finally {
    if (previousClient === undefined) delete process.env.NEXT_PUBLIC_ADSENSE_CLIENT;
    else process.env.NEXT_PUBLIC_ADSENSE_CLIENT = previousClient;
  }
});
