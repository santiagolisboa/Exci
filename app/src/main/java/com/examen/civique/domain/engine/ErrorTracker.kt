package com.examen.civique.domain.engine

import com.examen.civique.domain.model.AnswerAttempt
import com.examen.civique.domain.model.QuestionError

object ErrorTracker {
    fun update(
        existing: QuestionError?,
        answer: AnswerAttempt
    ): QuestionError? = when {
        !answer.isCorrect -> QuestionError(
            questionId = answer.questionId,
            errorCount = (existing?.errorCount ?: 0) + 1,
            lastWrongAnswer = answer.selectedAnswer,
            lastErrorAt = answer.answeredAt,
            active = true
        )
        answer.isCorrect && existing != null -> existing.copy(active = false)
        else -> existing
    }
}
