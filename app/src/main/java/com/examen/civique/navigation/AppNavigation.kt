package com.examen.civique.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.examen.civique.data.local.database.DatabaseProvider
import com.examen.civique.data.repository.AndroidCourseProgressRepository
import com.examen.civique.data.repository.AndroidQuizResultRepository
import com.examen.civique.data.repository.AndroidQuizSessionRepository
import com.examen.civique.data.repository.StaticCourseRepository
import com.examen.civique.data.repository.StaticQuestionRepository
import com.examen.civique.ui.courses.CoursesAdaptiveScreen
import com.examen.civique.ui.courses.CourseViewModel
import com.examen.civique.ui.courses.CourseViewModelFactory
import com.examen.civique.ui.exam.ExamResultScreen
import com.examen.civique.ui.exam.ExamReviewScreen
import com.examen.civique.ui.exam.ExamScreen
import com.examen.civique.ui.exam.ExamViewModel
import com.examen.civique.ui.exam.ExamViewModelFactory
import com.examen.civique.ui.home.HomeScreen
import com.examen.civique.ui.quiz.QuizScreen
import com.examen.civique.ui.quiz.QuizViewModel
import com.examen.civique.ui.quiz.QuizViewModelFactory
import com.examen.civique.ui.result.ResultScreen
import com.examen.civique.ui.review.ErrorReviewScreen
import com.examen.civique.ui.stats.StatsScreen
import com.examen.civique.ui.stats.StatsViewModel
import com.examen.civique.ui.stats.StatsViewModelFactory
import com.examen.civique.domain.model.ThemeMode

