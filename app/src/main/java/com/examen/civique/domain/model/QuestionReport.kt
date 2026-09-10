package com.examen.civique.domain.model

enum class QuestionReportReason {
    INCORRECT_QUESTION,
    INCORRECT_CORRECT_ANSWER,
    AMBIGUOUS,
    OUTDATED,
    TYPO,
    OTHER
}

enum class QuestionReportSource { QUIZ, EXAM, ERROR_REVIEW, FAVORITES, EXAM_REVIEW }

enum class QuestionReportStatus { PENDING, SENT, ERROR }

data class QuestionReport(
    val reportId: String,
    val questionId: String,
    val reason: QuestionReportReason,
    val comment: String? = null,
    val createdAt: Long,
    val source: QuestionReportSource,
    val selectedAnswer: String? = null,
    val displayedCorrectAnswer: String? = null,
    val appVersion: String,
    val questionDataVersion: String? = null,
    val userId: String? = null,
    val status: QuestionReportStatus = QuestionReportStatus.PENDING
)

object QuestionReportFactory {
    fun create(
        reportId: String,
        questionId: String,
        reason: QuestionReportReason,
        comment: String?,
        createdAt: Long,
        source: QuestionReportSource,
        appVersion: String,
        selectedAnswer: String? = null,
        displayedCorrectAnswer: String? = null,
        questionDataVersion: String? = null,
        userId: String? = null
    ) = QuestionReport(
        reportId = reportId,
        questionId = questionId,
        reason = reason,
        comment = comment?.trim()?.takeIf(String::isNotEmpty),
        createdAt = createdAt,
        source = source,
        selectedAnswer = selectedAnswer,
        displayedCorrectAnswer = displayedCorrectAnswer,
        appVersion = appVersion,
        questionDataVersion = questionDataVersion,
        userId = userId
    )
}

