package com.examen.civique.domain.model

data class SavedQuizQuestion(
    val id: String,
    val answers: List<String>,
    val correctAnswerIndex: Int
)

data class SavedQuizAnswer(
    val questionId: String,
    val selectedAnswerIndex: Int,
    val isCorrect: Boolean
)

data class QuizSession(
    val sessionType: SessionType = SessionType.QUIZ,
    val questions: List<SavedQuizQuestion>,
    val currentQuestionIndex: Int,
    val selectedAnswerIndex: Int?,
    val answerValidated: Boolean,
    val score: Int,
    val answers: List<SavedQuizAnswer>
)
