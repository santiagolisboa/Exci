package com.examen.civique.ui.exam

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowWidthSizeClass
import com.examen.civique.ui.components.AnswerOption
import com.examen.civique.ui.report.QuestionReportAction
import com.examen.civique.ui.report.QuestionReportViewModel
import com.examen.civique.domain.model.QuestionReportSource

@Composable
fun ExamScreen(
    viewModel: ExamViewModel,
    reportViewModel: QuestionReportViewModel,
    adaptiveInfo: WindowAdaptiveInfo,
    onExamFinished: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val favoriteIds by viewModel.favoriteIds.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.startExam()
    }

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

        ExamQuestionNavigator(
            questionIds = uiState.questions.map { it.id },
            currentQuestionIndex = uiState.currentQuestionIndex,
            answeredQuestionIds = uiState.selectedAnswers.keys,
            onQuestionSelected = viewModel::goToQuestion
        )

        Spacer(
            modifier = Modifier.height(28.dp)
        )

        Row(verticalAlignment = Alignment.Top) {
            Text(
                text = currentQuestion.question,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { viewModel.toggleFavorite(currentQuestion.id) }) {
                Icon(
                    imageVector = if (currentQuestion.id in favoriteIds) Icons.Filled.Star else Icons.Outlined.StarBorder,
                    contentDescription = if (currentQuestion.id in favoriteIds) "Retirer des favoris" else "Ajouter aux favoris"
                )
            }
            QuestionReportAction(
                questionId = currentQuestion.id,
                source = QuestionReportSource.EXAM,
                viewModel = reportViewModel,
                selectedAnswer = currentQuestion.answers.getOrNull(selectedAnswerIndex ?: -1),
                correctAnswer = currentQuestion.answers.getOrNull(currentQuestion.correctAnswerIndex)
            )
        }

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

    AnswerOption(
        answer = answer,
        selected = selectedAnswerIndex == index,
        onClick = onClick,
        modifier = modifier
    )
}

@Composable
private fun ExamQuestionNavigator(
    questionIds: List<String>,
    currentQuestionIndex: Int,
    answeredQuestionIds: Set<String>,
    onQuestionSelected: (Int) -> Unit
) {
    val listState = rememberLazyListState()

    LaunchedEffect(currentQuestionIndex) {
        if (currentQuestionIndex in questionIds.indices) {
            listState.animateScrollToItem(currentQuestionIndex)
        }
    }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("exam_question_navigator"),
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(questionIds) { index, questionId ->
            val isCurrent = index == currentQuestionIndex
            val answered = questionId in answeredQuestionIds
            val containerColor = when {
                isCurrent -> MaterialTheme.colorScheme.primary
                answered -> MaterialTheme.colorScheme.secondaryContainer
                else -> MaterialTheme.colorScheme.surface
            }
            val contentColor = when {
                isCurrent -> MaterialTheme.colorScheme.onPrimary
                answered -> MaterialTheme.colorScheme.onSecondaryContainer
                else -> MaterialTheme.colorScheme.onSurface
            }
            val stateLabel = when {
                isCurrent -> "Question actuelle"
                answered -> "Répondue"
                else -> "Non répondue"
            }

            Button(
                onClick = { onQuestionSelected(index) },
                modifier = Modifier
                    .size(42.dp)
                    .semantics {
                        contentDescription = "Question ${index + 1}"
                        stateDescription = stateLabel
                    },
                contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = containerColor,
                    contentColor = contentColor
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Text("${index + 1}")
            }
        }
    }
}
