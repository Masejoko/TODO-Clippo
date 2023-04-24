package com.example.todo_clippo.utils

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todo_clippo.model.ToDoModel

/**
 * Data Access Object (DAO) for the ToDoModel.
 *
 * This interface defines the methods for accessing and manipulating the ToDoModel data.
 */
@Dao
interface ToDoDao {

    /**
     * Retrieves all tasks from the database.
     *
     * @return A LiveData list of ToDoModel objects.
     */
    @Query("SELECT * FROM todo")
    fun getAllTasks(): LiveData<List<ToDoModel>>

    /**
     * Inserts a new task into the database.
     *
     * @param task The ToDoModel object to be inserted.
     */
    @Insert
    fun insertTask(task: ToDoModel)

    /**
     * Updates an existing task in the database.
     *
     * @param task The ToDoModel object to be updated.
     */
    @Update
    fun updateTask(task: ToDoModel)

    /**
     * Deletes a task from the database.
     *
     * @param toDoModel The ToDoModel object to be deleted.
     * @return The number of rows affected.
     */
    @Delete
    fun deleteTask(toDoModel: ToDoModel): Int

    /**
     * Updates the status of a task in the database.
     *
     * @param id The ID of the task to be updated.
     * @param status The new status to be set.
     * @return The number of rows affected.
     */
    @Query("UPDATE todo SET status = :status WHERE id = :id")
    fun updateStatus(id: Int, status: Int): Int
}