package com.examen.civique.ui.review

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.examen.civique.domain.model.displayName

@Composable
fun ErrorReviewScreen(viewModel: ReviewViewModel, onReview: (Set<String>) -> Unit) {
    val errors by viewModel.errors.collectAsStateWithLifecycle()
    val favorites by viewModel.favoriteIds.collectAsStateWithLifecycle()
    var expanded by remember { mutableStateOf<String?>(null) }

    Column(Modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Column(Modifier.widthIn(max = 800.dp).fillMaxSize()) {
            Text("Mes erreurs", fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Text("${errors.size} question(s) à revoir", modifier = Modifier.padding(vertical = 8.dp))
            if (errors.isEmpty()) {
                Text("Aucune erreur à réviser pour le moment.")
            } else {
                Button(onClick = { onReview(errors.map { it.question.id }.toSet()) }, modifier = Modifier.fillMaxWidth()) {
                    Text("Réviser mes erreurs")
                }
                Spacer(Modifier.height(12.dp))
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(errors, key = { it.question.id }) { item ->
                        val question = item.question
                        Card(Modifier.fillMaxWidth().clickable { expanded = if (expanded == question.id) null else question.id }) {
                            Column(Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.Top) {
                                    Text(question.question, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                                    IconButton(onClick = { viewModel.toggleFavorite(question.id) }) {
                                        Icon(
                                            if (question.id in favorites) Icons.Filled.Star else Icons.Outlined.StarBorder,
                                            if (question.id in favorites) "Retirer des favoris" else "Ajouter aux favoris"
                                        )
                                    }
                                }
                                Text(question.category.displayName(), color = MaterialTheme.colorScheme.primary)
                                Text(if (item.error.errorCount == 1) "1 erreur" else "${item.error.errorCount} erreurs")
                                if (expanded == question.id) {
                                    item.error.lastWrongAnswer?.let { Text("Ta dernière réponse : $it", modifier = Modifier.padding(top = 10.dp)) }
                                    Text("Bonne réponse : ${question.answers[question.correctAnswerIndex]}", modifier = Modifier.padding(top = 6.dp))
                                    Text(question.explanation, modifier = Modifier.padding(top = 8.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
