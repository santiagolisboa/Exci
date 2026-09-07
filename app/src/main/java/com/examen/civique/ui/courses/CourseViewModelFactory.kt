package com.examen.civique.ui.courses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.examen.civique.domain.repository.CourseProgressRepository
import com.examen.civique.domain.repository.CourseRepository
import com.examen.civique.domain.repository.QuestionRepository

class CourseViewModelFactory(
    private val courseRepository: CourseRepository,
    private val progressRepository: CourseProgressRepository,
    private val questionRepository: QuestionRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (
            modelClass.isAssignableFrom(
                CourseViewModel::class.java
            )
        ) {

            return CourseViewModel(
                courseRepository = courseRepository,
                progressRepository = progressRepository,
                questionRepository = questionRepository
            ) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class"
        )
    }
}
