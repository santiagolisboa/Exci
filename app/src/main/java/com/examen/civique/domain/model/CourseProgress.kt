package com.examen.civique.domain.model

data class CourseProgress(
    val courseId: Int,
    val completed: Boolean = false,
    val completedAt: Long? = null
)
