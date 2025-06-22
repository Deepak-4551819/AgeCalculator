package com.example.agecalculator.presentation.dashboard

import com.example.agecalculator.domain.model.Occasion

sealed interface DashboardAction {
    data object DismissDatePicker : DashboardAction
    data class ShowDatePicker(val occasion: Occasion) : DashboardAction
    data class DateSelected(val millis: Long?) : DashboardAction
}