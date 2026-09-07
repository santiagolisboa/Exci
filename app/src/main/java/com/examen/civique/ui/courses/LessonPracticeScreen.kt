package com.examen.civique.ui.courses

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.examen.civique.domain.model.QuizState
import com.examen.civique.ui.components.AnswerOption

@Composable
fun LessonPracticeScreen(
    state: QuizState,
    onSelectAnswer: (Int) -> Unit,
    onValidateAnswer: () -> Unit,
    onNextQuestion: () -> Unit,
    onClose: () -> Unit
) {
    BackHandler(onBack = onClose)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
            .testTag("lesson_practice")
    ) {
        Text("Entraînement thématique", fontSize = 26.sp, fontWeight = FontWeight.Bold)

        if (state.questions.isEmpty()) {
            Text("Aucune question n’est disponible pour cette leçon.", modifier = Modifier.padding(top = 24.dp))
            OutlinedButton(onClick = onClose, modifier = Modifier.fillMaxWidth().padding(top = 24.dp)) {
                Text("Retour à la leçon")
            }
            return@Column
        }

        if (state.quizFinished) {
            Text("Entraînement terminé", fontSize = 22.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 28.dp))
            Text("Score : ${state.score} / ${state.questions.size}", fontSize = 20.sp, modifier = Modifier.padding(top = 12.dp))
            Button(onClick = onClose, modifier = Modifier.fillMaxWidth().padding(top = 24.dp)) {
                Text("Retour à la leçon")
            }
            return@Column
        }

        val question = state.currentQuestion ?: return@Column
        Text(
            "Question ${state.currentQuestionIndex + 1} / ${state.questions.size}",
            modifier = Modifier.padding(top = 18.dp)
        )
        Text(question.question, fontSize = 22.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 24.dp))

        question.answers.forEachIndexed { index, answer ->
            AnswerOption(
                answer = answer,
                selected = state.selectedAnswerIndex == index,
                validated = state.answerValidated,
                correct = question.correctAnswerIndex == index,
                onClick = { onSelectAnswer(index) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(Modifier.height(20.dp))
        if (!state.answerValidated) {
            Button(
                onClick = onValidateAnswer,
                enabled = state.selectedAnswerIndex != null,
                modifier = Modifier.fillMaxWidth()
            ) { Text("Valider") }
        } else {
            val correct = state.selectedAnswerIndex == question.correctAnswerIndex
            Text(
                if (correct) "Bonne réponse" else "Réponse incorrecte",
                color = if (correct) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                fontWeight = FontWeight.Bold
            )
            Text(question.explanation, modifier = Modifier.padding(top = 10.dp))
            Button(onClick = onNextQuestion, modifier = Modifier.fillMaxWidth().padding(top = 20.dp)) {
                Text(if (state.currentQuestionIndex == state.questions.lastIndex) "Voir le résultat" else "Question suivante")
            }
        }
        OutlinedButton(onClick = onClose, modifier = Modifier.fillMaxWidth().padding(top = 12.dp)) {
            Text("Quitter l’entraînement")
        }
    }
}
