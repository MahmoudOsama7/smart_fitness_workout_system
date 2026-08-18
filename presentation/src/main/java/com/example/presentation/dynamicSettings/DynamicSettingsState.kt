package com.example.presentation.dynamicSettings

import com.example.domain.model.WeightUnit

data class DynamicSettingsUiState(
    val selectedWeightUnit: WeightUnit = WeightUnit.KG
)

data class DynamicSettingsNavigator(
    val onBackPressed: () -> Unit
)

sealed interface DynamicSettingsAction {

    data class SetWeightUnit(val weightUnit: WeightUnit) : DynamicSettingsAction
    data object GetWeightUnit : DynamicSettingsAction
}