@Composable
fun AppNavigation(
    adaptiveInfo: WindowAdaptiveInfo,
    themeMode: ThemeMode? = null,
    onThemeSelected: (ThemeMode) -> Unit = {}
) {

    val navController = rememberNavController()

    val context = LocalContext.current

    val database =
        DatabaseProvider.getDatabase(context)

    val questionRepository =
        StaticQuestionRepository()

    val courseRepository =
        StaticCourseRepository()

    val quizResultRepository =
        AndroidQuizResultRepository(
            database.quizResultDao()
        )

    val quizSessionRepository = remember(context) {
        AndroidQuizSessionRepository(context)
    }

    val courseProgressRepository =
        AndroidCourseProgressRepository(
            database.courseProgressDao()
        )

    val quizViewModel: QuizViewModel =
        viewModel(
            factory = QuizViewModelFactory(
                questionRepository = questionRepository,
                resultRepository = quizResultRepository,
                sessionRepository = quizSessionRepository
            )
        )

    val statsViewModel: StatsViewModel =
        viewModel(
            factory = StatsViewModelFactory(
                quizResultRepository
            )
        )

    val courseViewModel: CourseViewModel =
        viewModel(
            factory = CourseViewModelFactory(
                courseRepository = courseRepository,
                progressRepository = courseProgressRepository
            )
        )

    val quizUiState by
        quizViewModel.uiState.collectAsStateWithLifecycle()

    val hasSavedQuiz by
        quizViewModel.hasSavedSession.collectAsStateWithLifecycle()

    val statsUiState by
    statsViewModel.uiState.collectAsStateWithLifecycle()

    val courseProgress by
    courseViewModel.progress.collectAsStateWithLifecycle()

    val completedCourseIds =
        courseProgress
            .filter { it.completed }
            .map { it.courseId }
            .toSet()

    val navBackStackEntry by
    navController.currentBackStackEntryAsState()

    val currentDestination =
        navBackStackEntry?.destination

    val topLevelDestinations =
        remember {
            listOf(
                TopLevelDestination(
                    route = "home",
                    label = "Accueil",
                    icon = Icons.Default.Home
                ),
                TopLevelDestination(
                    route = "courses",
                    label = "Cours",
                    icon = Icons.Default.Book
                ),
                TopLevelDestination(
                    route = "stats",
                    label = "Stats",
                    icon = Icons.Default.BarChart
                ),
                TopLevelDestination(
                    route = "errors",
                    label = "Erreurs",
                    icon = Icons.Default.Error
                )
            )
        }

    val showNavigationSuite =
        currentDestination?.route in listOf(
            "home",
            "courses",
            "stats",
            "errors"
        )

    NavigationSuiteScaffold(
        layoutType = NavigationSuiteScaffoldDefaults
            .calculateFromAdaptiveInfo(adaptiveInfo),
        navigationSuiteItems = {

            if (showNavigationSuite) {

                topLevelDestinations.forEach { destination ->

                    item(
                        icon = {
                            Icon(
                                imageVector = destination.icon,
                                contentDescription = destination.label
                            )
                        },
                        label = {
                            Text(destination.label)
                        },
                        selected = currentDestination
                            ?.hierarchy
                            ?.any {
                                it.route == destination.route
                            } == true,
                        onClick = {

                            navController.navigate(destination.route) {

                                popUpTo(
                                    navController.graph.findStartDestination().id
                                ) {
                                    saveState = true
                                }

                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) {

        NavHost(
            navController = navController,
            startDestination = "home"
        ) {

        composable("home") {

            HomeScreen(
                progress = statsUiState.averageScore,
                quizCount = statsUiState.quizCount,
                adaptiveInfo = adaptiveInfo,
                hasSavedQuiz = hasSavedQuiz,
                themeMode = themeMode,

                onStartNewQuiz = {
                    quizViewModel.startNewQuiz()
                    navController.navigate("quiz")
                },

                onContinueQuiz = {
                    if (!quizViewModel.continueQuiz()) {
                        quizViewModel.startNewQuiz()
                    }
                    navController.navigate("quiz")
                },

                onThemeSelected = onThemeSelected,

                onStartExam = {
                    navController.navigate("examFlow")
                },

                onOpenCourses = {

                    navController.navigate("courses")
                },

                onOpenErrors = {

                    navController.navigate("errors")
                },

                onOpenStats = {

                    navController.navigate("stats")
                }
            )
        }

        composable("courses") {

            CoursesAdaptiveScreen(
                courses = courseViewModel.courses,
                completedCourseIds = completedCourseIds,
                onCourseFinished = { courseId ->
                    courseViewModel.markCourseCompleted(courseId)
                }
            )
        }

        composable("stats") {

            StatsScreen(
                viewModel = statsViewModel
            )
        }

        composable("quiz") {

            QuizScreen(
                viewModel = quizViewModel,
                adaptiveInfo = adaptiveInfo,

                onQuizFinished = {

                    navController.navigate("result") {

                        popUpTo("quiz") {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable("result") {

            ResultScreen(
                score = quizUiState.score,
                totalQuestions = quizUiState.questions.size,
                wrongAnswersCount =
                    quizViewModel.wrongAnswers.size,

                onRestartQuiz = {

                    quizViewModel.startNewQuiz()

                    navController.navigate("quiz") {

                        popUpTo("result") {
                            inclusive = true
                        }
                    }
                },

                onReviewErrors = {

                    navController.navigate("errors")
                },

                onGoHome = {
                    navController.navigate("home") {

                        popUpTo("home") {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable("errors") {

            ErrorReviewScreen(
                wrongAnswers =
                    quizViewModel.wrongAnswers,

                onGoHome = {
                    navController.navigate("home") {

                        popUpTo("home") {
                            inclusive = true
                        }
                    }
                }
            )
        }

        navigation(
            startDestination = "exam",
            route = "examFlow"
        ) {

            composable("exam") { backStackEntry ->

                val examFlowEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("examFlow")
                }

                val examViewModel: ExamViewModel =
                    viewModel(
                        viewModelStoreOwner = examFlowEntry,
                        factory = ExamViewModelFactory(
                            questionRepository = questionRepository
                        )
                    )

                ExamScreen(
                    viewModel = examViewModel,
                    adaptiveInfo = adaptiveInfo,

                    onExamFinished = {

                        navController.navigate("examResult") {

                            popUpTo("exam") {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable("examResult") { backStackEntry ->

                val examFlowEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("examFlow")
                }

                val examViewModel: ExamViewModel =
                    viewModel(
                        viewModelStoreOwner = examFlowEntry,
                        factory = ExamViewModelFactory(
                            questionRepository = questionRepository
                        )
                    )

                val examUiState by
                    examViewModel.uiState.collectAsStateWithLifecycle()

                ExamResultScreen(
                    score = examViewModel.score,
                    totalQuestions =
                        examUiState.questions.size,
                    wrongAnswersCount =
                        examViewModel.wrongAnswersCount,
                    unansweredQuestionsCount =
                        examViewModel.unansweredQuestionsCount,
                    finishReason =
                        examUiState.finishReason,

                    onReviewErrors = {

                        navController.navigate(
                            "examReview"
                        )
                    },

                    onRestartExam = {

                        examViewModel.startNewExam()

                        navController.navigate("exam") {

                            popUpTo("examResult") {
                                inclusive = true
                            }
                        }
                    },

                    onGoHome = {

                        navController.navigate("home") {

                            popUpTo("examFlow") {
                                inclusive = true
                            }

                            launchSingleTop = true
                        }
                    }
                )
            }

            composable("examReview") { backStackEntry ->

                val examFlowEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("examFlow")
                }

                val examViewModel: ExamViewModel =
                    viewModel(
                        viewModelStoreOwner = examFlowEntry,
                        factory = ExamViewModelFactory(
                            questionRepository = questionRepository
                        )
                    )

                ExamReviewScreen(
                    errors =
                        examViewModel.wrongAnswerReviews,

                    onGoHome = {

                        navController.navigate("home") {

                            popUpTo("examFlow") {
                                inclusive = true
                            }

                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}
}

private data class TopLevelDestination(
    val route: String,
    val label: String,
    val icon: ImageVector
)
