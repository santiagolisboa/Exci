package com.examen.civique.ui.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examen.civique.domain.engine.StatisticsCalculator
import com.examen.civique.domain.model.AnswerAttempt
import com.examen.civique.domain.model.LearningStats
import com.examen.civique.domain.model.Question
import com.examen.civique.domain.model.SessionResult
import com.examen.civique.domain.repository.AnswerAttemptRepository
import com.examen.civique.domain.repository.LearningRepository
import com.examen.civique.domain.repository.QuestionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class StatsUiState(
    val stats: LearningStats = LearningStats(),
    val results: List<SessionResult> = emptyList(),
    val recentAttempts: List<AnswerAttempt> = emptyList(),
    val questionsById: Map<String, Question> = emptyMap()
) {
    val quizCount get() = stats.quizCount
    val averageScore get() = stats.averageQuiz
}

class StatsViewModel(
    repository: LearningRepository,
    attemptRepository: AnswerAttemptRepository,
    questionRepository: QuestionRepository
) : ViewModel() {
    private val questions = questionRepository.getQuestions()

    val uiState: StateFlow<StatsUiState> = combine(
        repository.observeResults(), attemptRepository.observeAll()
    ) { results, attempts ->
        StatsUiState(
            stats = StatisticsCalculator.calculate(questions, results, attempts),
            results = results,
            recentAttempts = attempts.take(10),
            questionsById = questions.associateBy { it.id }
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        StatsUiState(
            stats = StatisticsCalculator.calculate(questions, emptyList(), emptyList()),
            questionsById = questions.associateBy { it.id }
        )
    )
}
