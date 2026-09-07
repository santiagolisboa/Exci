package com.examen.civique.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question_errors")
data class QuestionErrorEntity(
    @PrimaryKey val questionId: String,
    val errorCount: Int,
    val lastWrongAnswer: String?,
    val lastErrorAt: Long,
    val active: Boolean
)
