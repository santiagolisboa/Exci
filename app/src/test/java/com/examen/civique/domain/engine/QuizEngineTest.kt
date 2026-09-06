package com.examen.civique.domain.engine

import com.examen.civique.data.repository.StaticQuestionRepository
import com.examen.civique.domain.model.QuizState
import org.junit.Assert.assertEquals
import org.junit.Test

class QuizEngineTest {
    private val engine = QuizEngine()
    private val question = StaticQuestionRepository().getQuestions().first()

    @Test
    fun `selecting another answer replaces the previous selection`() {
        val initial = QuizState(questions = listOf(question))

        val changed = engine.selectAnswer(
            engine.selectAnswer(initial, 0),
            2
        )

        assertEquals(2, changed.selectedAnswerIndex)
        assertEquals(0, changed.answers.size)
    }

    @Test
    fun `validated answer cannot be changed`() {
        val selected = engine.selectAnswer(
            QuizState(questions = listOf(question)),
            1
        )
        val validated = engine.validateAnswer(selected)

        assertEquals(validated, engine.selectAnswer(validated, 2))
    }
}
