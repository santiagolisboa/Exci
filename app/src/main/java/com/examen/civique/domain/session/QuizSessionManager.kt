package com.examen.civique.domain.session

import com.examen.civique.domain.model.QuizAnswer
import com.examen.civique.domain.model.QuizSession
import com.examen.civique.domain.model.QuizState
import com.examen.civique.domain.model.SavedQuizAnswer
import com.examen.civique.domain.model.SavedQuizQuestion
import com.examen.civique.domain.repository.QuestionRepository
import com.examen.civique.domain.repository.QuizSessionRepository

class QuizSessionManager(
    private val sessionRepository: QuizSessionRepository,
    private val questionRepository: QuestionRepository
) {
    fun hasSession(): Boolean = restoreSession() != null

    fun saveSession(state: QuizState) {
        if (state.quizFinished || state.questions.isEmpty()) {
            sessionRepository.deleteSession()
            return
        }

        sessionRepository.saveSession(
            QuizSession(
                questions = state.questions.map { question ->
                    SavedQuizQuestion(
                        id = question.id,
                        answers = question.answers,
                        correctAnswerIndex = question.correctAnswerIndex
                    )
                },
                currentQuestionIndex = state.currentQuestionIndex,
                selectedAnswerIndex = state.selectedAnswerIndex,
                answerValidated = state.answerValidated,
                score = state.score,
                answers = state.answers.map { answer ->
                    SavedQuizAnswer(
                        questionId = answer.question.id,
                        selectedAnswerIndex = answer.selectedAnswerIndex,
                        isCorrect = answer.isCorrect
                    )
                }
            )
        )
    }

    fun startNewSession(state: QuizState) {
        sessionRepository.deleteSession()
        saveSession(state)
    }

    fun restoreSession(): QuizState? {
        val snapshot = sessionRepository.getSession() ?: return null
        val availableById = questionRepository.getQuestions().associateBy { it.id }

        val restoredQuestions = snapshot.questions.map { saved ->
            val source = availableById[saved.id] ?: return null
            if (saved.answers.size != source.answers.size ||
                saved.correctAnswerIndex !in saved.answers.indices
            ) return null
            source.copy(
                answers = saved.answers,
                correctAnswerIndex = saved.correctAnswerIndex
            )
        }

        if (restoredQuestions.isEmpty() ||
            snapshot.currentQuestionIndex !in restoredQuestions.indices ||
            snapshot.score !in 0..snapshot.answers.size
        ) return null

        val restoredById = restoredQuestions.associateBy { it.id }
        val restoredAnswers = snapshot.answers.map { saved ->
            val question = restoredById[saved.questionId] ?: return null
            if (saved.selectedAnswerIndex !in question.answers.indices) return null
            QuizAnswer(
                question = question,
                selectedAnswerIndex = saved.selectedAnswerIndex,
                isCorrect = saved.isCorrect
            )
        }

        val selected = snapshot.selectedAnswerIndex
        if (selected != null &&
            selected !in restoredQuestions[snapshot.currentQuestionIndex].answers.indices
        ) return null

        return QuizState(
            questions = restoredQuestions,
            currentQuestionIndex = snapshot.currentQuestionIndex,
            selectedAnswerIndex = selected,
            answerValidated = snapshot.answerValidated,
            score = snapshot.score,
            answers = restoredAnswers
        )
    }

    fun abandonSession() = sessionRepository.deleteSession()
}
