package com.examen.civique.domain.repository

import com.examen.civique.domain.model.Question

interface QuestionRepository {
    fun getQuestions(): List<Question>

    fun getQuestionsForLesson(lessonId: String): List<Question> =
        getQuestions().filter { it.lessonId == lessonId }
}
