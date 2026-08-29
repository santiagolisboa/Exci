package com.examen.civique.ui.review

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.examen.civique.domain.model.QuizAnswer

@Composable
fun ErrorReviewScreen(
    wrongAnswers: List<QuizAnswer>,
    onGoHome: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier
                .widthIn(max = 800.dp)
                .fillMaxSize()
                .padding(24.dp)
        ) {

        Text(
            text = "Mes erreurs",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "${wrongAnswers.size} question(s) à revoir",
            fontSize = 16.sp,
            modifier = Modifier.padding(
                top = 8.dp,
                bottom = 24.dp
            )
        )

        if (wrongAnswers.isEmpty()) {

            Text(
                text = "🎉 Aucune erreur ! Bravo.",
                fontSize = 20.sp
            )

        } else {

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                items(wrongAnswers) { quizAnswer ->

                    val question = quizAnswer.question

                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {

                            Text(
                                text = question.question,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(
                                modifier = Modifier.height(16.dp)
                            )

                            Text(
                                text = "❌ Ta réponse :"
                            )

                            Text(
                                text = question.answers[
                                    quizAnswer.selectedAnswerIndex
                                ],
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(
                                modifier = Modifier.height(12.dp)
                            )

                            Text(
                                text = "✅ Bonne réponse :"
                            )

                            Text(
                                text = question.answers[
                                    question.correctAnswerIndex
                                ],
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(
                                modifier = Modifier.height(16.dp)
                            )

                            Text(
                                text = question.explanation
                            )
                        }
                    }
                }
            }
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Button(
            onClick = onGoHome,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Retour à l'accueil")
        }
    }
}
}
