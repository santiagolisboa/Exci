package com.examen.civique.ui.courses

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.examen.civique.domain.model.Course
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun CoursesAdaptiveScreen(
    courses: List<Course>,
    completedCourseIds: Set<Int>,
    onCourseFinished: (Int) -> Unit
) {

    val navigator = rememberListDetailPaneScaffoldNavigator<Int>()
    val scope = rememberCoroutineScope()

    NavigableListDetailPaneScaffold(
        navigator = navigator,
        listPane = {
            AnimatedPane {
                CoursesScreen(
                    courses = courses,
                    completedCourseIds = completedCourseIds,
                    onOpenCourse = { courseId ->
                        scope.launch {
                            navigator.navigateTo(
                                ListDetailPaneScaffoldRole.Detail,
                                courseId
                            )
                        }
                    }
                )
            }
        },
        detailPane = {
            AnimatedPane {
                val courseId = navigator.currentDestination?.contentKey
                val course = courses.find { it.id == courseId }
                if (course != null) {
                    CourseDetailScreen(
                        course = course,
                        onCourseFinished = {
                            onCourseFinished(course.id)
                            scope.launch {
                                navigator.navigateBack()
                            }
                        }
                    )
                }
            }
        }
    )
}
