package com.examen.civique.domain.engine

import com.examen.civique.domain.model.AnswerAttempt
import com.examen.civique.domain.model.CategoryStats
import com.examen.civique.domain.model.LearningStats
import com.examen.civique.domain.model.MasteryStatus
import com.examen.civique.domain.model.Question
import com.examen.civique.domain.model.QuestionCategory
import com.examen.civique.domain.model.SessionResult
import com.examen.civique.domain.model.SessionType

object StatisticsCalculator {
    fun calculate(
        questions: List<Question>,
        results: List<SessionResult>,
        attempts: List<AnswerAttempt>
    ): LearningStats {
        val quizzes = results.filter { it.type != SessionType.EXAM }
        val exams = results.filter { it.type == SessionType.EXAM }
        val questionIds = questions.mapTo(mutableSetOf()) { it.id }
        val seenIds = attempts.mapTo(mutableSetOf()) { it.questionId }.intersect(questionIds)
        val correct = attempts.count { it.isCorrect }
        val latestByQuestion = attempts
            .sortedWith(compareBy<AnswerAttempt> { it.answeredAt }.thenBy { it.id })
            .associateBy { it.questionId }
        val mastery = questions.associate { question ->
            question.id to when (latestByQuestion[question.id]?.isCorrect) {
                null -> MasteryStatus.NEVER_SEEN
                false -> MasteryStatus.TO_REVIEW
                true -> MasteryStatus.MASTERED
            }
        }

        val categoryStats = QuestionCategory.entries.map { category ->
            val categoryQuestions = questions.filter { it.category == category }
            val categoryIds = categoryQuestions.mapTo(mutableSetOf()) { it.id }
            val categoryAttempts = attempts.filter { it.category == category }
            val categorySeen = categoryAttempts.mapTo(mutableSetOf()) { it.questionId }
                .intersect(categoryIds).size
            val categoryCorrect = categoryAttempts.count { it.isCorrect }
            CategoryStats(
                category = category,
                totalQuestions = categoryQuestions.size,
                questionsSeen = categorySeen,
                questionsRemaining = (categoryQuestions.size - categorySeen).coerceAtLeast(0),
                attempts = categoryAttempts.size,
                correct = categoryCorrect,
                wrong = categoryAttempts.size - categoryCorrect,
                successRate = percentage(categoryCorrect, categoryAttempts.size),
                coverage = percentage(categorySeen, categoryQuestions.size)
            )
        }

        val activeReviewIds = mastery.filterValues { it == MasteryStatus.TO_REVIEW }.keys
        val mostMissed = attempts.asSequence()
            .filter { !it.isCorrect && it.questionId in activeReviewIds }
            .groupingBy { it.questionId }.eachCount()
            .entries.sortedByDescending { it.value }.take(5).map { it.key }

        return LearningStats(
            quizCount = quizzes.size,
            examCount = exams.size,
            averageQuiz = quizzes.map { it.percentage }.averageOrZero(),
            averageExam = exams.map { it.percentage }.averageOrZero(),
            bestQuiz = quizzes.maxOfOrNull { it.percentage } ?: 0,
            bestExam = exams.maxOfOrNull { it.percentage } ?: 0,
            totalQuestions = questions.size,
            questionsSeen = seenIds.size,
            questionsRemaining = (questions.size - seenIds.size).coerceAtLeast(0),
            coverage = percentage(seenIds.size, questions.size),
            answered = attempts.size,
            correct = correct,
            wrong = attempts.size - correct,
            successRate = percentage(correct, attempts.size),
            mastered = mastery.values.count { it == MasteryStatus.MASTERED },
            toReview = mastery.values.count { it == MasteryStatus.TO_REVIEW },
            neverSeen = mastery.values.count { it == MasteryStatus.NEVER_SEEN },
            masteryByQuestion = mastery,
            byCategory = categoryStats,
            weakCategories = categoryStats.filter { it.attempts > 0 }
                .sortedWith(compareBy<CategoryStats> { it.successRate }.thenBy { it.coverage })
                .take(3),
            mostMissedQuestionIds = mostMissed
        )
    }

    private fun percentage(value: Int, total: Int) =
        if (total == 0) 0 else value * 100 / total

    private fun List<Int>.averageOrZero() = if (isEmpty()) 0 else average().toInt()
}
