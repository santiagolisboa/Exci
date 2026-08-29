package com.examen.civique.domain.repository

import com.examen.civique.domain.model.QuizResult
import kotlinx.coroutines.flow.Flow

interface QuizResultRepository {
    suspend fun saveQuizResult(score: Int, totalQuestions: Int)
    fun getAllResults(): Flow<List<QuizResult>>
    fun getQuizCount(): Flow<Int>
    fun getAverageScore(): Flow<Double?>
    fun getBestScore(): Flow<Int?>
}
