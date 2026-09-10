package com.examen.civique.data.repository

import com.examen.civique.data.local.dao.QuestionReportDao
import com.examen.civique.data.local.entity.QuestionReportEntity
import com.examen.civique.domain.model.QuestionReport
import com.examen.civique.domain.model.QuestionReportReason
import com.examen.civique.domain.model.QuestionReportSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class QuestionReportRepositoryTest {
    @Test fun sameReportIdCannotBeQueuedTwice() = runBlocking {
        val repository = AndroidQuestionReportRepository(MemoryDao())
        val report = QuestionReport(
            reportId = "stable-id",
            questionId = "q-1",
            reason = QuestionReportReason.OUTDATED,
            createdAt = 10L,
            source = QuestionReportSource.EXAM,
            appVersion = "1.0"
        )

        assertTrue(repository.submitReport(report))
        assertFalse(repository.submitReport(report))
    }

    private class MemoryDao : QuestionReportDao {
        private val rows = linkedMapOf<String, QuestionReportEntity>()
        override suspend fun insert(report: QuestionReportEntity): Long {
            if (rows.containsKey(report.reportId)) return -1
            rows[report.reportId] = report
            return rows.size.toLong()
        }
        override suspend fun pendingReports() = rows.values.toList()
    }
}
