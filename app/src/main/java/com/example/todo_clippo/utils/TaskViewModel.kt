package com.example.todo_clippo.utils

import androidx.lifecycle.*
import com.example.todo_clippo.model.ToDoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ViewModel class for managing tasks.
 *
 * @property toDoDao The Data Access Object for the ToDoModel.
 */
class TaskViewModel(private val toDoDao: ToDoDao) : ViewModel() {

    // LiveData object for observing the list of tasks.
    val tasks: LiveData<List<ToDoModel>> = toDoDao.getAllTasks()

    /**
     * Inserts a new task into the database.
     *
     * @param task The task to be inserted.
     */
    fun insertTask(task: ToDoModel) {
        viewModelScope.launch(Dispatchers.IO) {
            toDoDao.insertTask(task)
        }
    }

    /**
     * Updates an existing task in the database.
     *
     * @param task The task to be updated.
     */
    fun updateTask(task: ToDoModel) {
        viewModelScope.launch(Dispatchers.IO) {
            toDoDao.updateTask(task)
        }
    }

    /**
     * Deletes a task from the database.
     *
     * @param task The task to be deleted.
     */
    fun deleteTask(task: ToDoModel) {
        viewModelScope.launch(Dispatchers.IO) {
            toDoDao.deleteTask(task)
        }
    }

    /**
     * Updates the status of a task in the database.
     *
     * @param id The ID of the task to be updated.
     * @param status The new status of the task.
     */
    fun updateStatus(id: Int, status: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            toDoDao.updateStatus(id, status)
        }
    }
}
