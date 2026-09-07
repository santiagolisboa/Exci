package com.examen.civique.domain.engine

import com.examen.civique.data.local.courses
import com.examen.civique.data.repository.StaticQuestionRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class LessonPracticeEngineTest {
    private val repository = StaticQuestionRepository()
    private val engine = LessonPracticeEngine()

    @Test
    fun `practice uses only questions linked to the selected lesson`() {
        val lessonId = courses.first().lessons.first().id
        val expectedIds = repository.getQuestionsForLesson(lessonId).map { it.id }.toSet()

        val state = engine.createSession(lessonId, repository.getQuestions())

        assertTrue(state.questions.isNotEmpty())
        assertEquals(expectedIds, state.questions.map { it.id }.toSet())
        assertTrue(state.questions.all { it.lessonId == lessonId })
    }

    @Test
    fun `practice handles a lesson without questions`() {
        assertTrue(engine.createSession("missing", repository.getQuestions()).questions.isEmpty())
    }
}
