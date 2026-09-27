export function prepareQuestionSequence(
  currentIds: string[],
  availableIds: string[],
  currentIndex: number,
  completedIds: Iterable<string>,
) {
  const available = new Set(availableIds);
  const completed = new Set(completedIds);
  const seen = new Set<string>();
  const sequence: string[] = [];
  const safeIndex = Math.min(Math.max(0, currentIndex), Math.max(0, currentIds.length - 1));

  for (const id of currentIds.slice(0, safeIndex + 1)) {
    if (!available.has(id) || seen.has(id)) continue;
    seen.add(id);
    sequence.push(id);
  }

  const retainedIndex = Math.max(0, sequence.length - 1);

  for (const id of currentIds.slice(safeIndex + 1)) {
    if (!available.has(id) || seen.has(id) || completed.has(id)) continue;
    seen.add(id);
    sequence.push(id);
  }

  for (const id of availableIds) {
    if (seen.has(id) || completed.has(id)) continue;
    seen.add(id);
    sequence.push(id);
  }

  return { questionIds: sequence, index: retainedIndex };
}
