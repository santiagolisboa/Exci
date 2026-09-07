package com.examen.civique.ui.exam

import android.os.SystemClock
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examen.civique.domain.engine.ExamEngine
import com.examen.civique.domain.engine.AnswerAttemptFactory
import com.examen.civique.domain.model.Question
import com.examen.civique.domain.model.ExamFinishReason
import com.examen.civique.domain.model.ExamAnswerReview
import com.examen.civique.domain.model.ExamState
import com.examen.civique.domain.repository.QuestionRepository
import com.examen.civique.domain.repository.LearningRepository
import com.examen.civique.domain.repository.FavoriteQuestionRepository
import com.examen.civique.domain.repository.AnswerAttemptRepository
import com.examen.civique.domain.model.AnswerAttempt
import com.examen.civique.domain.model.AnswerSource
import com.examen.civique.domain.model.SessionType
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class ExamViewModel(
    private val questionRepository: QuestionRepository,
    private val learningRepository: LearningRepository,
    private val attemptRepository: AnswerAttemptRepository,
    private val favoriteRepository: FavoriteQuestionRepository,
    private val engine: ExamEngine = ExamEngine()
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        engine.createInitialState(questionRepository.getQuestions())
    )

    val uiState: StateFlow<ExamState> =
        _uiState.asStateFlow()

    private var timerJob: Job? = null
    private var deadlineElapsedRealtimeMillis: Long? = null
    private var examStarted = false
    private var resultSaved = false
    private val attemptMutex = Mutex()

    val favoriteIds = favoriteRepository.observeFavoriteIds().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), emptySet()
    )

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

    fun selectAnswer(index: Int) {
        val before = _uiState.value
        val question = before.currentQuestion ?: return
        val previousSelection = before.selectedAnswers[question.id]
        val after = engine.selectAnswer(before, index)
        _uiState.value = after
        if (AnswerAttemptFactory.shouldRecordExamSelection(previousSelection, index) &&
            after.selectedAnswers[question.id] == index
        ) {
            val attempt = AnswerAttempt(
                questionId = question.id,
                category = question.category,
                selectedAnswer = question.answers.getOrNull(index),
                isCorrect = index == question.correctAnswerIndex,
                answeredAt = System.currentTimeMillis(),
                source = AnswerSource.EXAM
            )
            viewModelScope.launch {
                attemptMutex.withLock {
                    attemptRepository.recordAttempt(attempt)
                    learningRepository.updateErrorProjection(attempt)
                }
            }
        }
    }

    fun toggleFavorite(questionId: String) {
        val favorite = questionId !in favoriteIds.value
        viewModelScope.launch { favoriteRepository.setFavorite(questionId, favorite) }
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
        if (_uiState.value.examFinished) return
        stopTimer()
        _uiState.value = engine.finishExam(_uiState.value, reason)
        saveResult()
    }

    private fun saveResult() {
        if (resultSaved) return
        resultSaved = true
        val state = _uiState.value
        val now = System.currentTimeMillis()
        val answers = state.questions.mapNotNull { question ->
            val selected = state.selectedAnswers[question.id] ?: return@mapNotNull null
            AnswerAttempt(
                questionId = question.id,
                category = question.category,
                selectedAnswer = question.answers.getOrNull(selected),
                isCorrect = selected == question.correctAnswerIndex,
                answeredAt = now,
                source = AnswerSource.EXAM
            )
        }
        viewModelScope.launch {
            learningRepository.saveCompletedSession(
                SessionType.EXAM,
                answers,
                totalQuestions = state.questions.size
            )
        }
    }

    fun startExam() {
        if (examStarted) return

        examStarted = true
        startTimer()
    }

    private fun startTimer() {
        stopTimer()

        deadlineElapsedRealtimeMillis =
            SystemClock.elapsedRealtime() +
                    _uiState.value.remainingSeconds * 1000L

        timerJob = viewModelScope.launch {

            while (
                isActive &&
                !_uiState.value.examFinished
            ) {
                val deadline = deadlineElapsedRealtimeMillis ?: break
                val remainingMillis =
                    deadline - SystemClock.elapsedRealtime()
                val remainingSeconds =
                    if (remainingMillis <= 0L) {
                        0
                    } else {
                        ((remainingMillis + 999L) / 1000L).toInt()
                    }

                _uiState.value = engine.updateRemainingTime(
                    state = _uiState.value,
                    remainingSeconds = remainingSeconds
                )

                if (_uiState.value.examFinished) {
                    deadlineElapsedRealtimeMillis = null
                    saveResult()
                    break
                }

                val delayUntilNextSecond =
                    remainingMillis - (remainingSeconds - 1L) * 1000L

                delay(delayUntilNextSecond.coerceAtLeast(1L))
            }
        }
    }

    fun startNewExam() {
        stopTimer()
        resultSaved = false

        _uiState.value = engine.createInitialState(questionRepository.getQuestions())

        examStarted = true
        startTimer()
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
        deadlineElapsedRealtimeMillis = null
    }

    override fun onCleared() {
        stopTimer()

        super.onCleared()
    }
}
