package com.examen.civique.domain.repository

import kotlinx.coroutines.flow.Flow

interface FavoriteQuestionRepository {
    fun observeFavoriteIds(): Flow<Set<String>>
    suspend fun setFavorite(questionId: String, favorite: Boolean)
}
