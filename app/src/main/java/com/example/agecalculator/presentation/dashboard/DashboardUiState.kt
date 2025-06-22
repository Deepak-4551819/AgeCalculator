package com.example.agecalculator.presentation.dashboard

import com.example.agecalculator.domain.model.Occasion

data class DashboardUiState(
    val isDatePickerDialogOpen: Boolean = false,
    val occasions: List<Occasion> = emptyList(),
    val selectedOccasion: Occasion? = null
)
