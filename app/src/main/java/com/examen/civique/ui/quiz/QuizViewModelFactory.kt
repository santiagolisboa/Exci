package com.examen.civique.ui.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.examen.civique.domain.repository.QuestionRepository
import com.examen.civique.domain.repository.QuizSessionRepository
import com.examen.civique.domain.repository.LearningRepository
import com.examen.civique.domain.repository.FavoriteQuestionRepository
import com.examen.civique.domain.repository.AnswerAttemptRepository

class QuizViewModelFactory(
    private val questionRepository: QuestionRepository,
    private val sessionRepository: QuizSessionRepository,
    private val learningRepository: LearningRepository,
    private val attemptRepository: AnswerAttemptRepository,
    private val favoriteRepository: FavoriteQuestionRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (modelClass.isAssignableFrom(QuizViewModel::class.java)) {

            return QuizViewModel(
                questionRepository = questionRepository,
                sessionRepository = sessionRepository,
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
