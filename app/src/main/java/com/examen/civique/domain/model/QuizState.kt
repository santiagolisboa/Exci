package com.examen.civique.domain.model

data class QuizState(
    val currentQuestionIndex: Int = 0,
    val selectedAnswerIndex: Int? = null,
    val answerValidated: Boolean = false,
    val score: Int = 0,
    val quizFinished: Boolean = false,
    val questions: List<Question> = emptyList(),
    val answers: List<QuizAnswer> = emptyList()
) {
    val currentQuestion: Question?
        get() = questions.getOrNull(currentQuestionIndex)

    val wrongAnswers: List<QuizAnswer>
        get() = answers.filter { !it.isCorrect }
}
