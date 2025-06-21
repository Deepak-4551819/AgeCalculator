package com.example.agecalculator.data.mapper

import com.example.agecalculator.data.local.OccasionEntity
import com.example.agecalculator.domain.model.Occasion

fun OccasionEntity.toDomain() = Occasion(
    id = id,
    title = title,
    dateMillis = dateMillis,
    emoji = emoji
)

fun Occasion.toEntity() = OccasionEntity(
    id = id ?: 0,
    title = title,
    dateMillis = dateMillis,
    emoji = emoji
)


