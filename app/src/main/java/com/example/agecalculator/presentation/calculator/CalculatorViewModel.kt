package com.example.agecalculator.presentation.calculator

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.toRoute
import com.example.agecalculator.domain.model.Occasion
import com.example.agecalculator.domain.repository.OccasionRepository
import com.example.agecalculator.presentation.navigation.Route
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.periodUntil
import kotlinx.datetime.until

class CalculatorViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: OccasionRepository
) : ViewModel(){

    val occasionId = savedStateHandle.toRoute<Route.CalculatorScreen>().id

    private val _uistate = MutableStateFlow(CalculatorUiState())
    val uiState: StateFlow<CalculatorUiState> = _uistate.asStateFlow()

    private val _event = Channel<CalculatorEvent>()
    val event = _event.receiveAsFlow()

    init {
        getOccasion()
    }

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
                    )
                }
            }

            CalculatorAction.DismissDatePicker -> {
                _uistate.update { it.copy(isDatePickerDialogOpen = false) }
            }

            is CalculatorAction.DateSelected -> {
                _uistate.update { it.copy(isDatePickerDialogOpen = false) }
                when (uiState.value.activeDateField) {
                    DateField.FROM -> _uistate.update { it.copy(fromDateMillis = action.millis) }
                    DateField.To -> _uistate.update { it.copy(toDateMillis = action.millis) }
                }
                calculateStats()
            }

            is CalculatorAction.SetTitle -> {
                _uistate.update { it.copy(title = action.title) }
            }

            CalculatorAction.SaveOccasion -> {
                saveOccasion()
            }

            CalculatorAction.DeleteOccasion -> {
                deleteOccasion()
            }
        }
    }

    private fun saveOccasion() {
        viewModelScope.launch {
            val occasion = Occasion(
                id = occasionId,
                dateMillis = _uistate.value.fromDateMillis,
                emoji = _uistate.value.emoji,
                title = _uistate.value.title
            )
            repository.upsertOccasion(occasion)
            _event.send(CalculatorEvent.ShowToast("Saved Successfully"))
            _event.send(CalculatorEvent.NavigateToDashboardScreen)
        }
    }

    private fun getOccasion() {
        if (occasionId == null) return
        viewModelScope.launch {
            repository.getOccasionById(occasionId)?.let { occasion ->
                _uistate.update {
                    it.copy(
                        fromDateMillis = occasion.dateMillis,
                        emoji = occasion.emoji,
                        title = occasion.title,
                        occasionId = occasion.id
                    )
                }
            }
            calculateStats()
        }
    }

    private fun deleteOccasion() {
        if(occasionId == null ) return
        viewModelScope.launch {
            repository.deleteOccasion(occasionId)
            _event.send(CalculatorEvent.ShowToast("Deleted Successfully"))
            _event.send(CalculatorEvent.NavigateToDashboardScreen)
        }
    }

    private fun calculateStats() {
        val timeZone = TimeZone.currentSystemDefault()
        val fromMillis = _uistate.value.fromDateMillis ?: System.currentTimeMillis()
        val toMillis = _uistate.value.toDateMillis ?: System.currentTimeMillis()

        val fromInstant = Instant.fromEpochMilliseconds(fromMillis)
        val toInstant = Instant.fromEpochMilliseconds(toMillis)

        val period = fromInstant.periodUntil(toInstant, timeZone)
        val diffInMonths = fromInstant.until(toInstant, DateTimeUnit.MONTH, timeZone)
        val diffInWeeks = fromInstant.until(toInstant, DateTimeUnit.WEEK, timeZone)
        val diffInDays = fromInstant.until(toInstant, DateTimeUnit.DAY, timeZone)
        val diffInHours = fromInstant.until(toInstant, DateTimeUnit.HOUR, timeZone)
        val diffInMinutes = fromInstant.until(toInstant, DateTimeUnit.MINUTE, timeZone)
        val diffInSeconds = fromInstant.until(toInstant, DateTimeUnit.SECOND, timeZone)

        _uistate.update {
            it.copy(
                period = period,
                ageStats = AgeStats(
                    years = period.years,
                    months = diffInMonths.toInt(),
                    weeks = diffInWeeks.toInt(),
                    days = diffInDays.toInt(),
                    hours = diffInHours.toInt(),
                    minutes = diffInMinutes.toInt(),
                    seconds = diffInSeconds.toInt()
                )
            )
        }
    }
}















