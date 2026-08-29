package com.examen.civique.ui.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ResultScreen(
    score: Int,
    totalQuestions: Int,
    wrongAnswersCount: Int,
    onRestartQuiz: () -> Unit,
    onReviewErrors: () -> Unit,
    onGoHome: () -> Unit
) {

    val percentage =
        if (totalQuestions > 0) {
            (score * 100) / totalQuestions
        } else {
            0
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier
                .widthIn(max = 600.dp)
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

        Text(
            text = "Quiz terminé 🎉",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = "$score / $totalQuestions",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "$percentage %",
            fontSize = 24.sp
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = when {
                percentage >= 80 ->
                    "Excellent résultat ! 🇫🇷"

                percentage >= 60 ->
                    "Bien joué ! Continue à réviser."

                else ->
                    "Encore un peu de révision et ça va venir !"
            },
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        Button(
            onClick = onRestartQuiz,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Recommencer le quiz")
        }

        if (wrongAnswersCount > 0) {

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            OutlinedButton(
                onClick = onReviewErrors,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Revoir mes erreurs ($wrongAnswersCount)"
                )
            }
        }

        OutlinedButton(
            onClick = onGoHome,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Retour à l'accueil")
        }
    }
}
}
