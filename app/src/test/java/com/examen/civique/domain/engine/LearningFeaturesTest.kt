package com.examen.civique.domain.engine

import com.examen.civique.domain.model.*
import org.junit.Assert.*
import org.junit.Test

class LearningFeaturesTest {
    private fun question(id: String, category: QuestionCategory = QuestionCategory.HISTOIRE) = Question(
        id = id, question = "Question $id", answers = listOf("bonne", "mauvaise"),
        correctAnswerIndex = 0, category = category, type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.EASY, explanation = "", lessonId = "lesson",
        official = false, sourceId = "test", verified = true
    )

    private fun attempt(
        id: String,
        correct: Boolean,
        time: Long = 100L,
        category: QuestionCategory = QuestionCategory.HISTOIRE,
        source: AnswerSource = AnswerSource.QUIZ
    ) = AnswerAttempt(
        questionId = id,
        category = category,
        selectedAnswer = if (correct) "bonne" else "mauvaise",
        isCorrect = correct,
        answeredAt = time,
        source = source
    )

    private val bank244 = (1..244).map { question("q$it") }

    @Test
    fun wrongAnswersAreAggregatedAndCorrectAnswerMastersWithoutDeletingHistory() {
        val first = ErrorTracker.update(null, attempt("q1", false))!!
        val second = ErrorTracker.update(first, attempt("q1", false, 200))!!
        val mastered = ErrorTracker.update(second, attempt("q1", true, 300))!!
        assertEquals(2, mastered.errorCount)
        assertFalse(mastered.active)
        assertEquals("mauvaise", mastered.lastWrongAnswer)
    }

    @Test
    fun progressionCountsUniqueQuestionsNotAttempts() {
        assertEquals(0, StatisticsCalculator.calculate(bank244, emptyList(), emptyList()).questionsSeen)
        assertEquals(1, StatisticsCalculator.calculate(bank244, emptyList(), listOf(attempt("q1", true))).questionsSeen)
        val repeated = listOf(attempt("q1", false, 1), attempt("q1", false, 2), attempt("q1", true, 3))
        assertEquals(1, StatisticsCalculator.calculate(bank244, emptyList(), repeated).questionsSeen)
        val ten = (1..10).map { attempt("q$it", true, it.toLong()) }
        val stats = StatisticsCalculator.calculate(bank244, emptyList(), ten)
        assertEquals(10, stats.questionsSeen)
        assertEquals(234, stats.questionsRemaining)
        assertEquals(244, stats.totalQuestions)
    }

    @Test
    fun performanceMasteryAndHistoryUseEveryAttempt() {
        val attempts = listOf(attempt("q1", false, 1), attempt("q1", true, 2), attempt("q2", false, 3))
        val stats = StatisticsCalculator.calculate(bank244, emptyList(), attempts)
        assertEquals(3, stats.answered)
        assertEquals(1, stats.correct)
        assertEquals(2, stats.wrong)
        assertEquals(33, stats.successRate)
        assertEquals(MasteryStatus.MASTERED, stats.masteryByQuestion["q1"])
        assertEquals(MasteryStatus.TO_REVIEW, stats.masteryByQuestion["q2"])
        assertEquals(MasteryStatus.NEVER_SEEN, stats.masteryByQuestion["q3"])
    }

    @Test
    fun categoryStatsDistinguishCoverageFromSuccess() {
        val bank = listOf(question("h1"), question("h2"), question("g1", QuestionCategory.GEOGRAPHIE))
        val attempts = listOf(attempt("h1", true), attempt("h1", false, 2))
        val history = StatisticsCalculator.calculate(bank, emptyList(), attempts).byCategory
            .first { it.category == QuestionCategory.HISTOIRE }
        assertEquals(1, history.questionsSeen)
        assertEquals(2, history.totalQuestions)
        assertEquals(50, history.coverage)
        assertEquals(2, history.attempts)
        assertEquals(50, history.successRate)
    }

    @Test
    fun quizValidationCreatesExactlyOneAttemptEvenAfterRestore() {
        val initial = QuizState(questions = listOf(question("q1")))
        val selected = QuizEngine().selectAnswer(initial, 0)
        val validated = QuizEngine().validateAnswer(selected)
        assertNotNull(AnswerAttemptFactory.fromQuizValidation(selected, validated, 1))
        assertNull(AnswerAttemptFactory.fromQuizValidation(validated, validated, 2))
    }

    @Test
    fun simulationAttemptsAndFinalResultCoexistWithoutDoubleCounting() {
        val bank = (1..40).map { question("e$it") }
        val attempts = bank.mapIndexed { index, q -> attempt(q.id, true, index.toLong(), source = AnswerSource.EXAM) }
        val result = SessionResult(1, SessionType.EXAM, 40, 40, 40, 0, 100, 100)
        val stats = StatisticsCalculator.calculate(bank, listOf(result), attempts)
        assertEquals(40, stats.answered)
        assertEquals(1, stats.examCount)
        assertFalse(AnswerAttemptFactory.shouldRecordExamSelection(0, 0))
        assertTrue(AnswerAttemptFactory.shouldRecordExamSelection(null, 0))
    }

    @Test
    fun exitPolicyOnlyConfirmsActiveSessions() {
        assertEquals(ExitConsequence.QUIZ_SAVED, ExitPolicy.forRoute("quiz", true, false))
        assertEquals(ExitConsequence.EXAM_FINISHED, ExitPolicy.forRoute("exam", false, true))
        assertEquals(ExitConsequence.NONE, ExitPolicy.forRoute("stats", false, false))
    }
}
