package com.examen.civique.ui.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.examen.civique.domain.repository.LearningRepository
import com.examen.civique.domain.repository.AnswerAttemptRepository
import com.examen.civique.domain.repository.QuestionRepository

class StatsViewModelFactory(
    private val repository: LearningRepository,
    private val attemptRepository: AnswerAttemptRepository,
    private val questionRepository: QuestionRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (
            modelClass.isAssignableFrom(
                StatsViewModel::class.java
            )
        ) {

            return StatsViewModel(
                repository = repository,
                attemptRepository = attemptRepository,
                questionRepository = questionRepository
            ) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class"
        )
    }
}
