-- Product-finalization hardening. Anonymous reports remain local until the user
-- signs in; this avoids opening an unauthenticated write policy that is easy to spam.

alter table public.question_reports
  drop constraint if exists question_reports_reason_check;

alter table public.question_reports
  add constraint question_reports_reason_check
  check (reason in ('incorrect_question', 'wrong_answer', 'ambiguous', 'typo', 'technical', 'other'));

alter table public.answer_attempts
  drop constraint if exists answer_attempts_selected_answer_index_check;

alter table public.answer_attempts
  add constraint answer_attempts_selected_answer_index_check
  check (selected_answer_index between 0 and 7);

create index if not exists answer_attempts_user_answered_at_idx
  on public.answer_attempts (user_id, answered_at desc);

create index if not exists quiz_results_user_completed_at_idx
  on public.quiz_results (user_id, completed_at desc);

create index if not exists question_reports_user_created_at_idx
  on public.question_reports (user_id, created_at desc);
