package com.examen.civique.domain.engine

import com.examen.civique.domain.model.Question
import com.examen.civique.domain.model.QuizAnswer
import com.examen.civique.domain.model.QuizState

class QuizEngine {

    fun createInitialState(questions: List<Question>): QuizState {
        return QuizState(
            questions = questions
                .shuffled()
                .map { it.shuffledAnswers() }
        )
    }

    fun selectAnswer(state: QuizState, index: Int): QuizState {
        if (state.answerValidated || state.quizFinished) return state
        val currentQuestion = state.currentQuestion ?: return state
        if (index !in currentQuestion.answers.indices) return state
        return state.copy(selectedAnswerIndex = index)
    }

    fun validateAnswer(state: QuizState): QuizState {
        if (state.answerValidated || state.quizFinished) return state
        val selectedAnswer = state.selectedAnswerIndex ?: return state
        val currentQuestion = state.currentQuestion ?: return state
        val isCorrect = selectedAnswer == currentQuestion.correctAnswerIndex
        val quizAnswer = QuizAnswer(currentQuestion, selectedAnswer, isCorrect)
        return state.copy(
            answerValidated = true,
            score = if (isCorrect) state.score + 1 else state.score,
            answers = state.answers + quizAnswer
        )
    }

    fun nextQuestion(state: QuizState): QuizState {
        if (state.quizFinished || !state.answerValidated) return state
        return if (state.currentQuestionIndex < state.questions.lastIndex) {
            state.copy(
                currentQuestionIndex = state.currentQuestionIndex + 1,
                selectedAnswerIndex = null,
                answerValidated = false
            )
        } else {
            state.copy(quizFinished = true)
        }
    }
}
