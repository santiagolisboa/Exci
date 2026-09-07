package com.examen.civique.data.repository

import com.examen.civique.data.local.dao.FavoriteQuestionDao
import com.examen.civique.data.local.entity.FavoriteQuestionEntity
import com.examen.civique.domain.repository.FavoriteQuestionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AndroidFavoriteQuestionRepository(
    private val dao: FavoriteQuestionDao
) : FavoriteQuestionRepository {
    override fun observeFavoriteIds(): Flow<Set<String>> =
        dao.observeIds().map { it.toSet() }

    override suspend fun setFavorite(questionId: String, favorite: Boolean) {
        if (favorite) dao.insert(FavoriteQuestionEntity(questionId, System.currentTimeMillis()))
        else dao.delete(questionId)
    }
}
