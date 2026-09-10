package com.examen.civique.domain.repository

import com.examen.civique.domain.model.QuestionReport

interface QuestionReportRepository {
    /** Stores exactly one report. Without a backend it remains queued locally. */
    suspend fun submitReport(report: QuestionReport): Boolean
}

