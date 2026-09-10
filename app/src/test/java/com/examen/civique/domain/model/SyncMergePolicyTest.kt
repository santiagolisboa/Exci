package com.examen.civique.domain.model

import org.junit.Assert.assertEquals
import org.junit.Test

class SyncMergePolicyTest {
    @Test fun favoritesAreUnioned() {
        assertEquals(setOf("a", "b", "c"), SyncMergePolicy.mergeFavoriteIds(setOf("a", "b"), setOf("b", "c")))
    }

    @Test fun uniqueRecordsAreMergedWithoutDuplicates() {
        data class Record(val id: String)
        val merged = SyncMergePolicy.mergeUnique(listOf(Record("local"), Record("same")), listOf(Record("remote"), Record("same"))) { it.id }
        assertEquals(setOf("local", "same", "remote"), merged.map { it.id }.toSet())
        assertEquals(3, merged.size)
    }

    @Test fun furthestCourseProgressWins() {
        assertEquals(8, SyncMergePolicy.furthestCourseProgress(8, 5))
    }
}

