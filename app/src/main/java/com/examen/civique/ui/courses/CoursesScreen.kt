package com.examen.civique.ui.courses

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.examen.civique.domain.engine.CourseProgressEngine
import com.examen.civique.domain.model.Course
import com.examen.civique.domain.model.CourseProgress

@Composable
fun CoursesScreen(
    courses: List<Course>,
    progress: List<CourseProgress>,
    questionCountForLesson: (String) -> Int,
    onOpenCourse: (Int) -> Unit
) {
    val progressEngine = CourseProgressEngine()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .testTag("courses_list"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text("Cours", fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Text(
                "Apprends chaque notion puis entraîne-toi avec les questions officielles.",
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        items(courses, key = { it.id }) { course ->
            val courseProgress = progress.find { it.courseId == course.id }
            val completedCount = progressEngine.completedLessonCount(courseProgress, course)
            val questionCount = course.lessons.sumOf { questionCountForLesson(it.id) }
            val progressValue = if (course.lessons.isEmpty()) 0f else
                completedCount.toFloat() / course.lessons.size
            val status = when {
                courseProgress?.completed == true -> "Terminé"
                completedCount > 0 || courseProgress?.lastLessonId != null -> "En cours"
                else -> "À commencer"
            }

            Card(
                onClick = { onOpenCourse(course.id) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(course.icon, fontSize = 32.sp)
                        Column(Modifier.padding(start = 12.dp).weight(1f)) {
                            Text(course.title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            Text(course.description, fontSize = 15.sp)
                        }
                    }
                    LinearProgressIndicator(
                        progress = { progressValue },
                        modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("$completedCount / ${course.lessons.size} leçons")
                        Text("$questionCount questions")
                    }
                    Text(
                        text = status,
                        color = if (courseProgress?.completed == true)
                            MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}
