package com.examen.civique.data.repository

import com.examen.civique.data.local.courses
import com.examen.civique.domain.model.Course
import com.examen.civique.domain.repository.CourseRepository

class StaticCourseRepository : CourseRepository {
    override fun getCourses(): List<Course> = courses
}
