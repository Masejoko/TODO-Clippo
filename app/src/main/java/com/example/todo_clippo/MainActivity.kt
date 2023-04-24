package com.example.todo_clippo

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_clippo.Adapter.ToDoAdapter
import com.example.todo_clippo.utils.AppDatabase
import com.example.todo_clippo.utils.TaskViewModel
import com.example.todo_clippo.utils.ToDoDao

/**
 * MainActivity is the main activity of the application, responsible for displaying the list of tasks,
 * handling the addition of new tasks and managing the user interactions.
 */
class MainActivity : AppCompatActivity(), DialogCloseListener {

    // Variables for the database, DAO, ViewModel, and adapter
    private lateinit var appDatabase: AppDatabase
    internal val toDoDao: ToDoDao by lazy { appDatabase.toDoDao() }
    private val viewModel: TaskViewModel by viewModels { TaskViewModelFactory(toDoDao) }
    private lateinit var adapter: ToDoAdapter

    /**
     * Called when the activity is starting, responsible for initializing the views and setting up the RecyclerView.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the database
        appDatabase = AppDatabase.getInstance(this)

        // Set up the RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.tasksRecyclerView)
        adapter = ToDoAdapter(viewModel, this, supportFragmentManager)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set up the item touch helper for swiping functionality
        val itemTouchHelper = ItemTouchHelper(RecyclerItemTouchHelper(adapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)

        // Observe the tasks LiveData and update the adapter when it changes
        viewModel.tasks.observe(this) { tasks ->
            tasks?.let {
                adapter.submitList(it)
            }
        }

        // Set up the click listener for the floating action button
        findViewById<View>(R.id.fab).setOnClickListener {
            val addNewTask = AddNewTask()
            addNewTask.show(supportFragmentManager, AddNewTask.TAG)
        }
    }

    /**
     * Handles the closing of the dialog after adding a new task or editing an existing one.
     */
    override fun handleDialogClose() {
        // No additional action needed here, as the LiveData observer will automatically update the adapter
    }
}