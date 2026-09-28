create table if not exists public.user_products (
  user_id uuid not null references auth.users(id) on delete cascade,
  product text not null check (product in ('exci', 'aisha')),
  activated_at timestamptz not null default now(),
  primary key (user_id, product)
);

create table if not exists public.aisha_profiles (
  user_id uuid primary key references auth.users(id) on delete cascade,
  interface_locale text not null default 'fr' check (interface_locale in ('fr', 'en', 'es', 'de')),
  speech_rate numeric(3,2) not null default 0.90 check (speech_rate between 0.50 and 1.50),
  initial_level integer check (initial_level between 0 and 9),
  assessment_completed_at timestamptz,
  created_at timestamptz not null default now(),
  updated_at timestamptz not null default now()
);

create table if not exists public.aisha_learning_progress (
  user_id uuid not null references auth.users(id) on delete cascade,
  lesson_id text not null,
  status text not null default 'started' check (status in ('started', 'completed')),
  started_at timestamptz not null default now(),
  completed_at timestamptz,
  updated_at timestamptz not null default now(),
  primary key (user_id, lesson_id)
);

create table if not exists public.aisha_exercise_attempts (
  id uuid primary key,
  user_id uuid not null references auth.users(id) on delete cascade,
  lesson_id text not null,
  exercise_id text not null,
  type text not null,
  skill text not null,
  answer jsonb,
  correct boolean not null,
  attempts integer not null default 1 check (attempts > 0),
  listen_count integer not null default 0 check (listen_count >= 0),
  hint_count integer not null default 0 check (hint_count >= 0),
  duration_ms integer check (duration_ms >= 0),
  completed_at timestamptz not null default now()
);

create index if not exists aisha_attempts_user_completed_idx on public.aisha_exercise_attempts (user_id, completed_at desc);
alter table public.user_products enable row level security;
alter table public.aisha_profiles enable row level security;
alter table public.aisha_learning_progress enable row level security;
alter table public.aisha_exercise_attempts enable row level security;

create policy "user_products_own_all" on public.user_products for all to authenticated using (auth.uid() = user_id) with check (auth.uid() = user_id);
create policy "aisha_profiles_own_all" on public.aisha_profiles for all to authenticated using (auth.uid() = user_id) with check (auth.uid() = user_id);
create policy "aisha_learning_progress_own_all" on public.aisha_learning_progress for all to authenticated using (auth.uid() = user_id) with check (auth.uid() = user_id);
create policy "aisha_exercise_attempts_own_all" on public.aisha_exercise_attempts for all to authenticated using (auth.uid() = user_id) with check (auth.uid() = user_id);

revoke all on table public.user_products from anon, authenticated;
revoke all on table public.aisha_profiles from anon, authenticated;
revoke all on table public.aisha_learning_progress from anon, authenticated;
revoke all on table public.aisha_exercise_attempts from anon, authenticated;
grant select, insert, update, delete on table public.user_products to authenticated;
grant select, insert, update, delete on table public.aisha_profiles to authenticated;
grant select, insert, update, delete on table public.aisha_learning_progress to authenticated;
grant select, insert on table public.aisha_exercise_attempts to authenticated;
