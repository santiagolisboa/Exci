package com.examen.civique.domain.model

data class QuizAnswer(
    val question: Question,
    val selectedAnswerIndex: Int,
    val isCorrect: Boolean
)
