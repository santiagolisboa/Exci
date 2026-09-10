package com.examen.civique.domain.model

data class UserAccount(
    val id: String,
    val email: String,
    val displayName: String? = null
)

sealed interface AuthState {
    data object Guest : AuthState
    data class Authenticated(val user: UserAccount) : AuthState
}

sealed interface AuthResult {
    data class Success(val user: UserAccount) : AuthResult
    data class Failure(val message: String) : AuthResult
}

enum class SyncStatus { LOCAL_ONLY, SYNCED, PENDING, ERROR }

data class SyncSummary(
    val status: SyncStatus = SyncStatus.LOCAL_ONLY,
    val message: String = "Données enregistrées sur cet appareil"
)

