package com.examen.civique.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class FavoriteQuestionRepositoryTest {
    private class PersistentFake : FavoriteQuestionRepository {
        val stored = MutableStateFlow<Set<String>>(emptySet())
        override fun observeFavoriteIds(): Flow<Set<String>> = stored
        override suspend fun setFavorite(questionId: String, favorite: Boolean) {
            stored.value = if (favorite) stored.value + questionId else stored.value - questionId
        }
    }

    @Test
    fun favoritesAreAddedRetrievedAndRemoved() = runBlocking {
        val repository = PersistentFake()
        repository.setFavorite("q1", true)
        repository.setFavorite("q2", true)
        assertEquals(setOf("q1", "q2"), repository.stored.value)
        repository.setFavorite("q1", false)
        assertEquals(setOf("q2"), repository.stored.value)
    }
}
