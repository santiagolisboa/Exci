create table if not exists public.profiles (
  id uuid primary key references auth.users(id) on delete cascade,
  display_name text,
  created_at timestamptz not null default now(),
  updated_at timestamptz not null default now()
);

create table if not exists public.favorites (
  user_id uuid not null references auth.users(id) on delete cascade,
  question_id text not null,
  created_at timestamptz not null default now(),
  primary key (user_id, question_id)
);

create table if not exists public.course_progress (
  user_id uuid not null references auth.users(id) on delete cascade,
  lesson_id text not null,
  completed_questions integer not null default 0 check (completed_questions >= 0),
  total_questions integer not null default 0 check (total_questions >= 0),
  updated_at timestamptz not null default now(),
  primary key (user_id, lesson_id)
);

create table if not exists public.quiz_results (
  id uuid primary key default gen_random_uuid(),
  user_id uuid not null references auth.users(id) on delete cascade,
  mode text not null check (mode in ('quiz', 'exam')),
  score integer not null check (score >= 0),
  total integer not null check (total > 0 and score <= total),
  completed_at timestamptz not null default now()
);

create table if not exists public.answer_attempts (
  id uuid primary key default gen_random_uuid(),
  user_id uuid not null references auth.users(id) on delete cascade,
  question_id text not null,
  selected_answer_index integer not null check (selected_answer_index >= 0),
  correct boolean not null,
  mode text not null check (mode in ('quiz', 'exam')),
  answered_at timestamptz not null default now()
);

create table if not exists public.question_reports (
  id uuid primary key default gen_random_uuid(),
  user_id uuid not null references auth.users(id) on delete cascade,
  question_id text not null,
  reason text not null check (reason in ('incorrect_question', 'wrong_answer', 'ambiguous', 'outdated', 'other')),
  details text check (char_length(details) <= 1000),
  status text not null default 'pending' check (status in ('pending', 'reviewing', 'resolved', 'rejected')),
  created_at timestamptz not null default now()
);

alter table public.profiles enable row level security;
alter table public.favorites enable row level security;
alter table public.course_progress enable row level security;
alter table public.quiz_results enable row level security;
alter table public.answer_attempts enable row level security;
alter table public.question_reports enable row level security;

create policy "profiles_select_own" on public.profiles for select using (auth.uid() = id);
create policy "profiles_insert_own" on public.profiles for insert with check (auth.uid() = id);
create policy "profiles_update_own" on public.profiles for update using (auth.uid() = id) with check (auth.uid() = id);
create policy "favorites_own_all" on public.favorites for all using (auth.uid() = user_id) with check (auth.uid() = user_id);
create policy "course_progress_own_all" on public.course_progress for all using (auth.uid() = user_id) with check (auth.uid() = user_id);
create policy "quiz_results_own_all" on public.quiz_results for all using (auth.uid() = user_id) with check (auth.uid() = user_id);
create policy "answer_attempts_own_all" on public.answer_attempts for all using (auth.uid() = user_id) with check (auth.uid() = user_id);
create policy "question_reports_insert_own" on public.question_reports for insert with check (auth.uid() = user_id);
create policy "question_reports_select_own" on public.question_reports for select using (auth.uid() = user_id);

create or replace function public.handle_new_user()
returns trigger
language plpgsql
security definer set search_path = ''
as $$
begin
  insert into public.profiles (id) values (new.id) on conflict (id) do nothing;
  return new;
end;
$$;

do $$
begin
  if not exists (select 1 from pg_trigger where tgname = 'on_auth_user_created') then
    create trigger on_auth_user_created after insert on auth.users for each row execute procedure public.handle_new_user();
  end if;
end;
$$;
