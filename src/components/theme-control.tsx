"use client";

import { Icon } from "@/components/icons";
import { useApp } from "@/components/app-provider";

export function ThemeControl() {
  const { theme, setTheme } = useApp();
  const next = theme === "system" ? "light" : theme === "light" ? "dark" : "system";
  const label = theme === "system" ? "Thème système" : theme === "light" ? "Thème clair" : "Thème sombre";
  return <button className="icon-button" onClick={() => setTheme(next)} title={`${label} — changer`} type="button"><Icon name="sun" width={20} height={20} /><span className="sr-only">{label}. Activer le thème suivant.</span></button>;
}
