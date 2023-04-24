package com.example.todo_clippo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todo_clippo.utils.TaskViewModel
import com.example.todo_clippo.utils.ToDoDao

class TaskViewModelFactory(private val toDoDao: ToDoDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(toDoDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
