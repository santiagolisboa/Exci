package com.examen.civique

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.assertDoesNotExist
import com.examen.civique.navigation.AppNavigation
import com.examen.civique.ui.theme.ExamenCiviqueTheme
import com.examen.civique.ui.components.AnswerOption
import com.examen.civique.data.local.courses
import com.examen.civique.ui.courses.CourseDetailScreen
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
        composeTestRule.onNodeWithText("Examen Civique").assertIsDisplayed()
        composeTestRule.onNodeWithText("🇫🇷 Examen Civique").assertDoesNotExist()
        composeTestRule.onNodeWithText("Votre progression").assertIsDisplayed()
        composeTestRule.onNodeWithText("Entraînement").assertIsDisplayed()
        composeTestRule.onNodeWithTag("home_progress").assertIsDisplayed()
        composeTestRule.onNodeWithText("Simulation d’examen").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithText("Mes erreurs").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithText("Favoris").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithText("Voir les statistiques").performScrollTo().assertIsDisplayed()
        
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

    @Test
    fun courseDetailShowsProgressContentAndPracticeAction() {
        composeTestRule.setContent {
            ExamenCiviqueTheme {
                CourseDetailScreen(
                    course = courses.first(),
                    progress = null,
                    questionCountForLesson = { 10 },
                    onLessonOpened = {},
                    onLessonCompleted = {},
                    onStartPractice = {}
                )
            }
        }

        composeTestRule.onNodeWithTag("course_detail").assertIsDisplayed()
        composeTestRule.onNodeWithText("Leçon 1 / 3").assertIsDisplayed()
        composeTestRule.onNodeWithTag("start_lesson_practice")
            .performScrollTo()
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag("complete_lesson").assertIsDisplayed()
    }
}
