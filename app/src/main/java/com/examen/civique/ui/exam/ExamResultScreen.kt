package com.examen.civique.ui.exam

import com.examen.civique.domain.model.ExamFinishReason
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
fun ExamResultScreen(
    score: Int,
    totalQuestions: Int,
    wrongAnswersCount: Int,
    unansweredQuestionsCount: Int,
    finishReason: ExamFinishReason?,
    onReviewErrors: () -> Unit,
    onRestartExam: () -> Unit,
    onGoHome: () -> Unit
) {

    val percentage =
        if (totalQuestions > 0) {
            (score * 100) / totalQuestions
        } else {
            0
        }

    val passed = percentage >= 80

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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

        Spacer(
            modifier = Modifier.height(64.dp)
        )

        Text(
            text = when (finishReason) {

                ExamFinishReason.TIME_UP ->
                    "⏱ Temps écoulé"

                ExamFinishReason.LEFT_APP ->
                    "⚠️ Examen interrompu"

                ExamFinishReason.COMPLETED ->
                    "Simulation terminée"

                null ->
                    "Simulation terminée"
            },
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text =
                if (passed) {
                    "Examen réussi 🎉"
                } else {
                    "Examen non réussi"
                },
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = "$score / $totalQuestions",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "$percentage %",
            fontSize = 24.sp
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = "✅ $score bonnes réponses",
            fontSize = 18.sp
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "❌ $wrongAnswersCount mauvaises réponses",
            fontSize = 18.sp
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "➖ $unansweredQuestionsCount sans réponse",
            fontSize = 18.sp
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        when (finishReason) {

            ExamFinishReason.LEFT_APP -> {

                Text(
                    text = "Vous avez quitté l'application pendant la simulation. L'examen a donc été automatiquement terminé.",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }

            ExamFinishReason.TIME_UP -> {

                Text(
                    text = "Les 45 minutes sont écoulées. Les réponses enregistrées ont été prises en compte.",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }

            ExamFinishReason.COMPLETED -> {

                Text(
                    text =
                        if (passed) {
                            "Bravo ! Vous avez atteint le score requis pour cette simulation."
                        } else {
                            "Continuez à vous entraîner pour améliorer votre score."
                        },
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }

            null -> Unit
        }

        Spacer(
            modifier = Modifier.height(40.dp)
        )

        if (wrongAnswersCount > 0) {

            Button(
                onClick = onReviewErrors,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Revoir mes erreurs")
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )
        }

        Button(
            onClick = onRestartExam,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Recommencer l'examen")
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedButton(
            onClick = onGoHome,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Retour à l'accueil")
        }
    }
}
}
