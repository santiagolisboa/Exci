package com.examen.civique.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.examen.civique.data.local.entity.AnswerAttemptEntity
import com.examen.civique.data.local.entity.QuestionErrorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LearningDao {
    @Query("SELECT * FROM question_errors WHERE active = 1 ORDER BY lastErrorAt DESC")
    fun observeActiveErrors(): Flow<List<QuestionErrorEntity>>

    @Query("SELECT * FROM question_errors WHERE questionId = :questionId")
    suspend fun getError(questionId: String): QuestionErrorEntity?

    @Upsert
    suspend fun upsertError(entity: QuestionErrorEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAnswers(entities: List<AnswerAttemptEntity>)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAnswer(entity: AnswerAttemptEntity): Long

    @Query("SELECT * FROM answer_records ORDER BY answeredAt DESC, id DESC")
    fun observeAnswers(): Flow<List<AnswerAttemptEntity>>

    @Query("SELECT * FROM answer_records WHERE questionId = :questionId ORDER BY answeredAt DESC, id DESC")
    fun observeAnswersForQuestion(questionId: String): Flow<List<AnswerAttemptEntity>>

    @Query("SELECT * FROM answer_records WHERE category = :category ORDER BY answeredAt DESC, id DESC")
    fun observeAnswersForCategory(category: String): Flow<List<AnswerAttemptEntity>>
}
