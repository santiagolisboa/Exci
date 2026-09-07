package com.examen.civique.domain.engine

import com.examen.civique.domain.model.Question
import com.examen.civique.domain.model.QuizState

class LessonPracticeEngine(
    private val quizEngine: QuizEngine = QuizEngine()
) {
    fun createSession(lessonId: String, questions: List<Question>): QuizState =
        quizEngine.createInitialState(
            questions.filter { it.lessonId == lessonId }
        )

    fun selectAnswer(state: QuizState, index: Int): QuizState =
        quizEngine.selectAnswer(state, index)

    fun validateAnswer(state: QuizState): QuizState =
        quizEngine.validateAnswer(state)

    fun nextQuestion(state: QuizState): QuizState =
        quizEngine.nextQuestion(state)
}
