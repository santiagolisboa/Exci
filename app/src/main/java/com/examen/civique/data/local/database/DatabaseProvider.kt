package com.examen.civique.data.local.database

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DatabaseProvider {

    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {

        return INSTANCE ?: synchronized(this) {

            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "examen_civique_database"
            )
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5)
                .build()

            INSTANCE = instance

            instance
        }
    }

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                """
                CREATE TABLE IF NOT EXISTS course_progress (
                    courseId INTEGER NOT NULL PRIMARY KEY,
                    completed INTEGER NOT NULL,
                    completedAt INTEGER
                )
                """.trimIndent()
            )
        }
    }

    private val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                "ALTER TABLE course_progress ADD COLUMN completedLessonIds TEXT NOT NULL DEFAULT ''"
            )
            db.execSQL(
                "ALTER TABLE course_progress ADD COLUMN lastLessonId TEXT"
            )
        }
    }

    private val MIGRATION_3_4 = object : Migration(3, 4) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE quiz_results ADD COLUMN sessionType TEXT NOT NULL DEFAULT 'QUIZ'")
            db.execSQL("CREATE TABLE IF NOT EXISTS favorite_questions (questionId TEXT NOT NULL PRIMARY KEY, addedAt INTEGER NOT NULL)")
            db.execSQL("CREATE TABLE IF NOT EXISTS question_errors (questionId TEXT NOT NULL PRIMARY KEY, errorCount INTEGER NOT NULL, lastWrongAnswer TEXT, lastErrorAt INTEGER NOT NULL, active INTEGER NOT NULL)")
            db.execSQL("CREATE TABLE IF NOT EXISTS answer_records (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, questionId TEXT NOT NULL, category TEXT NOT NULL, selectedAnswer TEXT, correct INTEGER NOT NULL, answeredAt INTEGER NOT NULL)")
        }
    }

    private val MIGRATION_4_5 = object : Migration(4, 5) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE answer_records ADD COLUMN source TEXT NOT NULL DEFAULT 'QUIZ'")
        }
    }
}
