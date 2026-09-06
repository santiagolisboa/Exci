package com.examen.civique.domain.repository

import com.examen.civique.domain.model.QuizSession

interface QuizSessionRepository {
    fun saveSession(session: QuizSession)
    fun getSession(): QuizSession?
    fun deleteSession()
}
