package com.examen.civique.domain.model

private const val EXAM_DURATION_SECONDS = 45 * 60

data class ExamState(
    val questions: List<Question> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val selectedAnswers: Map<String, Int> = emptyMap(),
    val remainingSeconds: Int = EXAM_DURATION_SECONDS,
    val examFinished: Boolean = false,
    val finishReason: ExamFinishReason? = null
) {
    val currentQuestion: Question?
        get() = questions.getOrNull(currentQuestionIndex)
}
