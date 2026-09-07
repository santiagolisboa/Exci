package com.examen.civique.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "answer_records")
data class AnswerAttemptEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val questionId: String,
    val category: String,
    val selectedAnswer: String?,
    val correct: Boolean,
    val answeredAt: Long,
    val source: String = "QUIZ"
)
