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

@Database(
    entities = [
        QuizResultEntity::class,
        CourseProgressEntity::class,
        FavoriteQuestionEntity::class,
        QuestionErrorEntity::class,
        AnswerAttemptEntity::class
    ],
    version = 5,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun quizResultDao(): QuizResultDao

    abstract fun courseProgressDao(): CourseProgressDao
    abstract fun favoriteQuestionDao(): FavoriteQuestionDao
    abstract fun learningDao(): LearningDao
}
