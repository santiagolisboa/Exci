package com.examen.civique.domain.engine

import com.examen.civique.data.repository.StaticQuestionRepository
import com.examen.civique.domain.model.Question
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExamEngineTest {
    @Test
    fun `repository exposes 244 unique questions`() {
        val questions = StaticQuestionRepository().getQuestions()

        assertEquals(244, questions.size)
        assertEquals(244, questions.map { it.id }.distinct().size)
    }

    @Test
    fun `exam selection receives the complete repository and selects 40 questions`() {
        val repositoryQuestions = StaticQuestionRepository().getQuestions()
        var selectionInput: List<Question> = emptyList()
        val engine = ExamEngine { allQuestions ->
            selectionInput = allQuestions
            allQuestions.reversed()
        }

        val state = engine.createInitialState(repositoryQuestions)

        assertEquals(repositoryQuestions, selectionInput)
        assertEquals(40, state.questions.size)
        assertTrue(state.questions.all { selected ->
            repositoryQuestions.any { it.id == selected.id }
        })
        assertEquals(repositoryQuestions.last().id, state.questions.first().id)
    }

    @Test
    fun `selecting another exam answer replaces the previous selection`() {
        val questions = StaticQuestionRepository().getQuestions()
        val engine = ExamEngine { it }
        val initial = engine.createInitialState(questions)

        val changed = engine.selectAnswer(
            engine.selectAnswer(initial, 0),
            3
        )

        assertEquals(3, changed.selectedAnswers[changed.currentQuestion!!.id])
        assertEquals(1, changed.selectedAnswers.size)
    }
}
