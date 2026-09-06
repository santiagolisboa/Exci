package com.examen.civique

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.performClick
import com.examen.civique.navigation.AppNavigation
import com.examen.civique.ui.theme.ExamenCiviqueTheme
import com.examen.civique.ui.components.AnswerOption
import org.junit.Rule
import org.junit.Test

class AdaptiveTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @OptIn(ExperimentalMaterial3AdaptiveApi::class)
    @Test
    fun testNavigationElementsVisible() {
        composeTestRule.setContent {
            ExamenCiviqueTheme {
                val adaptiveInfo = currentWindowAdaptiveInfoV2()
                AppNavigation(adaptiveInfo = adaptiveInfo)
            }
        }

        // Home screen title should be visible
        composeTestRule.onNodeWithText("🇫🇷 Examen Civique").assertIsDisplayed()
        
        // Navigation items should be visible
        composeTestRule.onNodeWithText("Accueil").assertIsDisplayed()
        composeTestRule.onNodeWithText("Cours").assertIsDisplayed()
    }

    @OptIn(ExperimentalMaterial3AdaptiveApi::class)
    @Test
    fun settingsOpensAppearanceDialog() {
        composeTestRule.setContent {
            ExamenCiviqueTheme {
                val adaptiveInfo = currentWindowAdaptiveInfoV2()
                AppNavigation(adaptiveInfo = adaptiveInfo)
            }
        }

        composeTestRule.onNodeWithContentDescription("Paramètres").performClick()
        composeTestRule.onNodeWithText("Apparence").assertIsDisplayed()
        composeTestRule.onNodeWithText("Mode clair").assertIsDisplayed()
        composeTestRule.onNodeWithText("Mode sombre").assertIsDisplayed()
    }

    @Test
    fun selectedAnswerExposesStableSelectionSemantics() {
        composeTestRule.setContent {
            ExamenCiviqueTheme {
                AnswerOption(
                    answer = "Réponse sélectionnée",
                    selected = true,
                    onClick = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Réponse sélectionnée").assertIsSelected()
    }
}
