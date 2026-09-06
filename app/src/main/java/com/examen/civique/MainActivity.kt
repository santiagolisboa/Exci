package com.examen.civique

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.examen.civique.data.repository.AndroidThemePreferenceRepository
import com.examen.civique.navigation.AppNavigation
import com.examen.civique.ui.theme.ExamenCiviqueTheme
import com.examen.civique.ui.theme.ThemeViewModel
import com.examen.civique.ui.theme.ThemeViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(android.graphics.Color.BLACK)
        )

        setContent {
            val themeViewModel: ThemeViewModel = viewModel(
                factory = ThemeViewModelFactory(
                    AndroidThemePreferenceRepository(applicationContext)
                )
            )
            val themeMode by themeViewModel.themeMode.collectAsStateWithLifecycle()

            ExamenCiviqueTheme(themeMode = themeMode) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                ) {
                    androidx.compose.foundation.layout.Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .windowInsetsTopHeight(
                                WindowInsets.statusBars.union(WindowInsets.displayCutout)
                            )
                    )
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        val adaptiveInfo = currentWindowAdaptiveInfoV2()
                        AppNavigation(
                            adaptiveInfo = adaptiveInfo,
                            themeMode = themeMode,
                            onThemeSelected = themeViewModel::selectTheme
                        )
                    }
                }
            }
        }
    }
}
