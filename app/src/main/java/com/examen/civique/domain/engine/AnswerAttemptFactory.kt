package com.examen.civique.domain.engine

import com.examen.civique.domain.model.AnswerAttempt
import com.examen.civique.domain.model.QuizState
import com.examen.civique.domain.model.toAnswerSource

object AnswerAttemptFactory {
    fun fromQuizValidation(
        before: QuizState,
        after: QuizState,
        answeredAt: Long
    ): AnswerAttempt? {
        if (before.answerValidated || !after.answerValidated) return null
        val answer = after.answers.lastOrNull() ?: return null
        return AnswerAttempt(
            questionId = answer.question.id,
            category = answer.question.category,
            selectedAnswer = answer.question.answers.getOrNull(answer.selectedAnswerIndex),
            isCorrect = answer.isCorrect,
            answeredAt = answeredAt,
            source = after.sessionType.toAnswerSource()
        )
    }

    fun shouldRecordExamSelection(previousIndex: Int?, selectedIndex: Int): Boolean =
        previousIndex != selectedIndex
}
