package com.cheise_proj.e_commerce.data.repository

import com.cheise_proj.e_commerce.data.db.entity.FavoriteEntity
import com.cheise_proj.e_commerce.data.db.entity.ProductWithFavorite
import kotlinx.coroutines.flow.Flow

interface IFavoriteRepository {
    suspend fun addFavorite(favoriteEntity: FavoriteEntity)
    fun getProductFavorite(): Flow<List<ProductWithFavorite>>
    suspend fun removeFavorite(identifier: Int?)
    suspend fun getFavorites(): Flow<List<FavoriteEntity>>
}