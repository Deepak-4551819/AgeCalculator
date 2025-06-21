package com.example.agecalculator.domain.repository

import com.example.agecalculator.domain.model.Occasion
import kotlinx.coroutines.flow.Flow

interface OccasionRepository {
    suspend fun upsertOccasion(occasion: Occasion)
    suspend fun deleteOccasion(occasionId: Int)
    fun getAllOccasions(): Flow<List<Occasion>>
    suspend fun getOccasionById(occasionId: Int): Occasion?

}