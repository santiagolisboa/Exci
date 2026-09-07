package com.examen.civique.data.repository

import android.content.Context
import androidx.core.content.edit
import com.examen.civique.domain.model.QuizSession
import com.examen.civique.domain.model.SavedQuizAnswer
import com.examen.civique.domain.model.SavedQuizQuestion
import com.examen.civique.domain.repository.QuizSessionRepository
import com.examen.civique.domain.model.SessionType
import org.json.JSONArray
import org.json.JSONObject

class AndroidQuizSessionRepository(context: Context) : QuizSessionRepository {
    private val preferences = context.applicationContext.getSharedPreferences(
        PREFERENCES_NAME,
        Context.MODE_PRIVATE
    )

    override fun saveSession(session: QuizSession) {
        preferences.edit { putString(SESSION_KEY, session.toJson().toString()) }
    }

    override fun getSession(): QuizSession? {
        val encoded = preferences.getString(SESSION_KEY, null) ?: return null
        return runCatching { JSONObject(encoded).toQuizSession() }
            .getOrElse {
                deleteSession()
                null
            }
    }

    override fun deleteSession() {
        preferences.edit { remove(SESSION_KEY) }
    }

    private fun QuizSession.toJson() = JSONObject().apply {
        put("sessionType", sessionType.name)
        put("currentQuestionIndex", currentQuestionIndex)
        put("selectedAnswerIndex", selectedAnswerIndex ?: JSONObject.NULL)
        put("answerValidated", answerValidated)
        put("score", score)
        put("questions", JSONArray().apply {
            questions.forEach { saved ->
                put(JSONObject().apply {
                    put("id", saved.id)
                    put("answers", JSONArray(saved.answers))
                    put("correctAnswerIndex", saved.correctAnswerIndex)
                })
            }
        })
        put("answers", JSONArray().apply {
            answers.forEach { saved ->
                put(JSONObject().apply {
                    put("questionId", saved.questionId)
                    put("selectedAnswerIndex", saved.selectedAnswerIndex)
                    put("isCorrect", saved.isCorrect)
                })
            }
        })
    }

    private fun JSONObject.toQuizSession(): QuizSession {
        val questionArray = getJSONArray("questions")
        val savedQuestions = buildList {
            for (index in 0 until questionArray.length()) {
                val saved = questionArray.getJSONObject(index)
                val answerArray = saved.getJSONArray("answers")
                add(
                    SavedQuizQuestion(
                        id = saved.getString("id"),
                        answers = buildList {
                            for (answerIndex in 0 until answerArray.length()) {
                                add(answerArray.getString(answerIndex))
                            }
                        },
                        correctAnswerIndex = saved.getInt("correctAnswerIndex")
                    )
                )
            }
        }

        val answerArray = getJSONArray("answers")
        val savedAnswers = buildList {
            for (index in 0 until answerArray.length()) {
                val saved = answerArray.getJSONObject(index)
                add(
                    SavedQuizAnswer(
                        questionId = saved.getString("questionId"),
                        selectedAnswerIndex = saved.getInt("selectedAnswerIndex"),
                        isCorrect = saved.getBoolean("isCorrect")
                    )
                )
            }
        }

        return QuizSession(
            sessionType = runCatching {
                SessionType.valueOf(optString("sessionType", SessionType.QUIZ.name))
            }.getOrDefault(SessionType.QUIZ),
            questions = savedQuestions,
            currentQuestionIndex = getInt("currentQuestionIndex"),
            selectedAnswerIndex = if (isNull("selectedAnswerIndex")) {
                null
            } else {
                getInt("selectedAnswerIndex")
            },
            answerValidated = getBoolean("answerValidated"),
            score = getInt("score"),
            answers = savedAnswers
        )
    }

    private companion object {
        const val PREFERENCES_NAME = "quiz_session_preferences"
        const val SESSION_KEY = "active_quiz_session"
    }
}
