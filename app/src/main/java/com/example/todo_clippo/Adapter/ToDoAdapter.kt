package com.example.todo_clippo.Adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_clippo.AddNewTask
import com.example.todo_clippo.R
import com.example.todo_clippo.model.ToDoModel
import com.example.todo_clippo.utils.TaskViewModel

/**
 * ToDoAdapter is a custom RecyclerView adapter for displaying ToDo items, managing their interactions,
 * and providing the ability to edit and delete items.
 *
 * @param viewModel The TaskViewModel instance responsible for managing the tasks.
 * @param context The context in which the adapter is being used.
 * @param fragmentManager The FragmentManager instance for managing fragments.
 */
class ToDoAdapter(
    private val viewModel: TaskViewModel,
    private val context: Context,
    private val fragmentManager: FragmentManager
) : ListAdapter<ToDoModel, ToDoAdapter.ViewHolder>(ToDoModelDiffCallback()) {

    /**
     * Creates a new ViewHolder for the ToDo items.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_layout, parent, false)
        return ViewHolder(itemView)
    }

    /**
     * Binds the data from the ToDoModel to the ViewHolder.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.task.text = item.task
        holder.task.isChecked = item.status != 0
        holder.task.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateStatus(item.id, if (isChecked) 1 else 0)
        }
    }

    /**
     * Deletes the ToDo item at the specified position.
     */
    fun deleteItem(position: Int) {
        val item = getItem(position)
        viewModel.deleteTask(item)
    }

    /**
     * Opens the AddNewTask fragment to edit the ToDo item at the specified position.
     */
    fun editItem(position: Int) {
        val item = getItem(position)
        val bundle = Bundle()
        bundle.putInt("id", item.id)
        bundle.putString("task", item.task)
        val fragment = AddNewTask()
        fragment.arguments = bundle
        fragment.show(fragmentManager, AddNewTask.TAG)
    }

    /**
     * ViewHolder class for holding and displaying the ToDo items.
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val task: CheckBox = view.findViewById(R.id.todoCheckBox)
    }

    /**
     * ToDoModelDiffCallback is a custom DiffUtil.ItemCallback class for efficiently determining
     * the difference between two ToDoModel instances.
     */
    class ToDoModelDiffCallback : DiffUtil.ItemCallback<ToDoModel>() {
        override fun areItemsTheSame(oldItem: ToDoModel, newItem: ToDoModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ToDoModel, newItem: ToDoModel): Boolean {
            return oldItem == newItem
        }
    }
}


