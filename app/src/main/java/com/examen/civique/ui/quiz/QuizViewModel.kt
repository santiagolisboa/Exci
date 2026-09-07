package com.examen.civique.ui.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examen.civique.domain.engine.QuizEngine
import com.examen.civique.domain.engine.AnswerAttemptFactory
import com.examen.civique.domain.model.QuizAnswer
import com.examen.civique.domain.model.QuizState
import com.examen.civique.domain.repository.QuestionRepository
import com.examen.civique.domain.repository.QuizSessionRepository
import com.examen.civique.domain.repository.LearningRepository
import com.examen.civique.domain.repository.FavoriteQuestionRepository
import com.examen.civique.domain.repository.AnswerAttemptRepository
import com.examen.civique.domain.model.AnswerAttempt
import com.examen.civique.domain.model.SessionType
import com.examen.civique.domain.model.toAnswerSource
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import com.examen.civique.domain.session.QuizSessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class QuizViewModel(
    private val questionRepository: QuestionRepository,
    sessionRepository: QuizSessionRepository,
    private val learningRepository: LearningRepository,
    private val attemptRepository: AnswerAttemptRepository,
    private val favoriteRepository: FavoriteQuestionRepository,
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
    private val attemptMutex = Mutex()

    val favoriteIds = favoriteRepository.observeFavoriteIds().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), emptySet()
    )

    val wrongAnswers: List<QuizAnswer>
        get() = _uiState.value.wrongAnswers

    fun selectAnswer(index: Int) {
        _uiState.value = engine.selectAnswer(_uiState.value, index)
        persistCurrentSession()
    }

    fun validateAnswer() {
        val before = _uiState.value
        val after = engine.validateAnswer(before)
        _uiState.value = after
        val attempt = AnswerAttemptFactory.fromQuizValidation(before, after, System.currentTimeMillis())
        if (attempt != null) {
            viewModelScope.launch {
                attemptMutex.withLock {
                    attemptRepository.recordAttempt(attempt)
                    learningRepository.updateErrorProjection(attempt)
                }
            }
        }
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
        startQuiz(questionRepository.getQuestions(), SessionType.QUIZ)
    }

    fun startQuiz(questionIds: Collection<String>, type: SessionType) {
        val ids = questionIds.toSet()
        startQuiz(questionRepository.getQuestions().filter { it.id in ids }, type)
    }

    private fun startQuiz(questions: List<com.examen.civique.domain.model.Question>, type: SessionType) {
        resultSaved = false
        _uiState.value = engine.createInitialState(questions, type)
        sessionManager.startNewSession(_uiState.value)
        _hasSavedSession.value = _uiState.value.questions.isNotEmpty()
    }

    fun saveActiveSession() = persistCurrentSession()

    fun toggleFavorite(questionId: String) {
        val favorite = questionId !in favoriteIds.value
        viewModelScope.launch { favoriteRepository.setFavorite(questionId, favorite) }
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
            learningRepository.saveCompletedSession(
                type = state.sessionType,
                answers = state.answers.map { it.toAttempt(state.sessionType) }
            )
        }
    }

    private fun QuizAnswer.toAttempt(type: SessionType) = AnswerAttempt(
        questionId = question.id,
        category = question.category,
        selectedAnswer = question.answers.getOrNull(selectedAnswerIndex),
        isCorrect = isCorrect,
        answeredAt = System.currentTimeMillis(),
        source = type.toAnswerSource()
    )
}
