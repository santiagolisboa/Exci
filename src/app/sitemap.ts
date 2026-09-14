import type { MetadataRoute } from "next";

export default function sitemap(): MetadataRoute.Sitemap {
  const updated = new Date("2026-09-15");
  const pigeonsPages = ["", "/en", "/es", "/de"].map((path) => ({
    url: `https://pigeons.click${path}`,
    lastModified: updated,
    changeFrequency: "monthly" as const,
    priority: path === "" ? 1 : 0.8,
  }));

  return [
    ...pigeonsPages,
    {
      url: "https://exci.pigeons.click",
      lastModified: updated,
      changeFrequency: "weekly",
      priority: 1,
    },
  ];
}
