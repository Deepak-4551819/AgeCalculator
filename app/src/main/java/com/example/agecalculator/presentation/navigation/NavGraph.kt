package com.example.agecalculator.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.agecalculator.presentation.calculator.CalculatorScreen
import com.example.agecalculator.presentation.calculator.CalculatorViewModel
import com.example.agecalculator.presentation.dashboard.DashboardScreen
import com.example.agecalculator.presentation.dashboard.DashboardViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavGraph(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        startDestination = Route.DashboardScreen,
        navController = navController
    ){
        composable<Route.DashboardScreen>{
            val viewModel : DashboardViewModel = koinViewModel()
            val state by viewModel.uiState.collectAsStateWithLifecycle()
            DashboardScreen(
                state = state,
                onAction = viewModel::onAction,
                navigateToCalculatorScreen = { occasionId ->
                    navController.navigate(Route.CalculatorScreen(occasionId))
                }
            )
        }
        composable<Route.CalculatorScreen>{
            val viewModel : CalculatorViewModel = koinViewModel()
            val state by viewModel.uiState.collectAsStateWithLifecycle()
            CalculatorScreen(
                state = state,
                onAction = viewModel::onAction,
                navigateUp = {
                    navController.navigateUp()
                }
            )
        }
    }

}