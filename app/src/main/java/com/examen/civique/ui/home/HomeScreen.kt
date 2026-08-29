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
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.window.core.layout.WindowWidthSizeClass

@Composable
fun HomeScreen(
    progress: Int,
    quizCount: Int,
    adaptiveInfo: WindowAdaptiveInfo,
    onStartQuiz: () -> Unit,
    onStartExam: () -> Unit = {},
    onOpenCourses: () -> Unit = {},
    onOpenErrors: () -> Unit = {},
    onOpenStats: () -> Unit = {}
) {

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

            Text(
                text = "🇫🇷 Examen Civique",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

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
                        onClick = onStartQuiz
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
                    onClick = onStartQuiz
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