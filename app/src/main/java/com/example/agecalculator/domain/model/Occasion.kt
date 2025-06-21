package com.example.agecalculator.domain.model

import androidx.room.PrimaryKey

data class Occasion(
    val id: Int?,
    val title: String,
    val dateMillis: Long?,
    val emoji: String
)