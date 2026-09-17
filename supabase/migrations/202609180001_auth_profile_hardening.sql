do $$
begin
  if not exists (
    select 1 from pg_constraint
    where conname = 'profiles_display_name_valid'
      and conrelid = 'public.profiles'::regclass
  ) then
    alter table public.profiles
      add constraint profiles_display_name_valid
      check (
        display_name is null
        or (
          char_length(btrim(display_name)) between 2 and 50
          and display_name !~ '[<>[:cntrl:]]'
        )
      ) not valid;
  end if;
end;
$$;

create or replace function public.handle_new_user()
returns trigger
language plpgsql
security definer set search_path = ''
as $$
declare
  candidate text;
begin
  candidate := btrim(coalesce(
    new.raw_user_meta_data ->> 'display_name',
    new.raw_user_meta_data ->> 'full_name',
    new.raw_user_meta_data ->> 'name'
  ));

  if char_length(candidate) not between 2 and 50 or candidate ~ '[<>[:cntrl:]]' then
    candidate := null;
  end if;

  insert into public.profiles (id, display_name)
  values (new.id, candidate)
  on conflict (id) do update
    set display_name = coalesce(public.profiles.display_name, excluded.display_name),
        updated_at = now();
  return new;
end;
$$;
