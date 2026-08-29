package com.examen.civique.data.repository

import com.examen.civique.data.local.dao.QuizResultDao
import com.examen.civique.data.local.entity.QuizResultEntity
import com.examen.civique.domain.model.QuizResult
import com.examen.civique.domain.repository.QuizResultRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AndroidQuizResultRepository(
    private val quizResultDao: QuizResultDao
) : QuizResultRepository {

    override suspend fun saveQuizResult(
        score: Int,
        totalQuestions: Int
    ) {

        val wrongAnswers =
            totalQuestions - score

        val percentage =
            if (totalQuestions > 0) {
                (score * 100) / totalQuestions
            } else {
                0
            }

        val result = QuizResultEntity(
            score = score,
            totalQuestions = totalQuestions,
            correctAnswers = score,
            wrongAnswers = wrongAnswers,
            percentage = percentage,
            completedAt = System.currentTimeMillis()
        )

        quizResultDao.insert(result)
    }

    override fun getAllResults(): Flow<List<QuizResult>> =
        quizResultDao.getAllResults().map { entities ->
            entities.map { it.toDomain() }
        }

    override fun getQuizCount(): Flow<Int> =
        quizResultDao.getQuizCount()

    override fun getAverageScore(): Flow<Double?> =
        quizResultDao.getAverageScore()

    override fun getBestScore(): Flow<Int?> =
        quizResultDao.getBestScore()
}

fun QuizResultEntity.toDomain(): QuizResult {
    return QuizResult(
        id = id,
        score = score,
        totalQuestions = totalQuestions,
        correctAnswers = correctAnswers,
        wrongAnswers = wrongAnswers,
        percentage = percentage,
        completedAt = completedAt
    )
}
