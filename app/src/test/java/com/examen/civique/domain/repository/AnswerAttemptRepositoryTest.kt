package com.examen.civique.domain.repository

import com.examen.civique.domain.model.AnswerAttempt
import com.examen.civique.domain.model.AnswerSource
import com.examen.civique.domain.model.QuestionCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AnswerAttemptRepositoryTest {
    private class Fake : AnswerAttemptRepository {
        private val attempts = MutableStateFlow<List<AnswerAttempt>>(emptyList())
        override suspend fun recordAttempt(attempt: AnswerAttempt): Long {
            val id = attempts.value.size + 1L
            attempts.value = listOf(attempt.copy(id = id)) + attempts.value
            return id
        }
        override fun observeAll(): Flow<List<AnswerAttempt>> = attempts
        override fun observeForQuestion(questionId: String) = attempts.map { rows -> rows.filter { it.questionId == questionId } }
        override fun observeForCategory(category: QuestionCategory) = attempts.map { rows -> rows.filter { it.category == category } }
        fun snapshot() = attempts.value
    }

    @Test
    fun correctAndIncorrectAttemptsArePersistedIndividually() = runBlocking {
        val repository = Fake()
        val base = AnswerAttempt(
            questionId = "q1", category = QuestionCategory.HISTOIRE,
            selectedAnswer = "a", isCorrect = true, answeredAt = 1,
            source = AnswerSource.QUIZ
        )
        repository.recordAttempt(base)
        repository.recordAttempt(base.copy(isCorrect = false, answeredAt = 2))
        assertEquals(2, repository.snapshot().size)
        assertFalse(repository.snapshot().first().isCorrect)
        assertTrue(repository.snapshot().last().isCorrect)
    }
}
