package com.examen.civique.domain.model

enum class ExitConsequence { NONE, QUIZ_SAVED, EXAM_FINISHED }

object ExitPolicy {
    fun forRoute(route: String?, quizActive: Boolean, examActive: Boolean): ExitConsequence = when {
        route == "quiz" && quizActive -> ExitConsequence.QUIZ_SAVED
        route == "exam" && examActive -> ExitConsequence.EXAM_FINISHED
        else -> ExitConsequence.NONE
    }
}
