package com.examen.civique.data.local

import com.examen.civique.domain.model.QuestionCategory
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.text.Normalizer

class QuestionsDataTest {

    @Test
    fun `official question bank has expected total`() {
        assertEquals(244, questions.size)
    }

    @Test
    fun `question counts by category match the completed bank`() {
        assertEquals(
            mapOf(
                QuestionCategory.PRINCIPES_VALEURS to 35,
                QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE to 53,
                QuestionCategory.GEOGRAPHIE to 23,
                QuestionCategory.HISTOIRE to 54,
                QuestionCategory.SOCIETE to 68,
                QuestionCategory.ECONOMIE to 11
            ),
            questions.groupingBy { it.category }.eachCount()
        )
    }

    @Test
    fun `question ids are non-empty and unique`() {
        assertTrue(questions.all { it.id.isNotBlank() })
        assertEquals(questions.size, questions.map { it.id }.distinct().size)
    }

    @Test
    fun `questions and answers are non-empty`() {
        assertTrue(questions.all { it.question.isNotBlank() })
        assertTrue(questions.all { question -> question.answers.all(String::isNotBlank) })
    }

    @Test
    fun `every question has four distinct answers and one valid correct answer`() {
        questions.forEach { question ->
            assertEquals("Unexpected answer count for ${question.id}", 4, question.answers.size)
            assertTrue(
                "Invalid correct answer index for ${question.id}",
                question.correctAnswerIndex in question.answers.indices
            )

            val correctAnswer = question.answers[question.correctAnswerIndex]
            assertEquals(
                "Correct answer is duplicated for ${question.id}",
                1,
                question.answers.count { it == correctAnswer }
            )
            assertEquals(
                "Answer propositions are duplicated for ${question.id}",
                question.answers.size,
                question.answers.map(::normalize).distinct().size
            )
        }
    }

    @Test
    fun `all categories belong to the domain enum`() {
        val validCategories = QuestionCategory.entries.toSet()
        assertTrue(questions.all { it.category in validCategories })
    }

    @Test
    fun `all official metadata is complete`() {
        questions.forEach { question ->
            assertTrue("Question not marked official: ${question.id}", question.official)
            assertTrue("Question not marked verified: ${question.id}", question.verified)
            assertTrue("Missing source for ${question.id}", question.sourceId.isNotBlank())
            assertTrue("Missing explanation for ${question.id}", question.explanation.isNotBlank())
            assertTrue("Missing lesson id for ${question.id}", question.lessonId.isNotBlank())
        }
    }

    @Test
    fun `question texts are unique exactly and after normalization`() {
        assertEquals(
            "Exact duplicate question text detected",
            questions.size,
            questions.map { it.question }.distinct().size
        )

        val normalizedQuestions = questions.groupBy { normalize(it.question) }
        val duplicates = normalizedQuestions.filterValues { it.size > 1 }
        assertTrue(
            "Normalized duplicate questions detected: ${duplicates.values.flatten().map { it.id }}",
            duplicates.isEmpty()
        )
    }

    private fun normalize(value: String): String =
        Normalizer.normalize(value.lowercase(), Normalizer.Form.NFD)
            .replace("\\p{M}+".toRegex(), "")
            .replace("[^a-z0-9]+".toRegex(), " ")
            .trim()
            .replace("\\s+".toRegex(), " ")
}
