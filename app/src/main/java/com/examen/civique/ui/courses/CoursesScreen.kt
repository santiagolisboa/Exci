package com.examen.civique.ui.courses

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.examen.civique.domain.model.Course

@Composable
fun CoursesScreen(
    courses: List<Course>,
    completedCourseIds: Set<Int>,
    onOpenCourse: (Int) -> Unit
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {

            Text(
                text = "Cours",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Choisis un thème pour commencer à réviser.",
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        items(courses) { course ->

            val completed =
                course.id in completedCourseIds

            Card(
                onClick = {
                    onOpenCourse(course.id)
                },
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = course.icon,
                        fontSize = 32.sp
                    )

                    Text(
                        text = course.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Text(
                        text = course.description,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(top = 6.dp)
                    )

                    Text(
                        text = if (completed) {
                            "✅ Terminé"
                        } else {
                            "${course.lessons.size} leçon(s)"
                        },
                        fontSize = 13.sp,
                        fontWeight = if (completed) {
                            FontWeight.Bold
                        } else {
                            FontWeight.Normal
                        },
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }
            }
        }
    }
}