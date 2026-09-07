package com.examen.civique.data.local

import com.examen.civique.data.repository.StaticQuestionRepository
import com.examen.civique.domain.validation.CourseCatalogValidator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CourseCatalogTest {
    private val repository = StaticQuestionRepository()

    @Test
    fun `catalog contains six courses and twenty eight unique lessons`() {
        val lessons = courses.flatMap { it.lessons }

        assertEquals(6, courses.size)
        assertEquals(28, lessons.size)
        assertEquals(6, courses.map { it.id }.distinct().size)
        assertEquals(28, lessons.map { it.id }.distinct().size)
    }

    @Test
    fun `all 244 questions are linked to an existing lesson`() {
        val questions = repository.getQuestions()
        val lessonIds = courses.flatMap { it.lessons }.map { it.id }.toSet()

        assertEquals(244, questions.size)
        assertEquals(244, questions.count { it.lessonId in lessonIds })
        assertTrue(CourseCatalogValidator.validate(courses, questions).isEmpty())
    }

    @Test
    fun `every lesson exposes official questions through the shared repository`() {
        val lessons = courses.flatMap { it.lessons }
        val linkedQuestions = lessons.flatMap { lesson ->
            repository.getQuestionsForLesson(lesson.id)
        }

        assertTrue(lessons.all { repository.getQuestionsForLesson(it.id).isNotEmpty() })
        assertEquals(244, linkedQuestions.size)
        assertEquals(244, linkedQuestions.map { it.id }.distinct().size)
    }

    @Test
    fun `lessons contain structured content and valid official sources`() {
        courses.flatMap { it.lessons }.forEach { lesson ->
            assertTrue(lesson.summary.isNotBlank())
            assertTrue(lesson.sections.isNotEmpty())
            assertTrue(lesson.sources.isNotEmpty())
            assertTrue(lesson.sources.all { it.url.startsWith("https://") })
        }
    }

    @Test
    fun `every question explanation is taught in its linked lesson`() {
        val lessonsById = courses.flatMap { it.lessons }.associateBy { it.id }

        repository.getQuestions().forEach { question ->
            val lessonText = lessonsById.getValue(question.lessonId).sections.joinToString(" ")
            assertTrue(
                "Missing explanation for ${question.id}",
                lessonText.contains(question.explanation)
            )
        }
    }
}
