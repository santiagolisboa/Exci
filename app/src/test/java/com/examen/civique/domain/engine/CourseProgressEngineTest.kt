package com.examen.civique.domain.engine

import com.examen.civique.data.local.courses
import com.examen.civique.domain.model.CourseProgress
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CourseProgressEngineTest {
    private val engine = CourseProgressEngine()
    private val course = courses.first()

    @Test
    fun `opening a lesson stores the resumable position without completing it`() {
        val lessonId = course.lessons[1].id

        val progress = engine.openLesson(null, course.id, lessonId)

        assertEquals(lessonId, progress.lastLessonId)
        assertFalse(progress.completed)
        assertTrue(progress.completedLessonIds.isEmpty())
    }

    @Test
    fun `course completes only after all its lessons`() {
        var progress: CourseProgress? = null

        course.lessons.dropLast(1).forEachIndexed { index, lesson ->
            progress = engine.completeLesson(progress, course, lesson.id, index.toLong())
        }
        assertFalse(progress!!.completed)

        progress = engine.completeLesson(progress, course, course.lessons.last().id, 100L)

        assertTrue(progress!!.completed)
        assertEquals(course.lessons.size, engine.completedLessonCount(progress, course))
        assertEquals(100L, progress!!.completedAt)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `unknown lesson cannot be completed`() {
        engine.completeLesson(null, course, "unknown", 0L)
    }
}
