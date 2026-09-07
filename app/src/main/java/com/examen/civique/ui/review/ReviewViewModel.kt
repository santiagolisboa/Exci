package com.examen.civique.ui.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examen.civique.domain.model.Question
import com.examen.civique.domain.model.QuestionError
import com.examen.civique.domain.repository.FavoriteQuestionRepository
import com.examen.civique.domain.repository.LearningRepository
import com.examen.civique.domain.repository.QuestionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ErrorQuestionUi(val question: Question, val error: QuestionError)

class ReviewViewModel(
    questionRepository: QuestionRepository,
    learningRepository: LearningRepository,
    private val favoriteRepository: FavoriteQuestionRepository
) : ViewModel() {
    private val questions = questionRepository.getQuestions().associateBy { it.id }

    val errors: StateFlow<List<ErrorQuestionUi>> = learningRepository.observeActiveErrors()
        .map { errors -> errors.mapNotNull { error -> questions[error.questionId]?.let { ErrorQuestionUi(it, error) } } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val favoriteIds: StateFlow<Set<String>> = favoriteRepository.observeFavoriteIds()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptySet())

    val favorites: StateFlow<List<Question>> = favoriteIds.map { ids -> ids.mapNotNull(questions::get) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun toggleFavorite(questionId: String) {
        viewModelScope.launch {
            favoriteRepository.setFavorite(questionId, questionId !in favoriteIds.value)
        }
    }
}
