package com.examen.civique.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.examen.civique.domain.model.AuthResult
import com.examen.civique.domain.model.AuthState
import com.examen.civique.domain.repository.AccountPreferenceRepository
import com.examen.civique.domain.repository.AuthRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AccountViewModel(
    private val authRepository: AuthRepository,
    private val preferences: AccountPreferenceRepository
) : ViewModel() {
    val authState: StateFlow<AuthState> = authRepository.authState

    fun shouldShowProposal() = !preferences.hasSeenAccountProposal()
    fun markProposalSeen() = preferences.markAccountProposalSeen()
    fun signOut() = viewModelScope.launch { authRepository.signOut() }

    suspend fun signUp(email: String, password: String, confirmation: String): String? {
        validateEmail(email)?.let { return it }
        validatePassword(password)?.let { return it }
        if (password != confirmation) return "Les mots de passe ne correspondent pas."
        return when (val result = authRepository.signUp(email.trim(), password)) {
            is AuthResult.Success -> null
            is AuthResult.Failure -> result.message
        }
    }

    suspend fun signIn(email: String, password: String): String? {
        validateEmail(email)?.let { return it }
        if (password.isBlank()) return "Saisissez votre mot de passe."
        return when (val result = authRepository.signIn(email.trim(), password)) {
            is AuthResult.Success -> null
            is AuthResult.Failure -> result.message
        }
    }

    companion object {
        fun validateEmail(value: String): String? = when {
            value.isBlank() -> "Saisissez votre adresse email."
            !value.trim().matches(Regex("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) -> "Saisissez une adresse email valide."
            else -> null
        }

        fun validatePassword(value: String): String? = when {
            value.isBlank() -> "Saisissez un mot de passe."
            value.length < 8 -> "Le mot de passe doit contenir au moins 8 caractères."
            else -> null
        }
    }
}

class AccountViewModelFactory(
    private val authRepository: AuthRepository,
    private val preferences: AccountPreferenceRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        AccountViewModel(authRepository, preferences) as T
}

