package com.examen.civique.ui.exam

import com.examen.civique.domain.model.ExamAnswerReview

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
import com.examen.civique.domain.model.QuestionReportSource
import com.examen.civique.ui.report.QuestionReportAction
import com.examen.civique.ui.report.QuestionReportViewModel

@Composable
fun ExamReviewScreen(
    errors: List<ExamAnswerReview>,
    reportViewModel: QuestionReportViewModel,
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
            text = "Erreurs de l'examen",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        if (errors.isEmpty()) {

            Text(
                text = "🎉 Aucune erreur !",
                fontSize = 20.sp
            )

        } else {

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                items(errors) { review ->

                    val question = review.question

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

                            QuestionReportAction(
                                question.id,
                                QuestionReportSource.EXAM_REVIEW,
                                reportViewModel,
                                selectedAnswer = review.selectedAnswerIndex?.let(question.answers::getOrNull),
                                correctAnswer = question.answers.getOrNull(question.correctAnswerIndex)
                            )

                            Spacer(
                                modifier = Modifier.height(16.dp)
                            )

                            Text("❌ Ta réponse")

                            Text(
                                text = review.selectedAnswerIndex
                                    ?.let { index ->
                                        question.answers[index]
                                    }
                                    ?: "Aucune réponse",
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(
                                modifier = Modifier.height(12.dp)
                            )

                            Text("✅ Bonne réponse")

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
