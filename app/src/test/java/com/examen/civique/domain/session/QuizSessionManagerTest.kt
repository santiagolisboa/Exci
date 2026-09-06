package com.examen.civique.domain.session

import com.examen.civique.domain.model.DifficultyLevel
import com.examen.civique.domain.model.Question
import com.examen.civique.domain.model.QuestionCategory
import com.examen.civique.domain.model.QuestionType
import com.examen.civique.domain.model.QuizAnswer
import com.examen.civique.domain.model.QuizSession
import com.examen.civique.domain.model.QuizState
import com.examen.civique.domain.repository.QuestionRepository
import com.examen.civique.domain.repository.QuizSessionRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class QuizSessionManagerTest {
    private val sourceQuestions = listOf(question("one"), question("two"), question("three"))
    private val repository = InMemoryQuizSessionRepository()
    private val manager = QuizSessionManager(
        sessionRepository = repository,
        questionRepository = object : QuestionRepository {
            override fun getQuestions(): List<Question> = sourceQuestions
        }
    )

    @Test
    fun `save and restore preserves the complete quiz state and both orders`() {
        val first = sourceQuestions[1].copy(
            answers = listOf("two-C", "two-A", "two-D", "two-B"),
            correctAnswerIndex = 1
        )
        val second = sourceQuestions[0].copy(
            answers = listOf("one-D", "one-C", "one-B", "one-A"),
            correctAnswerIndex = 3
        )
        val state = QuizState(
            questions = listOf(first, second, sourceQuestions[2]),
            currentQuestionIndex = 1,
            selectedAnswerIndex = 3,
            answerValidated = true,
            score = 2,
            answers = listOf(
                QuizAnswer(first, 1, true),
                QuizAnswer(second, 3, true)
            )
        )

        manager.saveSession(state)

        assertTrue(manager.hasSession())
        assertEquals(state, manager.restoreSession())
    }

    @Test
    fun `starting a new quiz abandons the previous session`() {
        manager.saveSession(QuizState(questions = sourceQuestions))
        val replacement = QuizState(questions = sourceQuestions.reversed())

        manager.startNewSession(replacement)

        assertTrue(repository.deleteCount > 0)
        assertEquals(replacement, manager.restoreSession())
    }

    @Test
    fun `finishing a quiz deletes its resumable session`() {
        manager.saveSession(QuizState(questions = sourceQuestions))

        manager.saveSession(
            QuizState(questions = sourceQuestions, quizFinished = true)
        )

        assertFalse(manager.hasSession())
        assertNull(repository.getSession())
    }

    private fun question(id: String) = Question(
        id = id,
        question = "Question $id",
        answers = listOf("$id-A", "$id-B", "$id-C", "$id-D"),
        correctAnswerIndex = 0,
        category = QuestionCategory.PRINCIPES_VALEURS,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.MEDIUM,
        explanation = "Explanation",
        lessonId = "lesson",
        official = true,
        sourceId = "source",
        verified = true
    )

    private class InMemoryQuizSessionRepository : QuizSessionRepository {
        private var session: QuizSession? = null
        var deleteCount = 0

        override fun saveSession(session: QuizSession) {
            this.session = session
        }

        override fun getSession(): QuizSession? = session

        override fun deleteSession() {
            deleteCount++
            session = null
        }
    }
}
