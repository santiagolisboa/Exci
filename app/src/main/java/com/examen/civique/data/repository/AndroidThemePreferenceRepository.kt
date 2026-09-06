package com.examen.civique.data.repository

import android.content.Context
import androidx.core.content.edit
import com.examen.civique.domain.model.ThemeMode
import com.examen.civique.domain.repository.ThemePreferenceRepository

class AndroidThemePreferenceRepository(context: Context) : ThemePreferenceRepository {
    private val preferences = context.applicationContext.getSharedPreferences(
        PREFERENCES_NAME,
        Context.MODE_PRIVATE
    )

    override fun getThemeMode(): ThemeMode? {
        val saved = preferences.getString(THEME_KEY, null) ?: return null
        return ThemeMode.entries.firstOrNull { it.name == saved }
    }

    override fun saveThemeMode(themeMode: ThemeMode) {
        preferences.edit { putString(THEME_KEY, themeMode.name) }
    }

    private companion object {
        const val PREFERENCES_NAME = "appearance_preferences"
        const val THEME_KEY = "theme_mode"
    }
}
