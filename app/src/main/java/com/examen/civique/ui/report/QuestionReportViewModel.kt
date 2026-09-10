package com.examen.civique.ui.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.examen.civique.domain.model.QuestionReportFactory
import com.examen.civique.domain.model.QuestionReportReason
import com.examen.civique.domain.model.QuestionReportSource
import com.examen.civique.domain.repository.AuthRepository
import com.examen.civique.domain.repository.QuestionReportRepository
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.UUID

class QuestionReportViewModel(
    private val repository: QuestionReportRepository,
    private val authRepository: AuthRepository,
    private val appVersion: String
) : ViewModel() {
    private val submitMutex = Mutex()

    suspend fun submit(
        questionId: String,
        reason: QuestionReportReason,
        comment: String?,
        source: QuestionReportSource,
        selectedAnswer: String?,
        correctAnswer: String?
    ): Boolean = submitMutex.withLock {
        repository.submitReport(
            QuestionReportFactory.create(
                reportId = UUID.randomUUID().toString(),
                questionId = questionId,
                reason = reason,
                comment = comment,
                createdAt = System.currentTimeMillis(),
                source = source,
                appVersion = appVersion,
                selectedAnswer = selectedAnswer,
                displayedCorrectAnswer = correctAnswer,
                userId = authRepository.currentUser?.id
            )
        )
    }
}

class QuestionReportViewModelFactory(
    private val repository: QuestionReportRepository,
    private val authRepository: AuthRepository,
    private val appVersion: String
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        QuestionReportViewModel(repository, authRepository, appVersion) as T
}

