package com.examen.civique.domain.repository

import com.examen.civique.domain.model.Course

interface CourseRepository {
    fun getCourses(): List<Course>
}
