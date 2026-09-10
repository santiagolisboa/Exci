package com.examen.civique.domain.model

enum class SessionType { QUIZ, EXAM, ERROR_REVIEW, FAVORITES_REVIEW }

enum class AnswerSource { QUIZ, EXAM, ERROR_REVIEW, FAVORITES }

enum class MasteryStatus { NEVER_SEEN, TO_REVIEW, MASTERED }

data class AnswerAttempt(
    val id: Long = 0,
    val questionId: String,
    val category: QuestionCategory,
    val selectedAnswer: String?,
    val isCorrect: Boolean,
    val answeredAt: Long,
    val source: AnswerSource,
    val syncId: String? = null
)

data class QuestionError(
    val questionId: String,
    val errorCount: Int,
    val lastWrongAnswer: String?,
    val lastErrorAt: Long,
    val active: Boolean
)

data class SessionResult(
    val id: Long = 0,
    val type: SessionType,
    val score: Int,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val wrongAnswers: Int,
    val percentage: Int,
    val completedAt: Long,
    val syncId: String? = null
)

data class CategoryStats(
    val category: QuestionCategory,
    val totalQuestions: Int,
    val questionsSeen: Int,
    val questionsRemaining: Int,
    val attempts: Int,
    val correct: Int,
    val wrong: Int,
    val successRate: Int,
    val coverage: Int
) {
    val answered: Int get() = attempts
}

data class LearningProgress(
    val totalQuestions: Int = 0,
    val seenQuestions: Int = 0,
    val masteredQuestions: Int = 0,
    val reviewQuestions: Int = 0,
    val remainingQuestions: Int = 0,
    val coverageRate: Int = 0
)

data class LearningStats(
    val quizCount: Int = 0,
    val examCount: Int = 0,
    val averageQuiz: Int = 0,
    val averageExam: Int = 0,
    val bestQuiz: Int = 0,
    val bestExam: Int = 0,
    val totalQuestions: Int = 0,
    val questionsSeen: Int = 0,
    val questionsRemaining: Int = 0,
    val coverage: Int = 0,
    val answered: Int = 0,
    val correct: Int = 0,
    val wrong: Int = 0,
    val successRate: Int = 0,
    val mastered: Int = 0,
    val toReview: Int = 0,
    val neverSeen: Int = 0,
    val masteryByQuestion: Map<String, MasteryStatus> = emptyMap(),
    val byCategory: List<CategoryStats> = emptyList(),
    val weakCategories: List<CategoryStats> = emptyList(),
    val mostMissedQuestionIds: List<String> = emptyList()
) {
    val progress: LearningProgress
        get() = LearningProgress(
            totalQuestions = totalQuestions,
            seenQuestions = questionsSeen,
            masteredQuestions = mastered,
            reviewQuestions = toReview,
            remainingQuestions = questionsRemaining,
            coverageRate = coverage
        )
}

fun SessionType.toAnswerSource(): AnswerSource = when (this) {
    SessionType.QUIZ -> AnswerSource.QUIZ
    SessionType.EXAM -> AnswerSource.EXAM
    SessionType.ERROR_REVIEW -> AnswerSource.ERROR_REVIEW
    SessionType.FAVORITES_REVIEW -> AnswerSource.FAVORITES
}
