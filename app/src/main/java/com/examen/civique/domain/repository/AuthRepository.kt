package com.examen.civique.domain.repository

import com.examen.civique.domain.model.AuthResult
import com.examen.civique.domain.model.AuthState
import com.examen.civique.domain.model.UserAccount
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    val currentUser: UserAccount?
    val authState: StateFlow<AuthState>
    suspend fun signUp(email: String, password: String): AuthResult
    suspend fun signIn(email: String, password: String): AuthResult
    suspend fun signOut()
}

