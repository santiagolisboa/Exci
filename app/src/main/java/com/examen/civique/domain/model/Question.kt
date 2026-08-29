package com.examen.civique.domain.model

data class Question(
    val id: Int,
    val question: String,
    val answers: List<String>,
    val correctAnswerIndex: Int,
    val explanation: String,
    val category: String
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
