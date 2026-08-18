package com.example.presentation.dynamicSettings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

const val DYNAMIC_SETTINGS_VIEW = "dynamic_settings_view"

@Composable
fun DynamicSettingsViewNavigation(
    onBackPressed: () -> Unit
) {
    val viewModel: DynamicSettingsViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val action = viewModel::onAction

    DynamicSettingsScreen(
        contract = DynamicSettingsContract(
            state = state,
            navigator = DynamicSettingsNavigator(
                onBackPressed = onBackPressed
            ),
            action = action
        )
    )
}