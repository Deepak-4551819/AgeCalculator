package com.example.agecalculator.presentation.calculator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CalculatorViewModel : ViewModel() {

    private val _uistate = MutableStateFlow(CalculatorUiState())
    val uiState: StateFlow<CalculatorUiState> = _uistate.asStateFlow()


    fun onAction(action: CalculatorAction) {
        when (action) {
            CalculatorAction.ShowEmojiPicker -> {
                _uistate.update { it.copy(isEmojiDialogOpen = true) }
            }

            CalculatorAction.DismissEmojiPicker -> {
                _uistate.update { it.copy(isEmojiDialogOpen = false) }
            }

            is CalculatorAction.EmojiSelected -> {
                _uistate.update {
                    it.copy(
                        isEmojiDialogOpen = false,
                        emoji = action.emoji
                    )
                }
            }

            is CalculatorAction.ShowDatePicker -> {
                _uistate.update {
                    it.copy(
                        isDatePickerDialogOpen = true,
                        activeDateField = action.dateField
                    ) }
            }
            CalculatorAction.DismissDatePicker ->{
                _uistate.update { it.copy(isDatePickerDialogOpen = false) }
            }
            is CalculatorAction.DateSelected -> {
                _uistate.update { it.copy(isDatePickerDialogOpen = false) }
                when (uiState.value.activeDateField) {
                    DateField.FROM -> _uistate.update { it.copy(fromDateMillis = action.millis) }
                    DateField.To -> _uistate.update { it.copy(toDateMillis = action.millis) }
                }
            }
        }
    }
}