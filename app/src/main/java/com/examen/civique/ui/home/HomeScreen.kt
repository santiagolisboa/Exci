package com.examen.civique.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.window.core.layout.WindowWidthSizeClass
import com.examen.civique.domain.model.ThemeMode

@Composable
fun HomeScreen(
    progress: Int,
    quizCount: Int,
    adaptiveInfo: WindowAdaptiveInfo,
    hasSavedQuiz: Boolean = false,
    themeMode: ThemeMode? = null,
    onStartNewQuiz: () -> Unit,
    onContinueQuiz: () -> Unit = {},
    onThemeSelected: (ThemeMode) -> Unit = {},
    onStartExam: () -> Unit = {},
    onOpenCourses: () -> Unit = {},
    onOpenErrors: () -> Unit = {},
    onOpenStats: () -> Unit = {}
) {
    var showQuizChoice by remember { mutableStateOf(false) }
    var showAppearance by remember { mutableStateOf(false) }
    val effectiveTheme = themeMode ?: if (isSystemInDarkTheme()) {
        ThemeMode.DARK
    } else {
        ThemeMode.LIGHT
    }

    if (showQuizChoice) {
        AlertDialog(
            onDismissRequest = { showQuizChoice = false },
            title = { Text("Quiz en cours") },
            text = { Text("Voulez-vous reprendre votre session ou recommencer ?") },
            confirmButton = {
                TextButton(onClick = {
                    showQuizChoice = false
                    onContinueQuiz()
                }) { Text("Continuer le quiz") }
            },
            dismissButton = {
                TextButton(onClick = {
                    showQuizChoice = false
                    onStartNewQuiz()
                }) { Text("Commencer un nouveau quiz") }
            }
        )
    }

    if (showAppearance) {
        AlertDialog(
            onDismissRequest = { showAppearance = false },
            title = { Text("Apparence") },
            text = {
                Column {
                    ThemeChoice("Mode clair", ThemeMode.LIGHT, effectiveTheme, onThemeSelected)
                    ThemeChoice("Mode sombre", ThemeMode.DARK, effectiveTheme, onThemeSelected)
                }
            },
            confirmButton = {
                TextButton(onClick = { showAppearance = false }) { Text("Fermer") }
            }
        )
    }

    val isWide =
        adaptiveInfo.windowSizeClass.windowWidthSizeClass !=
                WindowWidthSizeClass.COMPACT

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier
                .widthIn(max = 800.dp)
                .padding(24.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "🇫🇷 Examen Civique",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { showAppearance = true }) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Paramètres"
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Prêt à réviser ?",
                fontSize = 18.sp
            )

            Spacer(
                modifier = Modifier.height(32.dp)
            )

            if (isWide) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    HomeLargeCard(
                        title = "📝 Quiz rapide",
                        description = "Entraîne-toi avec quelques questions",
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if (hasSavedQuiz) showQuizChoice = true else onStartNewQuiz()
                        }
                    )

                    HomeLargeCard(
                        title = "🎓 Simulation d'examen",
                        description = "Teste-toi dans des conditions proches de l'examen",
                        modifier = Modifier.weight(1f),
                        onClick = onStartExam
                    )
                }

            } else {

                HomeLargeCard(
                    title = "📝 Quiz rapide",
                    description = "Entraîne-toi avec quelques questions",
                    onClick = {
                        if (hasSavedQuiz) showQuizChoice = true else onStartNewQuiz()
                    }
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                HomeLargeCard(
                    title = "🎓 Simulation d'examen",
                    description = "Teste-toi dans des conditions proches de l'examen",
                    onClick = onStartExam
                )
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                HomeSmallCard(
                    title = "📚",
                    label = "Cours",
                    modifier = Modifier.weight(1f),
                    onClick = onOpenCourses
                )

                HomeSmallCard(
                    title = "❌",
                    label = "Mes erreurs",
                    modifier = Modifier.weight(1f),
                    onClick = onOpenErrors
                )
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                HomeSmallCard(
                    title = "📊",
                    label = "Statistiques",
                    modifier = Modifier.weight(1f),
                    onClick = onOpenStats
                )

                HomeSmallCard(
                    title = "⭐",
                    label = "Favoris",
                    modifier = Modifier.weight(1f),
                    onClick = {}
                )
            }

            Spacer(
                modifier = Modifier.height(32.dp)
            )

            Text(
                text = "Ta progression",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            if (quizCount == 0) {

            Text(
                text = "Aucun quiz terminé pour le moment.",
                fontSize = 16.sp
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            LinearProgressIndicator(
                progress = { 0f },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Fais ton premier quiz pour commencer à suivre ta progression.",
                fontSize = 14.sp
            )

        } else {

            val progressValue =
                (progress / 100f).coerceIn(0f, 1f)

            LinearProgressIndicator(
                progress = { progressValue },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "$progress % de moyenne sur $quizCount quiz",
                fontSize = 14.sp
            )
        }
    }
}
}

@Composable
private fun ThemeChoice(
    label: String,
    mode: ThemeMode,
    selectedMode: ThemeMode,
    onSelected: (ThemeMode) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelected(mode) }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = mode == selectedMode,
            onClick = { onSelected(mode) }
        )
        Text(text = label, modifier = Modifier.padding(start = 8.dp))
    }
}

@Composable
fun HomeLargeCard(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(24.dp)
        ) {

            Text(
                text = title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = description,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun HomeSmallCard(
    title: String,
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Card(
        onClick = onClick,
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                text = title,
                fontSize = 32.sp
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = label,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
