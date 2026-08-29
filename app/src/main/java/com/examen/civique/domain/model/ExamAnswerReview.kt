package com.examen.civique.domain.model

data class ExamAnswerReview(
    val question: Question,
    val selectedAnswerIndex: Int?,
    val isCorrect: Boolean
)
