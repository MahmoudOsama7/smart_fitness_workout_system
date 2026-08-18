package com.example.presentation.dynamicSettings

data class DynamicSettingsContract(
    val state: DynamicSettingsUiState,
    val navigator: DynamicSettingsNavigator,
    val action: (DynamicSettingsAction) -> Unit
)