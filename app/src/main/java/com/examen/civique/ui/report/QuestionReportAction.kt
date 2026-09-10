package com.examen.civique.ui.report

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import android.widget.Toast
import com.examen.civique.domain.model.QuestionReportReason
import com.examen.civique.domain.model.QuestionReportSource
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionReportAction(
    questionId: String,
    source: QuestionReportSource,
    viewModel: QuestionReportViewModel,
    selectedAnswer: String? = null,
    correctAnswer: String? = null,
    modifier: Modifier = Modifier
) {
    var open by remember(questionId) { mutableStateOf(false) }
    var reason by remember(questionId) { mutableStateOf<QuestionReportReason?>(null) }
    var comment by remember(questionId) { mutableStateOf("") }
    var submitting by remember(questionId) { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Box(modifier) {
        IconButton(onClick = { open = true }, modifier = Modifier.sizeIn(minWidth = 48.dp, minHeight = 48.dp)) {
            Icon(Icons.Outlined.Flag, contentDescription = "Signaler un problème")
        }
    }

    if (open) ModalBottomSheet(onDismissRequest = { if (!submitting) open = false }) {
        Column(Modifier.fillMaxWidth().verticalScroll(rememberScrollState()).padding(horizontal = 24.dp).padding(bottom = 28.dp)) {
            Text("Signaler un problème", style = MaterialTheme.typography.headlineSmall)
            Text("Qu'est-ce qui vous semble incorrect ?", color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 6.dp, bottom = 14.dp))
            QuestionReportReason.entries.forEach { item ->
                Row(
                    Modifier.fillMaxWidth().clickable { reason = item }.padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(selected = reason == item, onClick = { reason = item })
                    Text(item.label(), Modifier.padding(start = 8.dp))
                }
            }
            OutlinedTextField(
                value = comment,
                onValueChange = { comment = it.take(1000) },
                label = { Text("Ajouter un commentaire (facultatif)") },
                minLines = 3,
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
            )
            Row(Modifier.fillMaxWidth().padding(top = 18.dp), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = { open = false }, enabled = !submitting) { Text("Annuler") }
                Spacer(Modifier.width(8.dp))
                Button(
                    enabled = reason != null && !submitting,
                    onClick = {
                        val selectedReason = reason ?: return@Button
                        submitting = true
                        scope.launch {
                            val stored = viewModel.submit(questionId, selectedReason, comment, source, selectedAnswer, correctAnswer)
                            submitting = false
                            if (stored) {
                                open = false
                                reason = null
                                comment = ""
                                Toast.makeText(
                                    context,
                                    "Merci, votre signalement a bien été pris en compte.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                ) { Text(if (submitting) "Enregistrement…" else "Envoyer le signalement") }
            }
        }
    }
}

private fun QuestionReportReason.label() = when (this) {
    QuestionReportReason.INCORRECT_QUESTION -> "Question incorrecte"
    QuestionReportReason.INCORRECT_CORRECT_ANSWER -> "La réponse indiquée comme correcte est incorrecte"
    QuestionReportReason.AMBIGUOUS -> "Question ou réponses ambiguës"
    QuestionReportReason.OUTDATED -> "Information obsolète"
    QuestionReportReason.TYPO -> "Erreur de texte"
    QuestionReportReason.OTHER -> "Autre"
}
