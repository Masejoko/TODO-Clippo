package com.example.todo_clippo

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.example.todo_clippo.model.ToDoModel
import com.example.todo_clippo.utils.TaskViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * AddNewTask is a BottomSheetDialogFragment for adding or updating tasks.
 */
class AddNewTask : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "ActionBottomDialog"

//        /**
//         * Creates a new instance of the AddNewTask fragment.
//         * @return A new instance of AddNewTask.
//         */
//        fun newInstance(): AddNewTask {
//            return AddNewTask()
//        }
    }

    private lateinit var newTaskText: EditText
    private lateinit var newTaskSaveButton: Button
    private val viewModel: TaskViewModel by viewModels { TaskViewModelFactory((activity as MainActivity).toDoDao) }

    private var isUpdate = false
    private var taskId = -1

    /**
     * Called when the fragment is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogStyle)
    }

    /**
     * Called to create the view hierarchy associated with the fragment.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.new_task, container, false)
        dialog?.window?.let { window ->
            WindowCompat.setDecorFitsSystemWindows(window, false)
        }
        return view
    }

    /**
     * Called immediately after onCreateView has returned, but before any saved state has been restored.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newTaskText = view.findViewById(R.id.newTaskText)
        newTaskSaveButton = view.findViewById(R.id.newTaskButton)

        // Check if the bundle has data (update scenario)
        val bundle = arguments
        if (bundle != null) {
            isUpdate = true
            val task = bundle.getString("task")
            taskId = bundle.getInt("id")
            newTaskText.setText(task)

            if (task?.isNotEmpty() == true) {
                newTaskSaveButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark))
            }
        }

        // Enable/disable the save button based on the input text
        newTaskText.addTextChangedListener {
            if (it.toString().isEmpty()) {
                newTaskSaveButton.isEnabled = false
                newTaskSaveButton.setTextColor(Color.GRAY)
            } else {
                newTaskSaveButton.isEnabled = true
                newTaskSaveButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark))
            }
        }

        // Save button click listener
        newTaskSaveButton.setOnClickListener {
            val text = newTaskText.text.toString()
            if (isUpdate) {
                viewModel.updateTask(ToDoModel(taskId, text, if (newTaskText.text.isNotEmpty()) 1 else 0))
            } else {
                viewModel.insertTask(ToDoModel(0, text, 0))
            }
            dismiss()
        }
    }

    /**
     * Notify the MainActivity about the dialog dismissal to refresh the tasks.
     */
    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        (activity as? MainActivity)?.handleDialogClose()
    }
}



