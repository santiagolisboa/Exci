"use client";
import { Icon } from "@/components/icons";
export function PigeonsThemeButton({ label }: { label:string }) {
 function toggleTheme() { const root=document.documentElement; const next=root.dataset.theme==="dark"?"light":"dark"; root.dataset.theme=next; root.style.colorScheme=next; localStorage.setItem("pigeons-theme",next); }
 return <button aria-label={label} className="pigeons-theme-button" onClick={toggleTheme} type="button"><Icon name="sun" width={19} height={19} /></button>;
}
