package com.examen.civique.ui.exam

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examen.civique.domain.engine.ExamEngine
import com.examen.civique.domain.model.Question
import com.examen.civique.domain.model.ExamFinishReason
import com.examen.civique.domain.model.ExamAnswerReview
import com.examen.civique.domain.model.ExamState
import com.examen.civique.domain.repository.QuestionRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class ExamViewModel(
    private val questionRepository: QuestionRepository,
    private val engine: ExamEngine = ExamEngine()
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        engine.createInitialState(questionRepository.getQuestions())
    )

    val uiState: StateFlow<ExamState> =
        _uiState.asStateFlow()

    private var timerJob: Job? = null

    val currentQuestion: Question
        get() = _uiState.value.currentQuestion!!

    val score: Int
        get() = engine.calculateScore(_uiState.value)

    val answeredQuestionsCount: Int
        get() = _uiState.value.selectedAnswers.size

    val wrongAnswersCount: Int
        get() = engine.getWrongAnswersCount(_uiState.value)

    val unansweredQuestionsCount: Int
        get() = engine.getUnansweredCount(_uiState.value)

    val wrongQuestions: List<Question>
        get() = engine.getWrongQuestions(_uiState.value)

    val answerReviews: List<ExamAnswerReview>
        get() = engine.getAnswerReviews(_uiState.value)

    val wrongAnswerReviews: List<ExamAnswerReview>
        get() =
            answerReviews.filter { review ->

                review.selectedAnswerIndex != null &&
                        !review.isCorrect
            }

    init {
        startTimer()
    }

    fun selectAnswer(index: Int) {
        _uiState.value = engine.selectAnswer(_uiState.value, index)
    }

    fun nextQuestion() {
        _uiState.value = engine.nextQuestion(_uiState.value)
    }

    fun previousQuestion() {
        _uiState.value = engine.previousQuestion(_uiState.value)
    }

    fun goToQuestion(index: Int) {
        _uiState.value = engine.goToQuestion(_uiState.value, index)
    }

    fun finishExam() {
        finishExamWithReason(ExamFinishReason.COMPLETED)
    }

    fun finishBecauseAppLeft() {
        finishExamWithReason(ExamFinishReason.LEFT_APP)
    }

    private fun finishExamWithReason(reason: ExamFinishReason) {
        timerJob?.cancel()
        _uiState.value = engine.finishExam(_uiState.value, reason)
    }

    private fun startTimer() {

        timerJob?.cancel()

        timerJob = viewModelScope.launch {

            while (
                isActive &&
                !_uiState.value.examFinished
            ) {

                delay(1000)

                _uiState.value = engine.tick(_uiState.value)
            }
        }
    }

    fun resetExam() {

        timerJob?.cancel()

        _uiState.value = engine.createInitialState(questionRepository.getQuestions())

        startTimer()
    }

    override fun onCleared() {

        timerJob?.cancel()

        super.onCleared()
    }
}
