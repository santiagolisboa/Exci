package com.examen.civique.ui.courses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examen.civique.domain.model.Course
import com.examen.civique.domain.model.CourseProgress
import com.examen.civique.domain.repository.CourseProgressRepository
import com.examen.civique.domain.repository.CourseRepository
import com.examen.civique.domain.repository.QuestionRepository
import com.examen.civique.domain.engine.CourseProgressEngine
import com.examen.civique.domain.engine.LessonPracticeEngine
import com.examen.civique.domain.model.QuizState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CourseViewModel(
    private val courseRepository: CourseRepository,
    private val progressRepository: CourseProgressRepository,
    private val questionRepository: QuestionRepository,
    private val progressEngine: CourseProgressEngine = CourseProgressEngine(),
    private val practiceEngine: LessonPracticeEngine = LessonPracticeEngine()
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

    private val _practiceState = MutableStateFlow<QuizState?>(null)
    val practiceState: StateFlow<QuizState?> = _practiceState

    private val _practiceLessonId = MutableStateFlow<String?>(null)
    val practiceLessonId: StateFlow<String?> = _practiceLessonId

    fun openLesson(courseId: Int, lessonId: String) {
        viewModelScope.launch {
            progressRepository.saveProgress(
                progressEngine.openLesson(
                    progress = progress.value.find { it.courseId == courseId },
                    courseId = courseId,
                    lessonId = lessonId
                )
            )
        }
    }

    fun completeLesson(courseId: Int, lessonId: String) {
        val course = courses.find { it.id == courseId } ?: return
        viewModelScope.launch {
            progressRepository.saveProgress(
                progressEngine.completeLesson(
                    progress = progress.value.find { it.courseId == courseId },
                    course = course,
                    lessonId = lessonId,
                    completedAt = System.currentTimeMillis()
                )
            )
        }
    }

    fun questionsForLesson(lessonId: String) =
        questionRepository.getQuestionsForLesson(lessonId)

    fun startLessonPractice(lessonId: String) {
        _practiceLessonId.value = lessonId
        _practiceState.value = practiceEngine.createSession(
            lessonId,
            questionRepository.getQuestions()
        )
    }

    fun selectPracticeAnswer(index: Int) {
        _practiceState.value = _practiceState.value?.let {
            practiceEngine.selectAnswer(it, index)
        }
    }

    fun validatePracticeAnswer() {
        _practiceState.value = _practiceState.value?.let(practiceEngine::validateAnswer)
    }

    fun nextPracticeQuestion() {
        _practiceState.value = _practiceState.value?.let(practiceEngine::nextQuestion)
    }

    fun closePractice() {
        _practiceState.value = null
        _practiceLessonId.value = null
    }
}
