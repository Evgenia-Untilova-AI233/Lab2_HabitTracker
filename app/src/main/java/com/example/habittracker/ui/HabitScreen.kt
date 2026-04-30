package com.example.habittracker.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habittracker.viewmodel.HabitViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitScreen(viewModel: HabitViewModel = viewModel()) {

    val habits by viewModel.habits.collectAsState()
    val completed by viewModel.completedCount.collectAsState(initial = 0)

    val progress = if (habits.isNotEmpty())
        completed.toFloat() / habits.size
    else 0f

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primaryContainer,
                        MaterialTheme.colorScheme.background
                    )
                )
            )
    ) {

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text("Трекер звичок")

                            Text(
                                "Виконано: $completed з ${habits.size}",
                                style = MaterialTheme.typography.bodySmall
                            )

                            LinearProgressIndicator(
                                progress = progress,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 4.dp)
                            )
                        }
                    }
                )
            },
            bottomBar = {
                Button(
                    onClick = { viewModel.resetHabits() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Скинути день")
                }
            }
        ) { padding ->

            LazyColumn(
                contentPadding = padding,
                modifier = Modifier.fillMaxSize()
            ) {
                items(habits) { habit ->
                    HabitCard(
                        habit = habit,
                        onClick = { viewModel.toggleHabit(habit.id) }
                    )
                }
            }
        }
    }
}