package com.examen.civique.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.examen.civique.data.local.dao.CourseProgressDao
import com.examen.civique.data.local.dao.QuizResultDao
import com.examen.civique.data.local.entity.CourseProgressEntity
import com.examen.civique.data.local.entity.QuizResultEntity

@Database(
    entities = [
        QuizResultEntity::class,
        CourseProgressEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun quizResultDao(): QuizResultDao

    abstract fun courseProgressDao(): CourseProgressDao
}