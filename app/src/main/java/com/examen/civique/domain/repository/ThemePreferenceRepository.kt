package com.examen.civique.domain.repository

import com.examen.civique.domain.model.ThemeMode

interface ThemePreferenceRepository {
    fun getThemeMode(): ThemeMode?
    fun saveThemeMode(themeMode: ThemeMode)
}
