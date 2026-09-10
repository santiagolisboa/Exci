package com.examen.civique.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.examen.civique.data.local.entity.QuestionReportEntity

@Dao
interface QuestionReportDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(report: QuestionReportEntity): Long

    @Query("SELECT * FROM question_reports WHERE status = 'PENDING' ORDER BY createdAt")
    suspend fun pendingReports(): List<QuestionReportEntity>
}

