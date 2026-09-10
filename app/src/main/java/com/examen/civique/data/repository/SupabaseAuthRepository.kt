package com.examen.civique.data.repository

import com.examen.civique.domain.model.AuthResult
import com.examen.civique.domain.model.AuthState
import com.examen.civique.domain.model.UserAccount
import com.examen.civique.domain.repository.AuthRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SupabaseAuthRepository(
    private val client: SupabaseClient
) : AuthRepository {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val state = MutableStateFlow<AuthState>(AuthState.Guest)

    override val currentUser: UserAccount?
        get() = (state.value as? AuthState.Authenticated)?.user

    override val authState: StateFlow<AuthState> = state.asStateFlow()

    init {
        scope.launch {
            client.auth.sessionStatus.collectLatest { status ->
                state.value = when (status) {
                    is SessionStatus.Authenticated ->
                        status.session.user?.toAccount()?.let(AuthState::Authenticated) ?: AuthState.Guest
                    else -> AuthState.Guest
                }
            }
        }
    }

    override suspend fun signUp(email: String, password: String): AuthResult = authCall {
        val returnedUser = client.auth.signUpWith(Email) {
            this.email = email
            this.password = password
        }
        val user = client.auth.currentUserOrNull() ?: returnedUser
        user?.toAccount()?.let(AuthResult::Success)
            ?: AuthResult.Failure("Le compte n’a pas pu être créé.")
    }

    override suspend fun signIn(email: String, password: String): AuthResult = authCall {
        client.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }
        client.auth.currentUserOrNull()?.toAccount()?.let(AuthResult::Success)
            ?: AuthResult.Failure("La connexion n’a pas pu être établie.")
    }

    override suspend fun signOut() {
        runCatching { client.auth.signOut() }
        state.value = AuthState.Guest
    }

    private suspend fun authCall(block: suspend () -> AuthResult): AuthResult =
        try {
            block()
        } catch (error: Exception) {
            AuthResult.Failure(error.toFrenchMessage())
        }

    private fun Exception.toFrenchMessage(): String {
        val detail = message.orEmpty().lowercase()
        return when {
            "email not confirmed" in detail -> "Confirmez votre adresse email avant de vous connecter."
            "invalid login credentials" in detail -> "Adresse email ou mot de passe incorrect."
            "already registered" in detail || "already been registered" in detail ->
                "Un compte existe déjà avec cette adresse email."
            "network" in detail || "unable to resolve host" in detail ->
                "Connexion au service impossible. Vérifiez votre accès à Internet."
            else -> "Une erreur est survenue avec le service de compte. Réessayez."
        }
    }
}

private fun UserInfo.toAccount() = UserAccount(
    id = id,
    email = email.orEmpty(),
    displayName = userMetadata?.get("display_name")?.toString()?.trim('"')
)
