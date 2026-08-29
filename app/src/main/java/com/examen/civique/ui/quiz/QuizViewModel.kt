package com.examen.civique.ui.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examen.civique.domain.engine.QuizEngine
import com.examen.civique.domain.model.Question
import com.examen.civique.domain.model.QuizAnswer
import com.examen.civique.domain.model.QuizState
import com.examen.civique.domain.repository.QuestionRepository
import com.examen.civique.domain.repository.QuizResultRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuizViewModel(
    private val questionRepository: QuestionRepository,
    private val resultRepository: QuizResultRepository,
    private val engine: QuizEngine = QuizEngine()
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        engine.createInitialState(questionRepository.getQuestions())
    )

    val uiState: StateFlow<QuizState> =
        _uiState.asStateFlow()

    val currentQuestion: Question
        get() = _uiState.value.currentQuestion!!

    val wrongAnswers: List<QuizAnswer>
        get() = _uiState.value.wrongAnswers

    fun selectAnswer(index: Int) {
        _uiState.value = engine.selectAnswer(_uiState.value, index)
    }

    fun validateAnswer() {
        _uiState.value = engine.validateAnswer(_uiState.value)
    }

    fun nextQuestion() {
        val nextState = engine.nextQuestion(_uiState.value)
        _uiState.value = nextState

        if (nextState.quizFinished) {
            saveResult()
        }
    }

    fun resetQuiz() {
        _uiState.value = engine.createInitialState(questionRepository.getQuestions())
    }

    private fun saveResult() {
        val state = _uiState.value

        viewModelScope.launch {
            resultRepository.saveQuizResult(
                score = state.score,
                totalQuestions = state.questions.size
            )
        }
    }
}
