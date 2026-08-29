package com.examen.civique.ui.stats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun StatsScreen(
    viewModel: StatsViewModel
) {

    val uiState by
    viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LazyColumn(
            modifier = Modifier
                .widthIn(max = 800.dp)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {

                Text(
                    text = "Statistiques",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            item {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    StatCard(
                        title = "Quiz réalisés",
                        value = "${uiState.quizCount}",
                        modifier = Modifier.weight(1f)
                    )

                    StatCard(
                        title = "Moyenne",
                        value = "${uiState.averageScore} %",
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {

                StatCard(
                    title = "Meilleur score",
                    value = "${uiState.bestScore} %",
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "Historique",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            if (uiState.results.isEmpty()) {

                item {

                    Text(
                        text = "Tu n'as pas encore terminé de quiz."
                    )
                }

            } else {

                items(uiState.results) { result ->

                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Text(
                                text = "${result.score} / ${result.totalQuestions}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = "${result.percentage} %"
                            )

                            Text(
                                text = "✅ ${result.correctAnswers}   ❌ ${result.wrongAnswers}"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StatCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Text(
                text = value,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = title
            )
        }
    }
}
