package com.examen.civique.navigation

import androidx.compose.material.icons.Icons
import androidx.activity.compose.BackHandler
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
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
import com.examen.civique.data.repository.AndroidQuizSessionRepository
import com.examen.civique.data.repository.AndroidFavoriteQuestionRepository
import com.examen.civique.data.repository.AndroidLearningRepository
import com.examen.civique.data.repository.AndroidAnswerAttemptRepository
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
import com.examen.civique.ui.review.FavoritesScreen
import com.examen.civique.ui.review.ReviewViewModel
import com.examen.civique.ui.review.ReviewViewModelFactory
import com.examen.civique.ui.stats.StatsScreen
import com.examen.civique.ui.stats.StatsViewModel
import com.examen.civique.ui.stats.StatsViewModelFactory
import com.examen.civique.domain.model.ThemeMode
import com.examen.civique.domain.model.ExitConsequence
import com.examen.civique.domain.model.ExitPolicy
import com.examen.civique.domain.model.SessionType
import com.examen.civique.domain.model.displayName

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

    val quizSessionRepository = remember(context) {
        AndroidQuizSessionRepository(context)
    }

    val learningRepository = remember(database) {
        AndroidLearningRepository(database.learningDao(), database.quizResultDao())
    }

    val attemptRepository = remember(database) {
        AndroidAnswerAttemptRepository(database.learningDao())
    }

    val favoriteRepository = remember(database) {
        AndroidFavoriteQuestionRepository(database.favoriteQuestionDao())
    }

    val courseProgressRepository =
        AndroidCourseProgressRepository(
            database.courseProgressDao()
        )

    val quizViewModel: QuizViewModel =
        viewModel(
            factory = QuizViewModelFactory(
                questionRepository = questionRepository,
                sessionRepository = quizSessionRepository,
                learningRepository = learningRepository,
                attemptRepository = attemptRepository,
                favoriteRepository = favoriteRepository
            )
        )

    val statsViewModel: StatsViewModel =
        viewModel(
            factory = StatsViewModelFactory(
                repository = learningRepository,
                attemptRepository = attemptRepository,
                questionRepository = questionRepository
            )
        )

    val reviewViewModel: ReviewViewModel = viewModel(
        factory = ReviewViewModelFactory(questionRepository, learningRepository, favoriteRepository)
    )

    val courseViewModel: CourseViewModel =
        viewModel(
            factory = CourseViewModelFactory(
                courseRepository = courseRepository,
                progressRepository = courseProgressRepository,
                questionRepository = questionRepository
            )
        )

    val quizUiState by
        quizViewModel.uiState.collectAsStateWithLifecycle()

    val hasSavedQuiz by
        quizViewModel.hasSavedSession.collectAsStateWithLifecycle()

    val statsUiState by
    statsViewModel.uiState.collectAsStateWithLifecycle()

    val activeErrors by reviewViewModel.errors.collectAsStateWithLifecycle()
    val favoriteIds by reviewViewModel.favoriteIds.collectAsStateWithLifecycle()

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
                ),
                TopLevelDestination(
                    route = "favorites",
                    label = "Favoris",
                    icon = Icons.Default.Star
                )
            )
        }

    val showNavigationSuite = true
    var activeExamViewModel by remember { mutableStateOf<ExamViewModel?>(null) }
    var examActive by remember { mutableStateOf(false) }
    var pendingExit by remember { mutableStateOf(ExitConsequence.NONE) }

    val navigateHome = {
        navController.navigate("home") {
            popUpTo(navController.graph.findStartDestination().id) { inclusive = false }
            launchSingleTop = true
            restoreState = true
        }
    }

    if (pendingExit != ExitConsequence.NONE) {
        val examExit = pendingExit == ExitConsequence.EXAM_FINISHED
        AlertDialog(
            onDismissRequest = { pendingExit = ExitConsequence.NONE },
            title = { Text(if (examExit) "Quitter la simulation ?" else "Quitter le quiz ?") },
            text = { Text(if (examExit) "Si tu quittes maintenant, la simulation sera considérée comme terminée." else "Ta progression sera sauvegardée et tu pourras reprendre ce quiz plus tard.") },
            confirmButton = {
                TextButton(onClick = {
                    if (examExit) activeExamViewModel?.finishBecauseAppLeft() else quizViewModel.saveActiveSession()
                    pendingExit = ExitConsequence.NONE
                    navigateHome()
                }) { Text(if (examExit) "Quitter la simulation" else "Quitter") }
            },
            dismissButton = {
                TextButton(onClick = { pendingExit = ExitConsequence.NONE }) {
                    Text(if (examExit) "Continuer l'examen" else "Rester")
                }
            }
        )
    }

    val currentExitConsequence = ExitPolicy.forRoute(
        currentDestination?.route,
        quizUiState.isActive,
        examActive
    )
    BackHandler(enabled = currentExitConsequence != ExitConsequence.NONE) {
        pendingExit = currentExitConsequence
    }

    NavigationSuiteScaffold(
        layoutType = NavigationSuiteScaffoldDefaults
            .calculateFromAdaptiveInfo(adaptiveInfo),
        navigationSuiteItems = {

            if (showNavigationSuite) {

                val destinations = if (currentExitConsequence != ExitConsequence.NONE) {
                    topLevelDestinations.filter { it.route == "home" }
                } else topLevelDestinations

                destinations.forEach { destination ->

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

                            if (destination.route == "home") {
                                if (currentExitConsequence == ExitConsequence.NONE) navigateHome()
                                else pendingExit = currentExitConsequence
                            } else navController.navigate(destination.route) {

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
                progress = statsUiState.stats.progress,
                successRate = statsUiState.stats.successRate,
                weakestCategory = statsUiState.stats.weakCategories.firstOrNull()?.category?.displayName(),
                errorCount = activeErrors.size,
                favoriteCount = favoriteIds.size,
                adaptiveInfo = adaptiveInfo,
                hasSavedQuiz = hasSavedQuiz,
                savedQuestionNumber = quizUiState.currentQuestionIndex.takeIf { hasSavedQuiz }?.plus(1),
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
                },
                onOpenFavorites = { navController.navigate("favorites") }
            )
        }

        composable("courses") {

            CoursesAdaptiveScreen(
                viewModel = courseViewModel
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
                viewModel = reviewViewModel,
                onReview = { ids ->
                    quizViewModel.startQuiz(ids, SessionType.ERROR_REVIEW)
                    navController.navigate("quiz")
                }
            )
        }

        composable("favorites") {
            FavoritesScreen(
                viewModel = reviewViewModel,
                onReview = { ids ->
                    quizViewModel.startQuiz(ids, SessionType.FAVORITES_REVIEW)
                    navController.navigate("quiz")
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
                            questionRepository = questionRepository,
                            learningRepository = learningRepository,
                            attemptRepository = attemptRepository,
                            favoriteRepository = favoriteRepository
                        )
                    )

                DisposableEffect(examViewModel) {
                    activeExamViewModel = examViewModel
                    onDispose {
                        if (activeExamViewModel === examViewModel) activeExamViewModel = null
                        examActive = false
                    }
                }
                LaunchedEffect(examViewModel) {
                    examViewModel.uiState.collect { examActive = !it.examFinished }
                }

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
                            questionRepository = questionRepository,
                            learningRepository = learningRepository,
                            attemptRepository = attemptRepository,
                            favoriteRepository = favoriteRepository
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
                            questionRepository = questionRepository,
                            learningRepository = learningRepository,
                            attemptRepository = attemptRepository,
                            favoriteRepository = favoriteRepository
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
