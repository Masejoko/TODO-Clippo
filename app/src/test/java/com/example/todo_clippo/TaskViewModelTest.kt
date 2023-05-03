package com.example.todo_clippo

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.todo_clippo.model.ToDoModel
import com.example.todo_clippo.utils.TaskViewModel
import com.example.todo_clippo.utils.ToDoDao
import kotlinx.coroutines.test.runTest

@ExperimentalCoroutinesApi
class TaskViewModelTest {

    @Mock
    private lateinit var toDoDao: ToDoDao

    @Mock
    private lateinit var tasksObserver: Observer<List<ToDoModel>>

    private lateinit var viewModel: TaskViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        `when`(toDoDao.getAllTasks()).thenReturn(MutableLiveData(emptyList()))
        viewModel = TaskViewModel(toDoDao)

        ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
            override fun executeOnDiskIO(runnable: Runnable) {
                runnable.run()
            }

            override fun postToMainThread(runnable: Runnable) {
                runnable.run()
            }

            override fun isMainThread(): Boolean {
                return true
            }
        })
    }

    @Test
    fun insertTaskTest() = runTest{
            val task = ToDoModel(0, "Sample Task", 0)
            viewModel.insertTask(task)
            verify(toDoDao).insertTask(task)
    }

    @Test
    fun updateTaskTest() = runTest{
            val originalTask = ToDoModel(1, "Original Task", 0)
            val updatedTask = ToDoModel(1, "Updated Task", 0)

            // Insert the original task first
            viewModel.insertTask(originalTask)
            verify(toDoDao).insertTask(originalTask)

            // Update the task
            viewModel.updateTask(updatedTask)
            verify(toDoDao).updateTask(updatedTask)
    }

    @Test
    fun deleteTaskTest() = runTest{
            val task = ToDoModel(0, "Sample Task", 0)

            // Insert task first
            viewModel.insertTask(task)
            verify(toDoDao).insertTask(task)

            // delete task
            viewModel.deleteTask(task)
            verify(toDoDao).deleteTask(task)
    }

    @Test
    fun updateStatusTest() = runTest{
            val task = ToDoModel(1, "Original Task", 0)

            // Insert the original task first
            viewModel.insertTask(task)
            verify(toDoDao).insertTask(task)

            // Update the task
            viewModel.updateStatus(1, 2)
            verify(toDoDao).updateStatus(1, 2)
    }

    @Test
    fun tasksLiveDataTest() {
        val emptyList = emptyList<ToDoModel>()
        `when`(toDoDao.getAllTasks()).thenReturn(MutableLiveData(emptyList))

        viewModel.tasks.observeForever(tasksObserver)
        verify(tasksObserver).onChanged(emptyList)
    }
}
