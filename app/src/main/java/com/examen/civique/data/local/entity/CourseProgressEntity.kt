package com.examen.civique.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "course_progress")
data class CourseProgressEntity(

    @PrimaryKey
    val courseId: Int,

    val completed: Boolean = false,

    val completedAt: Long? = null,
    val completedLessonIds: String = "",

    val lastLessonId: String? = null
)
