package com.examen.civique.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import com.examen.civique.domain.model.LearningProgress
import com.examen.civique.domain.model.ThemeMode

@Composable
fun HomeScreen(
    progress: LearningProgress,
    successRate: Int,
    weakestCategory: String?,
    errorCount: Int,
    favoriteCount: Int,
    adaptiveInfo: WindowAdaptiveInfo,
    hasSavedQuiz: Boolean = false,
    savedQuestionNumber: Int? = null,
    themeMode: ThemeMode? = null,
    onStartNewQuiz: () -> Unit,
    onContinueQuiz: () -> Unit = {},
    onThemeSelected: (ThemeMode) -> Unit = {},
    onStartExam: () -> Unit = {},
    onOpenCourses: () -> Unit = {},
    onOpenErrors: () -> Unit = {},
    onOpenStats: () -> Unit = {},
    onOpenFavorites: () -> Unit = {},
    onOpenSettings: () -> Unit = {}
) {
    val compact = adaptiveInfo.windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT

    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            Modifier.widthIn(max = 760.dp).fillMaxWidth()
                .padding(horizontal = if (compact) 20.dp else 32.dp, vertical = 24.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("Examen Civique", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.SemiBold)
                    Text("Continuez votre préparation", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                IconButton(onClick = onOpenSettings) {
                    Icon(Icons.Default.Settings, contentDescription = "Paramètres")
                }
            }

            Spacer(Modifier.height(32.dp))
            SectionLabel("Votre progression")
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom) {
                Text(
                    "${progress.seenQuestions} / ${progress.totalQuestions}",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(" questions travaillées", color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.weight(1f))
                Text("${progress.coverageRate} %", fontWeight = FontWeight.SemiBold)
            }
            LinearProgressIndicator(
                progress = { (progress.coverageRate / 100f).coerceIn(0f, 1f) },
                modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp).testTag("home_progress")
                    .semantics { contentDescription = "Progression ${progress.coverageRate} pour cent" }
            )
            Text(
                "${progress.masteredQuestions} maîtrisées · ${progress.reviewQuestions} à revoir · ${progress.remainingQuestions} restantes",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(32.dp))
            SectionLabel("Continuer")
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.School, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(Modifier.width(12.dp))
                        Column(Modifier.weight(1f)) {
                            Text("Entraînement", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
                            Text(
                                if (hasSavedQuiz && savedQuestionNumber != null) "Question $savedQuestionNumber"
                                else "Travaillez à votre rythme",
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                    Spacer(Modifier.height(18.dp))
                    Button(
                        onClick = if (hasSavedQuiz) onContinueQuiz else onStartNewQuiz,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text(if (hasSavedQuiz) "Continuer" else "Commencer")
                    }
                    if (hasSavedQuiz) {
                        TextButton(onClick = onStartNewQuiz, modifier = Modifier.align(Alignment.End)) {
                            Text("Recommencer")
                        }
                    }
                }
            }

            Spacer(Modifier.height(32.dp))
            SectionLabel("Se préparer")
            NavigationRow(Icons.Default.MenuBook, "Cours", onClick = onOpenCourses)
            HorizontalDivider()
            NavigationRow(Icons.Default.Assignment, "Simulation d’examen", "40 questions · 45 min", onClick = onStartExam)
            HorizontalDivider()
            NavigationRow(Icons.Default.ErrorOutline, "Mes erreurs", count = errorCount.takeIf { it > 0 }, onClick = onOpenErrors)
            HorizontalDivider()
            NavigationRow(Icons.Outlined.StarBorder, "Favoris", count = favoriteCount.takeIf { it > 0 }, onClick = onOpenFavorites)

            Spacer(Modifier.height(32.dp))
            SectionLabel("Votre niveau")
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("$successRate % de réussite", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
                    weakestCategory?.let { Text("À travailler : $it", color = MaterialTheme.colorScheme.onSurfaceVariant) }
                }
                TextButton(onClick = onOpenStats) { Text("Voir les statistiques") }
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(bottom = 10.dp)
    )
}

@Composable
private fun NavigationRow(
    icon: ImageVector,
    title: String,
    subtitle: String? = null,
    count: Int? = null,
    onClick: () -> Unit
) {
    Row(
        Modifier.fillMaxWidth().clickable(onClick = onClick).padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.width(16.dp))
        Column(Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            subtitle?.let { Text(it, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant) }
        }
        count?.let { Text(it.toString(), fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(horizontal = 8.dp)) }
        Icon(Icons.Default.ChevronRight, contentDescription = "Ouvrir $title", tint = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
