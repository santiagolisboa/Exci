package com.examen.civique.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.examen.civique.data.local.entity.QuizResultEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizResultDao {

    @Insert
    suspend fun insert(
        result: QuizResultEntity
    ): Long

    @Query(
        """
        SELECT *
        FROM quiz_results
        ORDER BY completedAt DESC
        """
    )
    fun getAllResults(): Flow<List<QuizResultEntity>>

    @Query(
        """
        SELECT COUNT(*)
        FROM quiz_results
        """
    )
    fun getQuizCount(): Flow<Int>

    @Query(
        """
        SELECT AVG(percentage)
        FROM quiz_results
        """
    )
    fun getAverageScore(): Flow<Double?>

    @Query(
        """
        SELECT MAX(percentage)
        FROM quiz_results
        """
    )
    fun getBestScore(): Flow<Int?>
}
