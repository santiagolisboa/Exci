export function completeQuestionSequence(currentIds: string[], availableIds: string[]) {
  const available = new Set(availableIds);
  const seen = new Set<string>();
  const completed: string[] = [];

  for (const id of currentIds) {
    if (!available.has(id) || seen.has(id)) continue;
    seen.add(id);
    completed.push(id);
  }

  for (const id of availableIds) {
    if (seen.has(id)) continue;
    seen.add(id);
    completed.push(id);
  }

  return completed;
}
