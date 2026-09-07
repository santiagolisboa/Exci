package com.examen.civique.data.repository

import com.examen.civique.data.local.dao.LearningDao
import com.examen.civique.data.local.entity.AnswerAttemptEntity
import com.examen.civique.domain.model.AnswerAttempt
import com.examen.civique.domain.model.AnswerSource
import com.examen.civique.domain.model.QuestionCategory
import com.examen.civique.domain.repository.AnswerAttemptRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AndroidAnswerAttemptRepository(
    private val dao: LearningDao
) : AnswerAttemptRepository {
    override suspend fun recordAttempt(attempt: AnswerAttempt): Long =
        dao.insertAnswer(attempt.toEntity())

    override fun observeAll(): Flow<List<AnswerAttempt>> =
        dao.observeAnswers().map { rows -> rows.map { it.toDomain() } }

    override fun observeForQuestion(questionId: String): Flow<List<AnswerAttempt>> =
        dao.observeAnswersForQuestion(questionId).map { rows -> rows.map { it.toDomain() } }

    override fun observeForCategory(category: QuestionCategory): Flow<List<AnswerAttempt>> =
        dao.observeAnswersForCategory(category.name).map { rows -> rows.map { it.toDomain() } }

    private fun AnswerAttempt.toEntity() = AnswerAttemptEntity(
        id = id,
        questionId = questionId,
        category = category.name,
        selectedAnswer = selectedAnswer,
        correct = isCorrect,
        answeredAt = answeredAt,
        source = source.name
    )

    private fun AnswerAttemptEntity.toDomain() = AnswerAttempt(
        id = id,
        questionId = questionId,
        category = QuestionCategory.valueOf(category),
        selectedAnswer = selectedAnswer,
        isCorrect = correct,
        answeredAt = answeredAt,
        source = runCatching { AnswerSource.valueOf(source) }.getOrDefault(AnswerSource.QUIZ)
    )
}
