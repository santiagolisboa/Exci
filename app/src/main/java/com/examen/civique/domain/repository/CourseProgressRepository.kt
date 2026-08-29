package com.examen.civique.domain.repository

import com.examen.civique.domain.model.CourseProgress
import kotlinx.coroutines.flow.Flow

interface CourseProgressRepository {
    suspend fun markCourseCompleted(courseId: Int)
    fun getAllProgress(): Flow<List<CourseProgress>>
    fun getProgressForCourse(courseId: Int): Flow<CourseProgress?>
    fun getCompletedCourseCount(): Flow<Int>
}
