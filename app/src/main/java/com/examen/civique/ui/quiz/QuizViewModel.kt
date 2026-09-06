package com.examen.civique.ui.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examen.civique.domain.engine.QuizEngine
import com.examen.civique.domain.model.QuizAnswer
import com.examen.civique.domain.model.QuizState
import com.examen.civique.domain.repository.QuestionRepository
import com.examen.civique.domain.repository.QuizResultRepository
import com.examen.civique.domain.repository.QuizSessionRepository
import com.examen.civique.domain.session.QuizSessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuizViewModel(
    private val questionRepository: QuestionRepository,
    private val resultRepository: QuizResultRepository,
    sessionRepository: QuizSessionRepository,
    private val engine: QuizEngine = QuizEngine()
) : ViewModel() {

    private val sessionManager = QuizSessionManager(
        sessionRepository = sessionRepository,
        questionRepository = questionRepository
    )

    private val _uiState = MutableStateFlow(
        sessionManager.restoreSession() ?: QuizState()
    )

    val uiState: StateFlow<QuizState> =
        _uiState.asStateFlow()

    private val _hasSavedSession = MutableStateFlow(sessionManager.hasSession())
    val hasSavedSession: StateFlow<Boolean> = _hasSavedSession.asStateFlow()

    private var resultSaved = false

    val wrongAnswers: List<QuizAnswer>
        get() = _uiState.value.wrongAnswers

    fun selectAnswer(index: Int) {
        _uiState.value = engine.selectAnswer(_uiState.value, index)
        persistCurrentSession()
    }

    fun validateAnswer() {
        _uiState.value = engine.validateAnswer(_uiState.value)
        persistCurrentSession()
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
            sessionManager.abandonSession()
            _hasSavedSession.value = false
            saveResult(nextState)
        } else {
            persistCurrentSession()
        }
    }

    fun startNewQuiz() {
        resultSaved = false
        _uiState.value = engine.createInitialState(questionRepository.getQuestions())
        sessionManager.startNewSession(_uiState.value)
        _hasSavedSession.value = _uiState.value.questions.isNotEmpty()
    }

    fun continueQuiz(): Boolean {
        val restored = sessionManager.restoreSession() ?: run {
            _hasSavedSession.value = false
            return false
        }
        resultSaved = false
        _uiState.value = restored
        _hasSavedSession.value = true
        return true
    }

    private fun persistCurrentSession() {
        sessionManager.saveSession(_uiState.value)
        _hasSavedSession.value = !_uiState.value.quizFinished &&
                _uiState.value.questions.isNotEmpty()
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
