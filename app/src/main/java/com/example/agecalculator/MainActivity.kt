package com.example.agecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.agecalculator.presentation.calculator.CalculatorScreen
import com.example.agecalculator.presentation.calculator.CalculatorViewModel
import com.example.agecalculator.ui.theme.AgeCalculatorTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AgeCalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel : CalculatorViewModel = viewModel()
                    val state by viewModel.uiState.collectAsStateWithLifecycle()
                    CalculatorScreen(
                        modifier = Modifier.padding(innerPadding),
                        state = state,
                        onAction = viewModel::onAction
                    )
                }
            }
        }
    }
}

