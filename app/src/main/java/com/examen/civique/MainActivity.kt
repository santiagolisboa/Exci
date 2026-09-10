package com.examen.civique

import android.os.Bundle
import android.provider.Settings
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
import androidx.compose.runtime.remember
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.examen.civique.data.repository.AndroidThemePreferenceRepository
import com.examen.civique.navigation.AppNavigation
import com.examen.civique.ui.theme.ExamenCiviqueTheme
import com.examen.civique.ui.theme.ThemeViewModel
import com.examen.civique.ui.theme.ThemeViewModelFactory
import com.examen.civique.ui.splash.BrandSplashScreen
import com.examen.civique.ui.splash.BrandSplashViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(android.graphics.Color.BLACK),
            navigationBarStyle = SystemBarStyle.dark(android.graphics.Color.BLACK)
        )

        setContent {
            val themeViewModel: ThemeViewModel = viewModel(
                factory = ThemeViewModelFactory(
                    AndroidThemePreferenceRepository(applicationContext)
                )
            )
            val themeMode by themeViewModel.themeMode.collectAsStateWithLifecycle()
            val brandSplashViewModel: BrandSplashViewModel = viewModel()
            val splashFinished by brandSplashViewModel.finished.collectAsStateWithLifecycle()
            val reducedMotion = remember {
                Settings.Global.getFloat(
                    contentResolver,
                    Settings.Global.ANIMATOR_DURATION_SCALE,
                    1f
                ) == 0f
            }

            ExamenCiviqueTheme(themeMode = themeMode) {
                Crossfade(
                    targetState = splashFinished,
                    animationSpec = tween(180),
                    label = "brand_to_home",
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                ) { finished ->
                    if (!finished) {
                        BrandSplashScreen(
                            elapsedMillis = brandSplashViewModel::elapsedMillis,
                            reducedMotion = reducedMotion,
                            onFinished = brandSplashViewModel::finish
                        )
                    } else {
                        Column(
                            modifier = Modifier.fillMaxSize().background(Color.Black)
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
    }
}
