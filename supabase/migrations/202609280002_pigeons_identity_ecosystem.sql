alter table public.profiles add column if not exists account_origin text;
alter table public.profiles add column if not exists newsletter_opt_in boolean not null default false;
alter table public.profiles add column if not exists newsletter_updated_at timestamptz;
alter table public.profiles add column if not exists privacy_policy_version text;
alter table public.profiles add column if not exists privacy_accepted_at timestamptz;
alter table public.profiles add constraint profiles_account_origin_check check (account_origin in ('exci', 'aisha', 'pigeons'));
alter table public.user_products add column if not exists last_used_at timestamptz not null default now();

create table public.pigeons_consent_events (
  id uuid primary key default gen_random_uuid(),
  user_id uuid not null references auth.users(id) on delete cascade,
  consent_type text not null check (consent_type in ('privacy', 'newsletter')),
  granted boolean not null,
  policy_version text,
  source_product text not null check (source_product in ('exci', 'aisha', 'pigeons')),
  created_at timestamptz not null default now()
);

create index pigeons_consent_events_user_created_idx on public.pigeons_consent_events (user_id, created_at desc);
alter table public.pigeons_consent_events enable row level security;
create policy "pigeons_consent_events_select_own" on public.pigeons_consent_events for select to authenticated using (auth.uid() = user_id);
create policy "pigeons_consent_events_insert_own" on public.pigeons_consent_events for insert to authenticated with check (auth.uid() = user_id);
revoke all on table public.pigeons_consent_events from anon, authenticated;
grant select, insert on table public.pigeons_consent_events to authenticated;

create or replace function public.handle_new_user()
returns trigger
language plpgsql
security definer set search_path = ''
as $$
declare
  origin text;
  newsletter boolean;
begin
  origin := case when new.raw_user_meta_data ->> 'account_origin' in ('exci', 'aisha', 'pigeons') then new.raw_user_meta_data ->> 'account_origin' else null end;
  newsletter := coalesce((new.raw_user_meta_data ->> 'newsletter_opt_in')::boolean, false);
  insert into public.profiles (id, display_name, account_origin, newsletter_opt_in, newsletter_updated_at, privacy_policy_version, privacy_accepted_at)
  values (new.id, nullif(new.raw_user_meta_data ->> 'display_name', ''), origin, newsletter, case when new.raw_user_meta_data ? 'newsletter_opt_in' then now() else null end, nullif(new.raw_user_meta_data ->> 'privacy_policy_version', ''), nullif(new.raw_user_meta_data ->> 'privacy_accepted_at', '')::timestamptz)
  on conflict (id) do update set
    display_name = coalesce(public.profiles.display_name, excluded.display_name),
    account_origin = coalesce(public.profiles.account_origin, excluded.account_origin),
    newsletter_opt_in = excluded.newsletter_opt_in,
    newsletter_updated_at = excluded.newsletter_updated_at,
    privacy_policy_version = coalesce(public.profiles.privacy_policy_version, excluded.privacy_policy_version),
    privacy_accepted_at = coalesce(public.profiles.privacy_accepted_at, excluded.privacy_accepted_at),
    updated_at = now();
  if origin in ('exci', 'aisha') then
    insert into public.user_products (user_id, product) values (new.id, origin)
    on conflict (user_id, product) do update set last_used_at = now();
  end if;
  return new;
end;
$$;
