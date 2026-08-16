package com.example.presentation.workOutView

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.util.Locale

@Composable
fun WorkoutViewScreen(
    contract: WorkoutViewContract
) {
    val state = contract.state
    val actions = contract.action
    Scaffold(
        topBar = {
            WorkoutTopBar(
                onHistoryClick = {},
                onSettingsClick = {}
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Text(
                text = state.exerciseName,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            WorkoutStateIndicator(
                state = state.workoutState
            )

            Spacer(
                modifier = Modifier.height(32.dp)
            )

            when (state.workoutState) {

                WorkoutStateType.READY -> {
                    ReadyWorkoutContent(
                        state = state,
                        onStartWorkout = {
                            actions(WorkoutAction.StartWorkout)
                        }
                    )
                }

                WorkoutStateType.ACTIVE_SET -> {
                    ActiveSetContent(
                        state = state,
                        onCompleteSet = {
                            actions(WorkoutAction.CompleteSet)
                        },
                        onPauseWorkout = {
                            actions(WorkoutAction.PauseWorkout)
                        }
                    )
                }

                WorkoutStateType.RESTING -> {
                    RestTimerContent(
                        state = state,
                        onSkipRest = {
                            actions(WorkoutAction.SkipRest)
                        }
                    )
                }

                WorkoutStateType.PAUSED -> {
                    PausedWorkoutContent(
                        onResumeWorkout = {
                            actions(WorkoutAction.ResumeWorkout)
                        },
                        onEndWorkout = {
                            actions(WorkoutAction.EndWorkout)
                        }
                    )
                }

                WorkoutStateType.COMPLETED -> {
                    CompletedWorkoutContent(
                        state = state
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WorkoutTopBar(
    onHistoryClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Workout"
            )
        },
        actions = {

            IconButton(
                onClick = onHistoryClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.List,
                    contentDescription = "Workout History"
                )
            }

            IconButton(
                onClick = onSettingsClick
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings"
                )
            }
        }
    )
}

@Composable
private fun WorkoutStateIndicator(
    state: WorkoutStateType
) {
    val text = when (state) {
        WorkoutStateType.READY -> "Ready"
        WorkoutStateType.ACTIVE_SET -> "Active Set"
        WorkoutStateType.RESTING -> "Rest"
        WorkoutStateType.PAUSED -> "Paused"
        WorkoutStateType.COMPLETED -> "Completed"
    }

    Surface(
        shape = RoundedCornerShape(50.dp),
        tonalElevation = 4.dp
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(
                horizontal = 20.dp,
                vertical = 8.dp
            ),
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun ReadyWorkoutContent(
    state: WorkoutUiState,
    onStartWorkout: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Today's Workout",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        SetProgress(
            currentSet = state.currentSet,
            totalSets = state.totalSets
        )

        Spacer(
            modifier = Modifier.height(40.dp)
        )

        Button(
            onClick = onStartWorkout,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Start Routine"
            )
        }
    }
}

@Composable
private fun SetProgress(
    currentSet: Int,
    totalSets: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Set $currentSet of $totalSets",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        LinearProgressIndicator(
            progress = {
                currentSet
                    .toFloat()
                    .div(totalSets)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(8.dp))
        )
    }
}

@Composable
private fun ActiveSetContent(
    state: WorkoutUiState,
    onCompleteSet: () -> Unit,
    onPauseWorkout: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SetProgress(
            currentSet = state.currentSet,
            totalSets = state.totalSets
        )

        Spacer(
            modifier = Modifier.height(40.dp)
        )

        WeightCard(
            weight = state.currentWeight
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        Button(
            onClick = onCompleteSet,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Complete Set")
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedButton(
            onClick = onPauseWorkout,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Pause")
        }
    }
}

@Composable
private fun WeightCard(
    weight: String
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Weight",
                style = MaterialTheme.typography.labelLarge
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = weight,
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun RestTimerContent(
    state: WorkoutUiState,
    onSkipRest: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Rest",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        RestTimer(
            seconds = state.remainingRestSeconds
        )

        Spacer(
            modifier = Modifier.height(40.dp)
        )

        Text(
            text = "Take a break before your next set"
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        OutlinedButton(
            onClick = onSkipRest,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Skip Rest")
        }
    }
}

@Composable
private fun RestTimer(
    seconds: Int
) {
    val progress = seconds
        .coerceIn(0, 60)
        .toFloat() / 60f

    Box(
        modifier = Modifier.size(220.dp),
        contentAlignment = Alignment.Center
    ) {

        CircularProgressIndicator(
            progress = {
                progress
            },
            modifier = Modifier.fillMaxSize(),
            strokeWidth = 12.dp
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "$seconds",
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "seconds"
            )
        }
    }
}

@Composable
private fun PausedWorkoutContent(
    onResumeWorkout: () -> Unit,
    onEndWorkout: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            imageVector = Icons.Default.Lock,
            contentDescription = null,
            modifier = Modifier.size(96.dp)
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = "Workout Paused",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        Button(
            onClick = onResumeWorkout,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Resume")
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedButton(
            onClick = onEndWorkout,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("End Workout")
        }
    }
}

@Composable
private fun CompletedWorkoutContent(
    state: WorkoutUiState
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            modifier = Modifier.size(96.dp)
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = "Workout Completed!",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        WorkoutSummaryCard(
            state = state
        )
    }
}

@Composable
private fun WorkoutSummaryCard(
    state: WorkoutUiState
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            SummaryRow(
                title = "Exercise",
                value = state.exerciseName
            )

            SummaryRow(
                title = "Sets",
                value = "${state.completedSets}/${state.totalSets}"
            )

            SummaryRow(
                title = "Weight",
                value = state.currentWeight
            )

            SummaryRow(
                title = "Duration",
                value = formatDuration(
                    state.elapsedTimeSeconds
                )
            )
        }
    }
}

@Composable
private fun SummaryRow(
    title: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = title
        )

        Text(
            text = value,
            fontWeight = FontWeight.Bold
        )
    }
}

private fun formatDuration(
    seconds: Long
): String {

    val minutes = seconds / 60
    val remainingSeconds = seconds % 60

    return String.format(
        Locale.US,
        "%02d:%02d",
        minutes,
        remainingSeconds
    )
}