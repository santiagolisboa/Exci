package com.examen.civique.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.examen.civique.data.local.entity.CourseProgressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseProgressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProgress(
        progress: CourseProgressEntity
    )

    @Query(
        """
        SELECT * 
        FROM course_progress
        """
    )
    fun getAllProgress(): Flow<List<CourseProgressEntity>>

    @Query(
        """
        SELECT * 
        FROM course_progress 
        WHERE courseId = :courseId
        LIMIT 1
        """
    )
    fun getProgressForCourse(
        courseId: Int
    ): Flow<CourseProgressEntity?>

    @Query(
        """
        SELECT COUNT(*) 
        FROM course_progress 
        WHERE completed = 1
        """
    )
    fun getCompletedCourseCount(): Flow<Int>
}