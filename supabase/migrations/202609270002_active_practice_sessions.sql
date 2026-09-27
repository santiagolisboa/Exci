create table if not exists public.active_practice_sessions (
  user_id uuid not null references auth.users(id) on delete cascade,
  mode text not null check (mode in ('quiz', 'exam')),
  session_id uuid not null,
  state jsonb not null check (jsonb_typeof(state) = 'object'),
  progress_step integer not null default 0 check (progress_step >= 0),
  updated_at timestamptz not null default now(),
  primary key (user_id, mode)
);

alter table public.active_practice_sessions enable row level security;

do $$
begin
  if not exists (
    select 1
    from pg_policies
    where schemaname = 'public'
      and tablename = 'active_practice_sessions'
      and policyname = 'active_practice_sessions_own_all'
  ) then
    create policy "active_practice_sessions_own_all"
    on public.active_practice_sessions
    for all
    using (auth.uid() = user_id)
    with check (auth.uid() = user_id);
  end if;
end
$$;
