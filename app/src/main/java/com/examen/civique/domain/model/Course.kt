package com.examen.civique.domain.model

data class Course(
    val id: Int,
    val title: String,
    val description: String,
    val icon: String,
    val lessons: List<Lesson>
)

data class Lesson(
    val id: Int,
    val title: String,
    val content: String
)
