package com.examen.civique.domain.model

data class QuizResult(
    val id: Long = 0,
    val score: Int,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val wrongAnswers: Int,
    val percentage: Int,
    val completedAt: Long
)
