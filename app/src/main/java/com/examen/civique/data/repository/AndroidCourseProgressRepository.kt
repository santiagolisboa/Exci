package com.examen.civique.data.repository

import com.examen.civique.data.local.dao.CourseProgressDao
import com.examen.civique.data.local.entity.CourseProgressEntity
import com.examen.civique.domain.model.CourseProgress
import com.examen.civique.domain.repository.CourseProgressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AndroidCourseProgressRepository(
    private val courseProgressDao: CourseProgressDao
) : CourseProgressRepository {

    override suspend fun saveProgress(progress: CourseProgress) {
        courseProgressDao.saveProgress(progress.toEntity())
    }

    override fun getAllProgress(): Flow<List<CourseProgress>> =
        courseProgressDao.getAllProgress().map { entities ->
            entities.map { it.toDomain() }
        }

    override fun getProgressForCourse(
        courseId: Int
    ): Flow<CourseProgress?> =
        courseProgressDao.getProgressForCourse(courseId).map { it?.toDomain() }

    override fun getCompletedCourseCount(): Flow<Int> =
        courseProgressDao.getCompletedCourseCount()
}

fun CourseProgressEntity.toDomain(): CourseProgress {
    return CourseProgress(
        courseId = courseId,
        completed = completed,
        completedAt = completedAt,
        completedLessonIds = completedLessonIds
            .split(',')
            .filter { it.isNotBlank() }
            .toSet(),
        lastLessonId = lastLessonId
    )
}

private fun CourseProgress.toEntity() = CourseProgressEntity(
    courseId = courseId,
    completed = completed,
    completedAt = completedAt,
    completedLessonIds = completedLessonIds.sorted().joinToString(","),
    lastLessonId = lastLessonId
)
