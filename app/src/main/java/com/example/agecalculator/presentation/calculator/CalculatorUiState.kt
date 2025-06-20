package com.example.agecalculator.presentation.calculator

data class CalculatorUiState(
    val emoji: String = "🎂",
    val fromDateMillis: Long? = null,
    val toDateMillis: Long? = null,
    val isEmojiDialogOpen: Boolean = false,
    val isDatePickerDialogOpen: Boolean = false,
    val activeDateField: DateField = DateField.FROM

)

enum class DateField {
    FROM,
    To
}