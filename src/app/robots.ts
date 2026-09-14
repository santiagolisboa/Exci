import type { MetadataRoute } from "next";

export default function robots(): MetadataRoute.Robots {
  return {
    rules: {
      userAgent: "*",
      allow: "/",
    },
    sitemap: [
      "https://pigeons.click/sitemap.xml",
      "https://exci.pigeons.click/sitemap.xml",
    ],
  };
}
