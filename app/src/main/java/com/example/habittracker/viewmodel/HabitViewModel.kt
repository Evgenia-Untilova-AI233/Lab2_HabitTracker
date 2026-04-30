package com.example.habittracker.viewmodel

import androidx.lifecycle.ViewModel
import com.example.habittracker.model.Habit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

class HabitViewModel : ViewModel() {

    private val initialHabits = listOf(
        Habit(1, "Пробіжка"),
        Habit(2, "Випити 2л води"),
        Habit(3, "Читання 20 хв"),
        Habit(4, "Медитація"),
        Habit(5, "Щоденник"),
        Habit(6, "Гітара 15 хв")
    )

    private val _habits = MutableStateFlow(initialHabits)
    val habits: StateFlow<List<Habit>> = _habits

    val completedCount = _habits.map { list ->
        list.count { it.isDone }
    }

    fun toggleHabit(id: Int) {
        _habits.value = _habits.value.map {
            if (it.id == id) it.copy(isDone = !it.isDone)
            else it
        }
    }

    fun resetHabits() {
        _habits.value = _habits.value.map {
            it.copy(isDone = false)
        }
    }
}