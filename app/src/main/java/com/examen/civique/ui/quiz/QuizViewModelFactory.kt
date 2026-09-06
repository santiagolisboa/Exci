package com.examen.civique.ui.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.examen.civique.domain.repository.QuestionRepository
import com.examen.civique.domain.repository.QuizResultRepository
import com.examen.civique.domain.repository.QuizSessionRepository

class QuizViewModelFactory(
    private val questionRepository: QuestionRepository,
    private val resultRepository: QuizResultRepository,
    private val sessionRepository: QuizSessionRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (modelClass.isAssignableFrom(QuizViewModel::class.java)) {

            return QuizViewModel(
                questionRepository = questionRepository,
                resultRepository = resultRepository,
                sessionRepository = sessionRepository
            ) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class"
        )
    }
}
