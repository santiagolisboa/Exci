package com.examen.civique.domain.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class QuestionReportTest {
    @Test
    fun report_keepsQuestionReasonSourceAndAllowsAnonymousUser() {
        val report = QuestionReportFactory.create(
            reportId = "report-uuid",
            questionId = "question-42",
            reason = QuestionReportReason.AMBIGUOUS,
            comment = "  formulation imprécise  ",
            createdAt = 123L,
            source = QuestionReportSource.QUIZ,
            appVersion = "1.0"
        )

        assertEquals("report-uuid", report.reportId)
        assertEquals("question-42", report.questionId)
        assertEquals(QuestionReportReason.AMBIGUOUS, report.reason)
        assertEquals("formulation imprécise", report.comment)
        assertNull(report.userId)
        assertEquals(QuestionReportStatus.PENDING, report.status)
    }

    @Test
    fun blankOptionalCommentIsNotPersisted() {
        val report = QuestionReportFactory.create(
            "id", "q", QuestionReportReason.TYPO, "   ", 1L,
            QuestionReportSource.FAVORITES, "1.0"
        )
        assertNull(report.comment)
    }
}

