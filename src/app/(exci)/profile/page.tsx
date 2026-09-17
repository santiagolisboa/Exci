import { redirect } from "next/navigation";

import { ProfileForm } from "@/components/profile-form";
import { createClient } from "@/lib/supabase/server";

type Props = { searchParams: Promise<{ status?: string | string[] }> };

export default async function ProfilePage({ searchParams }: Props) {
  const supabase = await createClient();
  const { data: { user } } = await supabase.auth.getUser();
  if (!user) redirect("/auth/login");

  const { data: profile } = await supabase.from("profiles").select("display_name").eq("id", user.id).maybeSingle();
  const { status } = await searchParams;
  const displayName = profile?.display_name ?? user.user_metadata.display_name ?? "";

  return <section className="profile-page"><div className="profile-card card"><div className="auth-heading"><p className="eyebrow">Compte EXCI</p><h1>Mon profil</h1><p>Gérez le nom associé à votre progression synchronisée.</p></div><ProfileForm displayName={displayName} email={user.email ?? ""} saved={status === "updated"} /></div></section>;
}
