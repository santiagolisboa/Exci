package com.examen.civique.domain.model

fun QuestionCategory.displayName(): String = when (this) {
    QuestionCategory.PRINCIPES_VALEURS -> "Principes et valeurs"
    QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE -> "Institutions et politique"
    QuestionCategory.GEOGRAPHIE -> "Géographie"
    QuestionCategory.HISTOIRE -> "Histoire"
    QuestionCategory.SOCIETE -> "Société"
    QuestionCategory.ECONOMIE -> "Économie"
}
