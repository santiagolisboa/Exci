package com.examen.civique.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun AnswerOption(
    answer: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    validated: Boolean = false,
    correct: Boolean = false,
    onClick: () -> Unit
) {
    val darkTheme = MaterialTheme.colorScheme.background.luminance() < 0.5f
    val containerColor = when {
        validated && correct -> MaterialTheme.colorScheme.primary
        validated && selected -> MaterialTheme.colorScheme.error
        selected && darkTheme -> Color.White
        selected -> Color.Black
        else -> MaterialTheme.colorScheme.surface
    }
    val contentColor = when {
        validated && correct -> MaterialTheme.colorScheme.onPrimary
        validated && selected -> MaterialTheme.colorScheme.onError
        selected && darkTheme -> Color.Black
        selected -> Color.White
        else -> MaterialTheme.colorScheme.onSurface
    }

    Button(
        onClick = onClick,
        modifier = modifier
            .padding(vertical = 6.dp)
            .semantics { this.selected = selected },
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Text(answer)
    }
}
