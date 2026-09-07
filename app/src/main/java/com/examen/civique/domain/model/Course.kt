package com.examen.civique.domain.model

data class Course(
    val id: Int,
    val title: String,
    val description: String,
    val icon: String,
    val lessons: List<Lesson>
)

data class Lesson(
    val id: String,
    val title: String,
    val summary: String,
    val sections: List<LessonSection>,
    val sources: List<LessonSource> = emptyList()
)

sealed interface LessonSection {
    data class Paragraph(val text: String) : LessonSection
    data class BulletList(
        val title: String? = null,
        val items: List<String>
    ) : LessonSection
    data class KeyFact(val title: String, val text: String) : LessonSection
}

data class LessonSource(
    val title: String,
    val url: String
)
