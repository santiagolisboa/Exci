-- Remove privileges inherited from broad/default grants. RLS does not protect
-- TRUNCATE, TRIGGER or REFERENCES, so browser roles must never receive them.
revoke all on table public.profiles from anon, authenticated;
revoke all on table public.favorites from anon, authenticated;
revoke all on table public.course_progress from anon, authenticated;
revoke all on table public.quiz_results from anon, authenticated;
revoke all on table public.answer_attempts from anon, authenticated;
revoke all on table public.question_reports from anon, authenticated;
revoke all on table public.active_practice_sessions from anon, authenticated;

grant select, insert, update on table public.profiles to authenticated;
grant select, insert, update, delete on table public.favorites to authenticated;
grant select, insert, update, delete on table public.course_progress to authenticated;
grant select, insert, update on table public.quiz_results to authenticated;
grant select, insert, update on table public.answer_attempts to authenticated;
grant select, insert on table public.question_reports to authenticated;
grant select, insert, update, delete on table public.active_practice_sessions to authenticated;

-- These SECURITY DEFINER functions are invoked by database triggers, never by
-- a browser client. PostgreSQL otherwise grants EXECUTE to PUBLIC by default.
revoke execute on function public.handle_new_user() from public, anon, authenticated;
revoke execute on function public.rls_auto_enable() from public, anon, authenticated;

-- Keep policies out of the anonymous role entirely, in addition to their
-- auth.uid() predicates.
alter policy "profiles_select_own" on public.profiles to authenticated;
alter policy "profiles_insert_own" on public.profiles to authenticated;
alter policy "profiles_update_own" on public.profiles to authenticated;
alter policy "favorites_own_all" on public.favorites to authenticated;
alter policy "course_progress_own_all" on public.course_progress to authenticated;
alter policy "quiz_results_own_all" on public.quiz_results to authenticated;
alter policy "answer_attempts_own_all" on public.answer_attempts to authenticated;
alter policy "question_reports_insert_own" on public.question_reports to authenticated;
alter policy "question_reports_select_own" on public.question_reports to authenticated;
alter policy "active_practice_sessions_own_all" on public.active_practice_sessions to authenticated;
