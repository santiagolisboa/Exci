package com.examen.civique.ui.courses

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun CoursesAdaptiveScreen(viewModel: CourseViewModel) {
    val navigator = rememberListDetailPaneScaffoldNavigator<Int>()
    val scope = rememberCoroutineScope()
    val progress by viewModel.progress.collectAsStateWithLifecycle()
    val practiceState by viewModel.practiceState.collectAsStateWithLifecycle()

    NavigableListDetailPaneScaffold(
        navigator = navigator,
        listPane = {
            AnimatedPane {
                CoursesScreen(
                    courses = viewModel.courses,
                    progress = progress,
                    questionCountForLesson = { viewModel.questionsForLesson(it).size },
                    onOpenCourse = { courseId ->
                        viewModel.closePractice()
                        scope.launch {
                            navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, courseId)
                        }
                    }
                )
            }
        },
        detailPane = {
            AnimatedPane {
                val courseId = navigator.currentDestination?.contentKey
                val course = viewModel.courses.find { it.id == courseId }
                if (course != null) {
                    val practice = practiceState
                    if (practice != null) {
                        LessonPracticeScreen(
                            state = practice,
                            onSelectAnswer = viewModel::selectPracticeAnswer,
                            onValidateAnswer = viewModel::validatePracticeAnswer,
                            onNextQuestion = viewModel::nextPracticeQuestion,
                            onClose = viewModel::closePractice
                        )
                    } else {
                        CourseDetailScreen(
                            course = course,
                            progress = progress.find { it.courseId == course.id },
                            questionCountForLesson = { viewModel.questionsForLesson(it).size },
                            onLessonOpened = { viewModel.openLesson(course.id, it) },
                            onLessonCompleted = { viewModel.completeLesson(course.id, it) },
                            onStartPractice = viewModel::startLessonPractice
                        )
                    }
                }
            }
        }
    )
}
