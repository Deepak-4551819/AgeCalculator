package com.example.agecalculator.presentation.navigation

import kotlinx.serialization.Serializable


//We are using the serializable annotation because under the hood this compose navigation library use this serialization library in parsing these navigation argument
@Serializable
sealed interface Route {
    @Serializable
    data object DashboardScreen: Route
    @Serializable
    data class CalculatorScreen(val id: Int?) : Route
}