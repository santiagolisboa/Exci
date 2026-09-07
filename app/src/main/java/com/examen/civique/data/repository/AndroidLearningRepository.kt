package com.examen.civique.data.repository

import com.examen.civique.data.local.dao.LearningDao
import com.examen.civique.data.local.dao.QuizResultDao
import com.examen.civique.data.local.entity.QuestionErrorEntity
import com.examen.civique.data.local.entity.QuizResultEntity
import com.examen.civique.domain.model.AnswerAttempt
import com.examen.civique.domain.model.QuestionError
import com.examen.civique.domain.model.SessionResult
import com.examen.civique.domain.model.SessionType
import com.examen.civique.domain.repository.LearningRepository
import com.examen.civique.domain.engine.ErrorTracker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AndroidLearningRepository(
    private val learningDao: LearningDao,
    private val resultDao: QuizResultDao
) : LearningRepository {
    override fun observeActiveErrors(): Flow<List<QuestionError>> =
        learningDao.observeActiveErrors().map { rows -> rows.map { it.toDomain() } }

    override fun observeResults(): Flow<List<SessionResult>> =
        resultDao.getAllResults().map { rows -> rows.map { it.toSessionResult() } }

    override suspend fun updateErrorProjection(attempt: AnswerAttempt) {
        val existing = learningDao.getError(attempt.questionId)
        val updated = ErrorTracker.update(existing?.toDomain(), attempt)
        if (updated != null && updated != existing?.toDomain()) {
            learningDao.upsertError(updated.toEntity())
        }
    }

    override suspend fun saveCompletedSession(
        type: SessionType,
        answers: List<AnswerAttempt>,
        totalQuestions: Int
    ) {
        val correct = answers.count { it.isCorrect }
        val total = totalQuestions
        resultDao.insert(
            QuizResultEntity(
                score = correct,
                totalQuestions = total,
                correctAnswers = correct,
                wrongAnswers = total - correct,
                percentage = if (total == 0) 0 else correct * 100 / total,
                completedAt = System.currentTimeMillis(),
                sessionType = type.name
            )
        )
    }

    private fun QuestionErrorEntity.toDomain() = QuestionError(
        questionId, errorCount, lastWrongAnswer, lastErrorAt, active
    )

    private fun QuestionError.toEntity() = QuestionErrorEntity(
        questionId, errorCount, lastWrongAnswer, lastErrorAt, active
    )

    private fun QuizResultEntity.toSessionResult() = SessionResult(
        id = id,
        type = runCatching { SessionType.valueOf(sessionType) }.getOrDefault(SessionType.QUIZ),
        score = score,
        totalQuestions = totalQuestions,
        correctAnswers = correctAnswers,
        wrongAnswers = wrongAnswers,
        percentage = percentage,
        completedAt = completedAt
    )
}
