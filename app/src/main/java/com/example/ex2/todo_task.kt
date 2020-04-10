package com.example.ex2

import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView


data class TodoTask(
    val taskString: String,
    var complete: Boolean
)


class TodoTaskHolder(view: View) : RecyclerView.ViewHolder(view) {
    val card: RelativeLayout = view.findViewById(R.id.todo_card)
    val taskText: TextView = view.findViewById(R.id.todo_text)
    val taskComplete: CheckBox = view.findViewById(R.id.todo_check)
}

class TodoTaskAdapter : RecyclerView.Adapter<TodoTaskHolder>() {
    private val _todoTasks: MutableList<TodoTask> = ArrayList()


    fun addTask(task: String) {
        _todoTasks.add(TodoTask(task, false))
        notifyItemInserted(_todoTasks.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoTaskHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_one, parent, false)

        Log.i("TodoLogger", _todoTasks.size.toString())

        return TodoTaskHolder(view)
    }

    override fun getItemCount(): Int {
        return _todoTasks.size
    }

    override fun onBindViewHolder(holder: TodoTaskHolder, position: Int) {
        val task = _todoTasks[position]
        holder.taskText.text = task.taskString
        holder.taskComplete.isChecked = _todoTasks[position].complete

        val popMsg = Toast.makeText(
            holder.itemView.context,
            "TODO " + holder.taskText.text + " is now DONE. BOOM!",
            Toast.LENGTH_SHORT
        )

        holder.card.setOnClickListener {
            if (!holder.taskComplete.isChecked) {
                popMsg.show()
                holder.taskComplete.isChecked = true
                _todoTasks[position].complete = true
            }
        }

        holder.card.setOnLongClickListener {
            val builder =
                AlertDialog.Builder(holder.itemView.context).setTitle("Are You Sure to delete?")
            builder.setPositiveButton("I'm sure") { _, _ ->
                _todoTasks.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, _todoTasks.size);
            }
            builder.setNegativeButton("Nope") { _, _ -> }
            builder.show()
            true
        }

    }
}

