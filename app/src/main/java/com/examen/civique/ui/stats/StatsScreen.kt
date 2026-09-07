package com.examen.civique.ui.stats

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.examen.civique.domain.model.AnswerSource
import com.examen.civique.domain.model.displayName
import java.text.DateFormat
import java.util.Date

@Composable
fun StatsScreen(viewModel: StatsViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val stats = uiState.stats
    LazyColumn(
        Modifier.fillMaxSize().padding(20.dp).widthIn(max = 800.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { Text("Statistiques", fontSize = 30.sp, fontWeight = FontWeight.Bold) }

        item {
            SectionCard("Progression") {
                Text("${stats.questionsSeen} / ${stats.totalQuestions} questions vues", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                LinearProgressIndicator(
                    progress = { (stats.coverage / 100f).coerceIn(0f, 1f) },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)
                )
                Text("${stats.coverage} % de la banque parcourue · ${stats.questionsRemaining} restantes")
            }
        }

        if (stats.answered == 0) {
            item { Text("Commence un quiz pour voir ta progression ici.") }
        } else {
            item {
                SectionCard("Performance") {
                    Text("${stats.successRate} % de réussite", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text("${stats.answered} réponses · ${stats.correct} bonnes · ${stats.wrong} erreurs")
                }
            }
            item {
                SectionCard("Maîtrise") {
                    Text("${stats.mastered} maîtrisées")
                    Text("${stats.toReview} à revoir")
                    Text("${stats.neverSeen} jamais vues")
                }
            }
            item { SectionTitle("Par catégorie") }
            items(stats.byCategory, key = { it.category.name }) { category ->
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(14.dp)) {
                        Text(category.category.displayName(), fontWeight = FontWeight.Bold)
                        Text("${category.questionsSeen} / ${category.totalQuestions} vues · ${category.questionsRemaining} restantes")
                        Text("${category.successRate} % de réussite · ${category.coverage} % parcouru")
                        Text("${category.attempts} tentatives", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

            if (stats.weakCategories.isNotEmpty() || stats.mostMissedQuestionIds.isNotEmpty()) {
                item { SectionTitle("À travailler") }
                items(stats.weakCategories, key = { "weak-${it.category.name}" }) { category ->
                    Text("• ${category.category.displayName()} — ${category.successRate} % de réussite")
                }
                items(stats.mostMissedQuestionIds, key = { "missed-$it" }) { questionId ->
                    uiState.questionsById[questionId]?.let { question ->
                        Text("• ${question.question}")
                    }
                }
            }

            item { SectionTitle("Activité récente") }
            items(uiState.recentAttempts, key = { "attempt-${it.id}" }) { attempt ->
                Card(Modifier.fillMaxWidth()) {
                    Row(
                        Modifier.fillMaxWidth().padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(Modifier.weight(1f)) {
                            Text(sourceLabel(attempt.source), fontWeight = FontWeight.Bold)
                            Text(uiState.questionsById[attempt.questionId]?.question ?: attempt.questionId, maxLines = 2)
                            Text(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(Date(attempt.answeredAt)), style = MaterialTheme.typography.bodySmall)
                        }
                        Text(if (attempt.isCorrect) "✓" else "✗", color = if (attempt.isCorrect) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error, fontSize = 22.sp)
                    }
                }
            }

            if (uiState.results.isNotEmpty()) {
                item { Text("${stats.quizCount} quiz terminés · ${stats.examCount} simulations terminées", style = MaterialTheme.typography.bodySmall) }
            }
        }
    }
}

@Composable
private fun SectionCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            content()
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(text, fontSize = 22.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
}

private fun sourceLabel(source: AnswerSource) = when (source) {
    AnswerSource.QUIZ -> "Quiz"
    AnswerSource.EXAM -> "Simulation"
    AnswerSource.ERROR_REVIEW -> "Révision erreurs"
    AnswerSource.FAVORITES -> "Favoris"
}
