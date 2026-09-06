package com.examen.civique.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.examen.civique.domain.model.ThemeMode
import com.examen.civique.domain.repository.ThemePreferenceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ThemeViewModel(
    private val repository: ThemePreferenceRepository
) : ViewModel() {
    private val _themeMode = MutableStateFlow(repository.getThemeMode())
    val themeMode: StateFlow<ThemeMode?> = _themeMode.asStateFlow()

    fun selectTheme(themeMode: ThemeMode) {
        repository.saveThemeMode(themeMode)
        _themeMode.value = themeMode
    }
}

class ThemeViewModelFactory(
    private val repository: ThemePreferenceRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            return ThemeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
