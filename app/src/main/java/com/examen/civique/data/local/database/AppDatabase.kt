package com.examen.civique.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.examen.civique.data.local.dao.CourseProgressDao
import com.examen.civique.data.local.dao.QuizResultDao
import com.examen.civique.data.local.dao.FavoriteQuestionDao
import com.examen.civique.data.local.dao.LearningDao
import com.examen.civique.data.local.entity.CourseProgressEntity
import com.examen.civique.data.local.entity.QuizResultEntity
import com.examen.civique.data.local.entity.FavoriteQuestionEntity
import com.examen.civique.data.local.entity.QuestionErrorEntity
import com.examen.civique.data.local.entity.AnswerAttemptEntity
import com.examen.civique.data.local.entity.QuestionReportEntity
import com.examen.civique.data.local.dao.QuestionReportDao

@Database(
    entities = [
        QuizResultEntity::class,
        CourseProgressEntity::class,
        FavoriteQuestionEntity::class,
        QuestionErrorEntity::class,
        AnswerAttemptEntity::class,
        QuestionReportEntity::class
    ],
    version = 7,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun quizResultDao(): QuizResultDao

    abstract fun courseProgressDao(): CourseProgressDao
    abstract fun favoriteQuestionDao(): FavoriteQuestionDao
    abstract fun learningDao(): LearningDao
    abstract fun questionReportDao(): QuestionReportDao
}
