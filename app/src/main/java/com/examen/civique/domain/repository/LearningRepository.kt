package com.examen.civique.domain.repository

import com.examen.civique.domain.model.AnswerAttempt
import com.examen.civique.domain.model.QuestionError
import com.examen.civique.domain.model.SessionResult
import com.examen.civique.domain.model.SessionType
import kotlinx.coroutines.flow.Flow

interface LearningRepository {
    fun observeActiveErrors(): Flow<List<QuestionError>>
    fun observeResults(): Flow<List<SessionResult>>
    suspend fun updateErrorProjection(attempt: AnswerAttempt)
    suspend fun saveCompletedSession(
        type: SessionType,
        answers: List<AnswerAttempt>,
        totalQuestions: Int = answers.size
    )
}
