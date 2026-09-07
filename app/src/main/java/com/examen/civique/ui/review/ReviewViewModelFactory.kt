package com.examen.civique.ui.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.examen.civique.domain.repository.FavoriteQuestionRepository
import com.examen.civique.domain.repository.LearningRepository
import com.examen.civique.domain.repository.QuestionRepository

class ReviewViewModelFactory(
    private val questions: QuestionRepository,
    private val learning: LearningRepository,
    private val favorites: FavoriteQuestionRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        ReviewViewModel(questions, learning, favorites) as T
}
