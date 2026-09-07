package com.examen.civique.domain.engine

import com.examen.civique.domain.model.AnswerAttempt
import com.examen.civique.domain.model.LearningProgress
import com.examen.civique.domain.model.MasteryStatus
import com.examen.civique.domain.model.Question
import kotlin.math.roundToInt

data class LearningProgressResult(
    val progress: LearningProgress,
    val masteryByQuestion: Map<String, MasteryStatus>
)

object LearningProgressCalculator {
    fun calculate(
        questions: List<Question>,
        attempts: List<AnswerAttempt>
    ): LearningProgressResult {
        val latest = attempts
            .sortedWith(compareBy<AnswerAttempt> { it.answeredAt }.thenBy { it.id })
            .associateBy { it.questionId }
        val mastery = questions.associate { question ->
            question.id to when (latest[question.id]?.isCorrect) {
                null -> MasteryStatus.NEVER_SEEN
                false -> MasteryStatus.TO_REVIEW
                true -> MasteryStatus.MASTERED
            }
        }
        val seen = mastery.values.count { it != MasteryStatus.NEVER_SEEN }
        val total = questions.size
        return LearningProgressResult(
            progress = LearningProgress(
                totalQuestions = total,
                seenQuestions = seen,
                masteredQuestions = mastery.values.count { it == MasteryStatus.MASTERED },
                reviewQuestions = mastery.values.count { it == MasteryStatus.TO_REVIEW },
                remainingQuestions = (total - seen).coerceAtLeast(0),
                coverageRate = if (total == 0) 0 else (seen * 100f / total).roundToInt()
            ),
            masteryByQuestion = mastery
        )
    }
}
