package com.examen.civique.domain.engine

import com.examen.civique.domain.model.Course
import com.examen.civique.domain.model.CourseProgress

class CourseProgressEngine {
    fun openLesson(
        progress: CourseProgress?,
        courseId: Int,
        lessonId: String
    ): CourseProgress = (progress ?: CourseProgress(courseId)).copy(
        lastLessonId = lessonId
    )

    fun completeLesson(
        progress: CourseProgress?,
        course: Course,
        lessonId: String,
        completedAt: Long
    ): CourseProgress {
        require(course.lessons.any { it.id == lessonId })
        val current = progress ?: CourseProgress(course.id)
        val completedLessons = current.completedLessonIds + lessonId
        val courseCompleted = current.completed ||
                course.lessons.all { it.id in completedLessons }
        return current.copy(
            completed = courseCompleted,
            completedAt = if (courseCompleted) current.completedAt ?: completedAt else null,
            completedLessonIds = completedLessons,
            lastLessonId = lessonId
        )
    }

    fun completedLessonCount(progress: CourseProgress?, course: Course): Int =
        if (progress?.completed == true) {
            course.lessons.size
        } else {
            course.lessons.count { it.id in progress?.completedLessonIds.orEmpty() }
        }
}
