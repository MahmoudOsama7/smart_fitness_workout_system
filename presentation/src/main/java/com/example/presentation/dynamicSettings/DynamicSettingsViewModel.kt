package com.example.presentation.dynamicSettings

import androidx.lifecycle.ViewModel
import com.example.domain.model.WeightUnit
import com.example.domain.usecase.GetWeightUnitUseCase
import com.example.domain.usecase.SetWeightUnitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DynamicSettingsViewModel @Inject constructor(
    private val getWeightUnitUseCase: GetWeightUnitUseCase,
    private val setWeightUnitUseCase: SetWeightUnitUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DynamicSettingsUiState())
    val uiState: StateFlow<DynamicSettingsUiState> = _uiState.asStateFlow()

    fun onAction(action: DynamicSettingsAction) {
        when (action) {
            DynamicSettingsAction.GetWeightUnit -> loadSavedWeightUnit()
            is DynamicSettingsAction.SetWeightUnit -> onUnitSelected(action.weightUnit)
        }
    }

    private fun loadSavedWeightUnit() {
        val currentUnit = getWeightUnitUseCase()
        _uiState.update { currentState ->
            currentState.copy(selectedWeightUnit = currentUnit)
        }
    }

    fun onUnitSelected(unit: WeightUnit) {
        setWeightUnitUseCase(unit)
        _uiState.update { currentState ->
            currentState.copy(selectedWeightUnit = unit)
        }
    }
}