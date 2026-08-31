package com.examen.civique.domain.engine

import com.examen.civique.domain.model.ExamAnswerReview
import com.examen.civique.domain.model.ExamFinishReason
import com.examen.civique.domain.model.ExamState
import com.examen.civique.domain.model.Question

private const val EXAM_QUESTION_COUNT = 40

class ExamEngine {

    fun createInitialState(questions: List<Question>): ExamState {
        return ExamState(
            questions = questions
                .shuffled()
                .take(EXAM_QUESTION_COUNT)
                .map { it.shuffledAnswers() }
        )
    }

    fun selectAnswer(state: ExamState, index: Int): ExamState {
        if (state.examFinished) return state
        val currentQuestion = state.questions[state.currentQuestionIndex]
        if (index !in currentQuestion.answers.indices) return state
        return state.copy(
            selectedAnswers = state.selectedAnswers + (currentQuestion.id to index)
        )
    }

    fun nextQuestion(state: ExamState): ExamState {
        if (state.examFinished) return state
        return if (state.currentQuestionIndex < state.questions.lastIndex) {
            state.copy(currentQuestionIndex = state.currentQuestionIndex + 1)
        } else {
            state
        }
    }

    fun previousQuestion(state: ExamState): ExamState {
        if (state.examFinished) return state
        return if (state.currentQuestionIndex > 0) {
            state.copy(currentQuestionIndex = state.currentQuestionIndex - 1)
        } else {
            state
        }
    }

    fun goToQuestion(state: ExamState, index: Int): ExamState {
        if (state.examFinished) return state
        return if (index in state.questions.indices) {
            state.copy(currentQuestionIndex = index)
        } else {
            state
        }
    }

    fun finishExam(state: ExamState, reason: ExamFinishReason): ExamState {
        if (state.examFinished) return state
        return state.copy(
            examFinished = true,
            finishReason = reason
        )
    }

    fun updateRemainingTime(state: ExamState, remainingSeconds: Int): ExamState {
        if (state.examFinished) return state
        val remaining = remainingSeconds.coerceAtLeast(0)
        return if (remaining == 0) {
            state.copy(
                remainingSeconds = 0,
                examFinished = true,
                finishReason = ExamFinishReason.TIME_UP
            )
        } else {
            state.copy(remainingSeconds = remaining)
        }
    }

    fun calculateScore(state: ExamState): Int {
        return state.questions.count { question ->
            val selectedAnswer = state.selectedAnswers[question.id]
            selectedAnswer == question.correctAnswerIndex
        }
    }

    fun getWrongAnswersCount(state: ExamState): Int {
        return state.selectedAnswers.count { (questionId, selectedIndex) ->
            val question = state.questions.find { it.id == questionId }
            question != null && selectedIndex != question.correctAnswerIndex
        }
    }

    fun getWrongQuestions(state: ExamState): List<Question> {
        return state.questions.filter { question ->
            val selectedIndex = state.selectedAnswers[question.id]
            selectedIndex != null && selectedIndex != question.correctAnswerIndex
        }
    }

    fun getUnansweredCount(state: ExamState): Int {
        return state.questions.size - state.selectedAnswers.size
    }

    fun getAnswerReviews(state: ExamState): List<ExamAnswerReview> {
        return state.questions.map { question ->
            val selectedAnswerIndex = state.selectedAnswers[question.id]
            ExamAnswerReview(
                question = question,
                selectedAnswerIndex = selectedAnswerIndex,
                isCorrect = selectedAnswerIndex != null &&
                        selectedAnswerIndex == question.correctAnswerIndex
            )
        }
    }
}
