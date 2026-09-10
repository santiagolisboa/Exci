package com.examen.civique.data.repository

import com.examen.civique.domain.model.AuthResult
import com.examen.civique.domain.model.AuthState
import com.examen.civique.domain.model.UserAccount
import com.examen.civique.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UnavailableAuthRepository : AuthRepository {
    private val state = MutableStateFlow<AuthState>(AuthState.Guest)
    override val currentUser: UserAccount? get() = (state.value as? AuthState.Authenticated)?.user
    override val authState: StateFlow<AuthState> = state.asStateFlow()

    override suspend fun signUp(email: String, password: String): AuthResult = unavailable()
    override suspend fun signIn(email: String, password: String): AuthResult = unavailable()
    override suspend fun signOut() { state.value = AuthState.Guest }

    private fun unavailable() = AuthResult.Failure(
        "La création de compte et la synchronisation seront disponibles dès que le service sécurisé sera connecté."
    )
}

