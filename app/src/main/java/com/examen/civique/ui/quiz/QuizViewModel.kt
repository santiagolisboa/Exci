package com.examen.civique.ui.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examen.civique.domain.engine.QuizEngine
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

    private var resultSaved = false

    val wrongAnswers: List<QuizAnswer>
        get() = _uiState.value.wrongAnswers

    fun selectAnswer(index: Int) {
        _uiState.value = engine.selectAnswer(_uiState.value, index)
    }

    fun validateAnswer() {
        _uiState.value = engine.validateAnswer(_uiState.value)
    }

    fun nextQuestion() {
        val currentState = _uiState.value

        if (currentState.quizFinished) return

        val nextState = engine.nextQuestion(currentState)
        _uiState.value = nextState

        if (
            !currentState.quizFinished &&
            nextState.quizFinished &&
            !resultSaved
        ) {
            resultSaved = true
            saveResult(nextState)
        }
    }

    fun resetQuiz() {
        resultSaved = false
        _uiState.value = engine.createInitialState(questionRepository.getQuestions())
    }

    private fun saveResult(state: QuizState) {
        viewModelScope.launch {
            resultRepository.saveQuizResult(
                score = state.score,
                totalQuestions = state.questions.size
            )
        }
    }
}
