package com.examen.civique.domain.validation

import com.examen.civique.domain.model.Course
import com.examen.civique.domain.model.LessonSection
import com.examen.civique.domain.model.Question

object CourseCatalogValidator {
    fun validate(courses: List<Course>, questions: List<Question>): List<String> {
        val errors = mutableListOf<String>()
        val lessons = courses.flatMap { it.lessons }

        duplicateValues(courses.map { it.id }).forEach {
            errors += "Identifiant de cours dupliqué : $it"
        }
        duplicateValues(lessons.map { it.id }).forEach {
            errors += "Identifiant de leçon dupliqué : $it"
        }

        courses.forEach { course ->
            if (course.title.isBlank() || course.description.isBlank()) {
                errors += "Cours incomplet : ${course.id}"
            }
            if (course.lessons.isEmpty()) errors += "Cours sans leçon : ${course.id}"
        }

        lessons.forEach { lesson ->
            if (lesson.title.isBlank() || lesson.summary.isBlank() || lesson.sections.isEmpty()) {
                errors += "Leçon incomplète : ${lesson.id}"
            }
            lesson.sections.forEach { section ->
                val valid = when (section) {
                    is LessonSection.Paragraph -> section.text.isNotBlank()
                    is LessonSection.KeyFact -> section.title.isNotBlank() && section.text.isNotBlank()
                    is LessonSection.BulletList -> section.items.isNotEmpty() &&
                            section.items.all { it.isNotBlank() }
                }
                if (!valid) errors += "Section vide : ${lesson.id}"
            }
            lesson.sources.forEach { source ->
                if (source.title.isBlank() || !source.url.startsWith("https://")) {
                    errors += "Source invalide : ${lesson.id}"
                }
            }
        }

        val lessonIds = lessons.map { it.id }.toSet()
        questions.forEach { question ->
            if (question.lessonId !in lessonIds) {
                errors += "Question orpheline : ${question.id} -> ${question.lessonId}"
            }
        }

        duplicateValues(questions.map { it.id }).forEach {
            errors += "Identifiant de question dupliqué : $it"
        }

        return errors.distinct()
    }

    private fun <T> duplicateValues(values: List<T>): Set<T> =
        values.groupingBy { it }.eachCount().filterValues { it > 1 }.keys
}
