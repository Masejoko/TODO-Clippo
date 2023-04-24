package com.example.todo_clippo

import android.app.AlertDialog
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.todo_clippo.Adapter.ToDoAdapter

/**
 * RecyclerItemTouchHelper is a custom ItemTouchHelper.SimpleCallback class that handles swipe
 * gestures on RecyclerView items and manages swipe interactions for the ToDoAdapter.
 *
 * @param adapter The ToDoAdapter instance that manages the RecyclerView items.
 */
class RecyclerItemTouchHelper(private val adapter: ToDoAdapter) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    /**
     * This method is not used in this implementation, but it's required by the parent class.
     */
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: ViewHolder,
        target: ViewHolder
    ): Boolean {
        return false
    }

    /**
     * Handles swipe gestures on RecyclerView items and performs actions based on swipe direction.
     */
    override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
        val position = viewHolder.bindingAdapterPosition // Use bindingAdapterPosition instead
        val context = viewHolder.itemView.context // Get context from the itemView

        if (direction == ItemTouchHelper.LEFT) {
            AlertDialog.Builder(context)
                .setTitle("Delete Task")
                .setMessage("Are you sure you want to delete this Task?")
                .setPositiveButton("Confirm") { _, _ ->
                    adapter.deleteItem(position)
                }
                .setNegativeButton(android.R.string.cancel) { _, _ ->
                    adapter.notifyItemChanged(viewHolder.bindingAdapterPosition) // Use bindingAdapterPosition instead
                }
                .create()
                .show()
        } else {
            adapter.editItem(position)
        }
    }

    /**
     * Customizes the appearance of the RecyclerView items during swipe gestures.
     */
    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        // Call the superclass method to handle the default drawing behavior
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        // Declare variables for the icon and background color
        val icon: Drawable?
        val background: ColorDrawable

        // Get the itemView and its context
        val itemView: View = viewHolder.itemView
        val context = viewHolder.itemView.context
        val backgroundCornerOffset = 20

        // Determine the icon and background color based on swipe direction
        if (dX > 0) {
            icon = ContextCompat.getDrawable(context, R.drawable.baseline_edit)
            background = ColorDrawable(ContextCompat.getColor(context, R.color.colorPrimaryDark))
        } else {
            icon = ContextCompat.getDrawable(context, R.drawable.baseline_delete_24)
            background = ColorDrawable(Color.RED)
        }

        // Draw the icon and background if the icon is not null
        icon?.let {
            // Calculate icon dimensions and position
            val iconMargin = (itemView.height - it.intrinsicHeight) / 2
            val iconTop = itemView.top + (itemView.height - it.intrinsicHeight) / 2
            val iconBottom = iconTop + it.intrinsicHeight

            // Set the icon bounds and background bounds based on swipe direction
            if (dX > 0) { // Swiping to the right (edit action)
                val iconLeft = itemView.left + iconMargin
                val iconRight = itemView.left + iconMargin + it.intrinsicWidth
                it.setBounds(iconLeft, iconTop, iconRight, iconBottom)

                background.setBounds(itemView.left, itemView.top,
                    itemView.left + dX.toInt() + backgroundCornerOffset, itemView.bottom)
            } else if (dX < 0) { // Swiping to the left (delete action)
                val iconLeft = itemView.right - iconMargin - it.intrinsicWidth
                val iconRight = itemView.right - iconMargin
                it.setBounds(iconLeft, iconTop, iconRight, iconBottom)

                background.setBounds(itemView.right + dX.toInt() - backgroundCornerOffset,
                    itemView.top, itemView.right, itemView.bottom)
            } else { // View is unSwiped (reset background bounds)
                background.setBounds(0, 0, 0, 0)
            }

            // Draw the background and icon on the canvas
            background.draw(c)
            it.draw(c)
        }
    }
}

