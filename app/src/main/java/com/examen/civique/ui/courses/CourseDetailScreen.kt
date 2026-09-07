package com.examen.civique.ui.courses

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.examen.civique.domain.model.Course
import com.examen.civique.domain.model.CourseProgress
import com.examen.civique.domain.model.LessonSection

@Composable
fun CourseDetailScreen(
    course: Course,
    progress: CourseProgress?,
    questionCountForLesson: (String) -> Int,
    onLessonOpened: (String) -> Unit,
    onLessonCompleted: (String) -> Unit,
    onStartPractice: (String) -> Unit
) {
    val initialIndex = remember(course.id, progress?.lastLessonId) {
        course.lessons.indexOfFirst { it.id == progress?.lastLessonId }.coerceAtLeast(0)
    }
    var currentLessonIndex by rememberSaveable(course.id, progress?.lastLessonId) {
        mutableIntStateOf(initialIndex)
    }

    if (course.lessons.isEmpty()) {
        Text("Aucune leçon disponible.", modifier = Modifier.padding(24.dp))
        return
    }

    val lesson = course.lessons[currentLessonIndex]
    val lessonCompleted = progress?.completed == true ||
            lesson.id in progress?.completedLessonIds.orEmpty()
    val uriHandler = LocalUriHandler.current

    LaunchedEffect(lesson.id) { onLessonOpened(lesson.id) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
            .testTag("course_detail")
    ) {
        Text("${course.icon} ${course.title}", fontSize = 26.sp, fontWeight = FontWeight.Bold)
        Text(course.description, modifier = Modifier.padding(top = 8.dp))

        LinearProgressIndicator(
            progress = { (currentLessonIndex + 1f) / course.lessons.size },
            modifier = Modifier.fillMaxWidth().padding(top = 20.dp)
        )
        Text(
            "Leçon ${currentLessonIndex + 1} / ${course.lessons.size}",
            modifier = Modifier.padding(top = 8.dp)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(course.lessons, key = { _, item -> item.id }) { index, item ->
                val completed = progress?.completed == true ||
                        item.id in progress?.completedLessonIds.orEmpty()
                FilterChip(
                    selected = index == currentLessonIndex,
                    onClick = { currentLessonIndex = index },
                    label = { Text("${if (completed) "✓ " else ""}${index + 1}") }
                )
            }
        }

        Text(lesson.title, fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 28.dp))
        Text(lesson.summary, fontSize = 17.sp, lineHeight = 26.sp, modifier = Modifier.padding(top = 10.dp))

        lesson.sections.forEach { section ->
            when (section) {
                is LessonSection.Paragraph -> Text(
                    section.text,
                    fontSize = 17.sp,
                    lineHeight = 26.sp,
                    modifier = Modifier.padding(top = 18.dp)
                )
                is LessonSection.BulletList -> Column(Modifier.padding(top = 20.dp)) {
                    section.title?.let { Text(it, fontWeight = FontWeight.Bold, fontSize = 19.sp) }
                    section.items.forEach { item ->
                        Text("• $item", fontSize = 16.sp, lineHeight = 24.sp, modifier = Modifier.padding(top = 8.dp))
                    }
                }
                is LessonSection.KeyFact -> Column(
                    Modifier.fillMaxWidth().padding(top = 22.dp)
                ) {
                    Text(section.title, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    Text(section.text, modifier = Modifier.padding(top = 6.dp), fontWeight = FontWeight.Medium)
                }
            }
        }

        Text(
            "${questionCountForLesson(lesson.id)} questions associées",
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 28.dp)
        )
        Button(
            onClick = { onStartPractice(lesson.id) },
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp).testTag("start_lesson_practice")
        ) { Text("S’entraîner sur ce thème") }

        if (lesson.sources.isNotEmpty()) {
            Text("Sources officielles", fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 28.dp))
            lesson.sources.forEach { source ->
                TextButton(onClick = { uriHandler.openUri(source.url) }) { Text(source.title) }
            }
        }

        if (!lessonCompleted) {
            OutlinedButton(
                onClick = { onLessonCompleted(lesson.id) },
                modifier = Modifier.fillMaxWidth().padding(top = 20.dp).testTag("complete_lesson")
            ) { Text("Terminer la leçon") }
        } else {
            Text(
                "✓ Leçon terminée",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 20.dp)
            )
        }

        Spacer(Modifier.height(20.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(
                onClick = { currentLessonIndex-- },
                enabled = currentLessonIndex > 0,
                modifier = Modifier.weight(1f)
            ) { Text("← Précédente") }
            Button(
                onClick = { currentLessonIndex++ },
                enabled = currentLessonIndex < course.lessons.lastIndex,
                modifier = Modifier.weight(1f)
            ) { Text("Suivante →") }
        }
    }
}
