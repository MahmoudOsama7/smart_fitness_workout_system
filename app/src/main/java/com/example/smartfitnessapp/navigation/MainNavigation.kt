package com.example.smartfitnessapp.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.presentation.dynamicSettings.DYNAMIC_SETTINGS_VIEW
import com.example.presentation.dynamicSettings.DynamicSettingsViewNavigation
import com.example.presentation.workOutHistory.WORKOUT_HISTORY_VIEW
import com.example.presentation.workOutHistory.WorkoutHistoryViewNavigation
import com.example.presentation.workOutView.WORKOUT_VIEW
import com.example.presentation.workOutView.WorkoutViewNavigation

const val MAIN_ROUTE = "main_route"

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = MAIN_ROUTE
        ) {
            navigation(
                startDestination = WORKOUT_VIEW,
                route = MAIN_ROUTE
            ) {
                composable(route = WORKOUT_VIEW) {
                    WorkoutViewNavigation(
                        onNavigateToWorkoutHistory = {
                            navController.navigate(route = WORKOUT_HISTORY_VIEW)
                        },
                        onNavigateToDynamicSettings = {
                            navController.navigate(route = DYNAMIC_SETTINGS_VIEW)
                        }
                    )
                }
                composable(route = WORKOUT_HISTORY_VIEW) {
                    WorkoutHistoryViewNavigation(
                        onBackPressed = navController::navigateUp
                    )
                }
                composable(route = DYNAMIC_SETTINGS_VIEW) {
                    DynamicSettingsViewNavigation(
                        onBackPressed = navController::navigateUp
                    )
                }
            }
        }
    }
}