package com.examen.civique

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import com.examen.civique.navigation.AppNavigation
import com.examen.civique.ui.theme.ExamenCiviqueTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            ExamenCiviqueTheme {

                val adaptiveInfo = currentWindowAdaptiveInfoV2()
                AppNavigation(adaptiveInfo = adaptiveInfo)

            }
        }
    }
}