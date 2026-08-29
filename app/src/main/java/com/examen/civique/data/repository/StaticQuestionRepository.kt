package com.examen.civique.data.repository

import com.examen.civique.data.local.questions
import com.examen.civique.domain.model.Question
import com.examen.civique.domain.repository.QuestionRepository

class StaticQuestionRepository : QuestionRepository {
    override fun getQuestions(): List<Question> = questions
}
