package com.examen.civique.ui.exam

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.examen.civique.domain.repository.QuestionRepository
import com.examen.civique.domain.repository.LearningRepository
import com.examen.civique.domain.repository.FavoriteQuestionRepository
import com.examen.civique.domain.repository.AnswerAttemptRepository

class ExamViewModelFactory(
    private val questionRepository: QuestionRepository,
    private val learningRepository: LearningRepository,
    private val attemptRepository: AnswerAttemptRepository,
    private val favoriteRepository: FavoriteQuestionRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (modelClass.isAssignableFrom(ExamViewModel::class.java)) {

            return ExamViewModel(
                questionRepository = questionRepository,
                learningRepository = learningRepository,
                attemptRepository = attemptRepository,
                favoriteRepository = favoriteRepository
            ) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class"
        )
    }
}
