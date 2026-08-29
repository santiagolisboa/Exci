package com.examen.civique.ui.exam

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowWidthSizeClass

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExamScreen(
    viewModel: ExamViewModel,
    adaptiveInfo: WindowAdaptiveInfo,
    onExamFinished: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var showFinishDialog by remember {
        mutableStateOf(false)
    }

    val unansweredQuestions =
        uiState.questions.size - uiState.selectedAnswers.size

    if (showFinishDialog) {

        AlertDialog(
            onDismissRequest = {
                showFinishDialog = false
            },

            title = {
                Text("Terminer l'examen ?")
            },

            text = {

                if (unansweredQuestions > 0) {

                    Text(
                        "Il reste $unansweredQuestions question(s) sans réponse. " +
                                "Voulez-vous vraiment terminer l'examen ?"
                    )

                } else {

                    Text(
                        "Vous avez répondu à toutes les questions. " +
                                "Voulez-vous terminer l'examen ?"
                    )
                }
            },

            confirmButton = {

                TextButton(
                    onClick = {

                        showFinishDialog = false

                        viewModel.finishExam()
                    }
                ) {
                    Text("Terminer")
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {
                        showFinishDialog = false
                    }
                ) {
                    Text("Continuer")
                }
            }
        )
    }

    ExamLifecycleObserver(
        examStarted = true,
        examFinished = uiState.examFinished,
        onAppLeft = {
            viewModel.finishBecauseAppLeft()
        }
    )

    LaunchedEffect(uiState.examFinished) {
        if (uiState.examFinished) {
            onExamFinished()
        }
    }

    if (uiState.examFinished) {
        return
    }

    if (uiState.questions.isEmpty()) {
        Text("Aucune question disponible.")
        return
    }

    val currentQuestion = viewModel.currentQuestion

    val selectedAnswerIndex =
        uiState.selectedAnswers[currentQuestion.id]

    val minutes = uiState.remainingSeconds / 60
    val seconds = uiState.remainingSeconds % 60

    val formattedTime =
        "%02d:%02d".format(minutes, seconds)

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
                .padding(24.dp)
        ) {

            Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = "Simulation",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "⏱ $formattedTime",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        LinearProgressIndicator(
            progress = {
                (uiState.currentQuestionIndex + 1).toFloat() /
                        uiState.questions.size.toFloat()
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = "Question ${uiState.currentQuestionIndex + 1} / ${uiState.questions.size}"
            )

            Text(
                text = "${uiState.selectedAnswers.size} répondues"
            )
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "Questions",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            uiState.questions.forEachIndexed { index, question ->

                val answered =
                    uiState.selectedAnswers.containsKey(question.id)

                val isCurrent =
                    index == uiState.currentQuestionIndex

                OutlinedButton(
                    onClick = {
                        viewModel.goToQuestion(index)
                    }
                ) {

                    Text(
                        text = when {
                            isCurrent -> "➤ ${index + 1}"
                            answered -> "✓ ${index + 1}"
                            else -> "${index + 1}"
                        }
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(28.dp)
        )

        Text(
            text = currentQuestion.question,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        if (useTwoColumns) {

            Column {

                for (i in 0 until currentQuestion.answers.size step 2) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        ExamAnswerButton(
                            answer = currentQuestion.answers[i],
                            index = i,
                            selectedAnswerIndex = selectedAnswerIndex,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                viewModel.selectAnswer(i)
                            }
                        )

                        if (i + 1 < currentQuestion.answers.size) {

                            ExamAnswerButton(
                                answer = currentQuestion.answers[i + 1],
                                index = i + 1,
                                selectedAnswerIndex = selectedAnswerIndex,
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

                ExamAnswerButton(
                    answer = answer,
                    index = index,
                    selectedAnswerIndex = selectedAnswerIndex,
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

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            OutlinedButton(
                onClick = {
                    viewModel.previousQuestion()
                },
                enabled = uiState.currentQuestionIndex > 0
            ) {
                Text("Précédente")
            }

            Spacer(
                modifier = Modifier.weight(1f)
            )

            if (
                uiState.currentQuestionIndex <
                uiState.questions.lastIndex
            ) {

                Button(
                    onClick = {
                        viewModel.nextQuestion()
                    }
                ) {
                    Text("Suivante")
                }

            } else {

                Button(
                    onClick = {
                        showFinishDialog = true
                    }
                ) {
                    Text("Terminer")
                }
            }
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        OutlinedButton(
            onClick = {
                showFinishDialog = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Terminer l'examen")
        }
    }
}
}

@Composable
fun ExamAnswerButton(
    answer: String,
    index: Int,
    selectedAnswerIndex: Int?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    OutlinedButton(
        onClick = onClick,
        modifier = modifier.padding(vertical = 6.dp)
    ) {

        Text(
            text = if (selectedAnswerIndex == index) {
                "✓ $answer"
            } else {
                answer
            }
        )
    }
}
