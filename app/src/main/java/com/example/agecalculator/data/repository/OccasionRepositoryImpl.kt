package com.example.agecalculator.data.repository

import com.example.agecalculator.data.local.OccasionDao
import com.example.agecalculator.data.mapper.toDomain
import com.example.agecalculator.data.mapper.toEntity
import com.example.agecalculator.domain.model.Occasion
import com.example.agecalculator.domain.repository.OccasionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OccasionRepositoryImpl(
    private val dao: OccasionDao
): OccasionRepository {

    override suspend fun upsertOccasion(occasion: Occasion) {
       dao.upsertOccasion(occasion.toEntity())
    }

    override suspend fun deleteOccasion(occasionId: Int) {
       dao.deleteOccasion(occasionId)
    }

    override fun getAllOccasions(): Flow<List<Occasion>> {
        return dao.getAllOccasions().map { occasionEntities ->
            if (occasionEntities.isNotEmpty()){
                occasionEntities.map { it.toDomain() }
            } else {
                val default = Occasion(
                    id = null,
                    title = "Birthday",
                    dateMillis = 0L,
                    emoji = "🎂"
                )
                dao.upsertOccasion(default.toEntity())
                listOf(default)
            }

        }
    }

    override suspend fun getOccasionById(occasionId: Int): Occasion? {
       return dao.getOccasionById(occasionId)?.toDomain()
    }
}