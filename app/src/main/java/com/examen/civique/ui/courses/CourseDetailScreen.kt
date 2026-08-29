package com.examen.civique.ui.courses

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.examen.civique.domain.model.Course

@Composable
fun CourseDetailScreen(
    course: Course,
    onCourseFinished: () -> Unit
) {

    var currentLessonIndex by remember(course.id) {
        mutableIntStateOf(0)
    }

    if (course.lessons.isEmpty()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {

            Text(
                text = course.title,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(
                text = "Aucune leçon disponible pour le moment."
            )
        }

        return
    }

    val currentLesson =
        course.lessons[currentLessonIndex]

    val progress =
        (currentLessonIndex + 1).toFloat() /
                course.lessons.size.toFloat()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {

        Text(
            text = course.icon,
            fontSize = 36.sp
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = course.title,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = "Leçon ${currentLessonIndex + 1} / ${course.lessons.size}",
            fontSize = 14.sp
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        Text(
            text = currentLesson.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = currentLesson.content,
            fontSize = 17.sp,
            lineHeight = 26.sp
        )

        Spacer(
            modifier = Modifier.height(40.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            if (currentLessonIndex > 0) {

                OutlinedButton(
                    onClick = {
                        currentLessonIndex--
                    },
                    modifier = Modifier.weight(1f)
                ) {

                    Text("← Précédente")
                }
            }

            if (
                currentLessonIndex <
                course.lessons.lastIndex
            ) {

                Button(
                    onClick = {
                        currentLessonIndex++
                    },
                    modifier = Modifier.weight(1f)
                ) {

                    Text("Suivante →")
                }

            } else {

                Button(
                    onClick = onCourseFinished,
                    modifier = Modifier.weight(1f)
                ) {

                    Text("Terminer ✓")
                }
            }
        }
    }
}