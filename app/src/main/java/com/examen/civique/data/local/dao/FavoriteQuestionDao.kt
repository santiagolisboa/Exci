package com.examen.civique.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.examen.civique.data.local.entity.FavoriteQuestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteQuestionDao {
    @Query("SELECT questionId FROM favorite_questions ORDER BY addedAt DESC")
    fun observeIds(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: FavoriteQuestionEntity)

    @Query("DELETE FROM favorite_questions WHERE questionId = :questionId")
    suspend fun delete(questionId: String)
}
