package com.examen.civique.ui.courses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examen.civique.domain.model.Course
import com.examen.civique.domain.model.CourseProgress
import com.examen.civique.domain.repository.CourseProgressRepository
import com.examen.civique.domain.repository.CourseRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CourseViewModel(
    private val courseRepository: CourseRepository,
    private val progressRepository: CourseProgressRepository
) : ViewModel() {

    val courses: List<Course>
        get() = courseRepository.getCourses()

    val progress: StateFlow<List<CourseProgress>> =
        progressRepository
            .getAllProgress()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    fun markCourseCompleted(courseId: Int) {

        viewModelScope.launch {

            progressRepository.markCourseCompleted(
                courseId = courseId
            )
        }
    }

    fun isCourseCompleted(courseId: Int): Boolean {

        return progress.value.any { item ->
            item.courseId == courseId &&
                    item.completed
        }
    }
}