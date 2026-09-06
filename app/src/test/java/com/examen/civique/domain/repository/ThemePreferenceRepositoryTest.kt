package com.examen.civique.domain.repository

import com.examen.civique.domain.model.ThemeMode
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ThemePreferenceRepositoryTest {
    @Test
    fun `theme representation saves and restores without Android types`() {
        val repository = InMemoryThemePreferenceRepository()

        assertNull(repository.getThemeMode())
        repository.saveThemeMode(ThemeMode.DARK)
        assertEquals(ThemeMode.DARK, repository.getThemeMode())
        repository.saveThemeMode(ThemeMode.LIGHT)
        assertEquals(ThemeMode.LIGHT, repository.getThemeMode())
    }

    private class InMemoryThemePreferenceRepository : ThemePreferenceRepository {
        private var mode: ThemeMode? = null
        override fun getThemeMode(): ThemeMode? = mode
        override fun saveThemeMode(themeMode: ThemeMode) {
            mode = themeMode
        }
    }
}
