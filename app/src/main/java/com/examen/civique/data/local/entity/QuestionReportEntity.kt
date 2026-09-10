package com.examen.civique.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question_reports")
data class QuestionReportEntity(
    @PrimaryKey val reportId: String,
    val questionId: String,
    val reason: String,
    val comment: String?,
    val createdAt: Long,
    val source: String,
    val selectedAnswer: String?,
    val displayedCorrectAnswer: String?,
    val appVersion: String,
    val questionDataVersion: String?,
    val userId: String?,
    val status: String
)

