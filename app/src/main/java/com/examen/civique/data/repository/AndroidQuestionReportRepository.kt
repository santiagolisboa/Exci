package com.examen.civique.data.repository

import com.examen.civique.data.local.dao.QuestionReportDao
import com.examen.civique.data.local.entity.QuestionReportEntity
import com.examen.civique.domain.model.QuestionReport
import com.examen.civique.domain.repository.QuestionReportRepository

class AndroidQuestionReportRepository(
    private val dao: QuestionReportDao
) : QuestionReportRepository {
    override suspend fun submitReport(report: QuestionReport): Boolean = dao.insert(
        QuestionReportEntity(
            reportId = report.reportId,
            questionId = report.questionId,
            reason = report.reason.name,
            comment = report.comment,
            createdAt = report.createdAt,
            source = report.source.name,
            selectedAnswer = report.selectedAnswer,
            displayedCorrectAnswer = report.displayedCorrectAnswer,
            appVersion = report.appVersion,
            questionDataVersion = report.questionDataVersion,
            userId = report.userId,
            status = report.status.name
        )
    ) != -1L
}

