package com.examen.civique.domain.model

/** Provider-independent rules used when guest data is attached to an account. */
object SyncMergePolicy {
    fun mergeFavoriteIds(local: Set<String>, remote: Set<String>): Set<String> = local + remote

    fun <T> mergeUnique(local: List<T>, remote: List<T>, idOf: (T) -> String): List<T> =
        (remote + local).distinctBy(idOf)

    fun furthestCourseProgress(localCompletedLessons: Int, remoteCompletedLessons: Int): Int =
        maxOf(localCompletedLessons, remoteCompletedLessons)
}

