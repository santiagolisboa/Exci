package com.examen.civique.ui.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.window.core.layout.WindowWidthSizeClass
import com.examen.civique.ui.components.AnswerOption

@Composable
fun QuizScreen(
    viewModel: QuizViewModel = viewModel(),
    adaptiveInfo: WindowAdaptiveInfo,
    onQuizFinished: (Int) -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val favoriteIds by viewModel.favoriteIds.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.quizFinished) {

        if (uiState.quizFinished) {
            onQuizFinished(uiState.score)
        }
    }

    if (uiState.quizFinished) {
        return
    }

    val currentQuestion = uiState.currentQuestion

    if (currentQuestion == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Aucune question disponible.")
        }
        return
    }

    val isWide =
        adaptiveInfo.windowSizeClass.windowWidthSizeClass !=
                WindowWidthSizeClass.COMPACT

    val useTwoColumns =
        isWide && currentQuestion.answers.all { it.length < 40 }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier
                .widthIn(max = 800.dp)
                .padding(32.dp)
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Question ${uiState.currentQuestionIndex + 1} / ${uiState.questions.size}",
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { viewModel.toggleFavorite(currentQuestion.id) }) {
                    Icon(
                        imageVector = if (currentQuestion.id in favoriteIds) Icons.Filled.Star else Icons.Outlined.StarBorder,
                        contentDescription = if (currentQuestion.id in favoriteIds) "Retirer des favoris" else "Ajouter aux favoris"
                    )
                }
            }

            Text(
                text = "Score : ${uiState.score}",
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 8.dp)
            )

            Text(
                text = currentQuestion.question,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    top = 24.dp,
                    bottom = 24.dp
                )
            )

            if (useTwoColumns) {

                Column {

                    for (i in 0 until currentQuestion.answers.size step 2) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {

                            QuizAnswerButton(
                                answer = currentQuestion.answers[i],
                                index = i,
                                selectedAnswerIndex = uiState.selectedAnswerIndex,
                                answerValidated = uiState.answerValidated,
                                correctAnswerIndex = currentQuestion.correctAnswerIndex,
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    viewModel.selectAnswer(i)
                                }
                            )

                            if (i + 1 < currentQuestion.answers.size) {

                                QuizAnswerButton(
                                    answer = currentQuestion.answers[i + 1],
                                    index = i + 1,
                                    selectedAnswerIndex = uiState.selectedAnswerIndex,
                                    answerValidated = uiState.answerValidated,
                                    correctAnswerIndex = currentQuestion.correctAnswerIndex,
                                    modifier = Modifier.weight(1f),
                                    onClick = {
                                        viewModel.selectAnswer(i + 1)
                                    }
                                )

                            } else {

                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }

            } else {

                currentQuestion.answers.forEachIndexed { index, answer ->

                    QuizAnswerButton(
                        answer = answer,
                        index = index,
                        selectedAnswerIndex = uiState.selectedAnswerIndex,
                        answerValidated = uiState.answerValidated,
                        correctAnswerIndex = currentQuestion.correctAnswerIndex,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            viewModel.selectAnswer(index)
                        }
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            if (!uiState.answerValidated) {

            Button(
                onClick = {
                    viewModel.validateAnswer()
                },
                enabled = uiState.selectedAnswerIndex != null,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Valider")
            }

        } else {

            val isCorrect =
                uiState.selectedAnswerIndex == currentQuestion.correctAnswerIndex

            Text(
                text = if (isCorrect) {
                    "✅ Bonne réponse !"
                } else {
                    "❌ Mauvaise réponse"
                },
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = currentQuestion.explanation
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Button(
                onClick = {
                    viewModel.nextQuestion()
                },
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    if (uiState.currentQuestionIndex == uiState.questions.lastIndex) {
                        "Voir le résultat"
                    } else {
                        "Question suivante"
                    }
                )
            }
        }
    }
}
}

@Composable
fun QuizAnswerButton(
    answer: String,
    index: Int,
    selectedAnswerIndex: Int?,
    answerValidated: Boolean,
    correctAnswerIndex: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    AnswerOption(
        answer = answer,
        selected = selectedAnswerIndex == index,
        validated = answerValidated,
        correct = index == correctAnswerIndex,
        onClick = onClick,
        modifier = modifier
    )
}
