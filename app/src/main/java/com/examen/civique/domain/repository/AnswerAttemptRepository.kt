package com.examen.civique.domain.repository

import com.examen.civique.domain.model.AnswerAttempt
import com.examen.civique.domain.model.QuestionCategory
import kotlinx.coroutines.flow.Flow

interface AnswerAttemptRepository {
    suspend fun recordAttempt(attempt: AnswerAttempt): Long
    fun observeAll(): Flow<List<AnswerAttempt>>
    fun observeForQuestion(questionId: String): Flow<List<AnswerAttempt>>
    fun observeForCategory(category: QuestionCategory): Flow<List<AnswerAttempt>>
}
