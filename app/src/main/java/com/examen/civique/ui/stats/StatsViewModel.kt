package com.examen.civique.ui.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examen.civique.domain.model.QuizResult
import com.examen.civique.domain.repository.QuizResultRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class StatsUiState(
    val quizCount: Int = 0,
    val averageScore: Int = 0,
    val bestScore: Int = 0,
    val results: List<QuizResult> = emptyList()
)

class StatsViewModel(
    repository: QuizResultRepository
) : ViewModel() {

    val uiState: StateFlow<StatsUiState> =
        combine(
            repository.getQuizCount(),
            repository.getAverageScore(),
            repository.getBestScore(),
            repository.getAllResults()
        ) { quizCount, averageScore, bestScore, results ->

            StatsUiState(
                quizCount = quizCount,
                averageScore =
                    averageScore?.toInt() ?: 0,
                bestScore =
                    bestScore ?: 0,
                results = results
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = StatsUiState()
        )
}