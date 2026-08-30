package com.examen.civique.domain.model

data class Question(
    val id: String,
    val question: String,
    val answers: List<String>,
    val correctAnswerIndex: Int,
    val category: QuestionCategory,
    val type: QuestionType,
    val difficulty: DifficultyLevel,
    val explanation: String,
    val lessonId: String,
    val official: Boolean,
    val sourceId: String,
    val verified: Boolean
) {

    fun shuffledAnswers(): Question {

        val correctAnswer = answers[correctAnswerIndex]

        val shuffled = answers.shuffled()

        return copy(
            answers = shuffled,
            correctAnswerIndex = shuffled.indexOf(correctAnswer)
        )
    }
}
