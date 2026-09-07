package com.examen.civique.ui.review

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
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
fun FavoritesScreen(viewModel: ReviewViewModel, onReview: (Set<String>) -> Unit) {
    val questions by viewModel.favorites.collectAsStateWithLifecycle()
    var expanded by remember { mutableStateOf<String?>(null) }
    Column(Modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Column(Modifier.widthIn(max = 800.dp).fillMaxSize()) {
            Text("Favoris", fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))
            if (questions.isEmpty()) {
                Text("Tu n'as encore ajouté aucune question à tes favoris.")
            } else {
                Button(onClick = { onReview(questions.map { it.id }.toSet()) }, modifier = Modifier.fillMaxWidth()) {
                    Text("Réviser mes favoris")
                }
                Spacer(Modifier.height(12.dp))
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(questions, key = { it.id }) { question ->
                        Card(Modifier.fillMaxWidth().clickable { expanded = if (expanded == question.id) null else question.id }) {
                            Column(Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.Top) {
                                    Text(question.question, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                                    IconButton(onClick = { viewModel.toggleFavorite(question.id) }) {
                                        Icon(Icons.Filled.Star, "Retirer des favoris")
                                    }
                                }
                                Text(question.category.displayName(), color = MaterialTheme.colorScheme.primary)
                                if (expanded == question.id) {
                                    Spacer(Modifier.height(10.dp))
                                    Text("Bonne réponse : ${question.answers[question.correctAnswerIndex]}")
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
