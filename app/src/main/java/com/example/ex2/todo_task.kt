package com.example.ex2

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
    val complete: Boolean = false
)


class TodoTaskHolder(view: View) : RecyclerView.ViewHolder(view) {
    val card: RelativeLayout = view.findViewById(R.id.todo_card)
    val taskText: TextView = view.findViewById(R.id.todo_text)
    val taskComplete: CheckBox = view.findViewById(R.id.todo_check)
}

class TodoTaskAdapter : RecyclerView.Adapter<TodoTaskHolder>() {
    private val _todoTasks: MutableList<TodoTask> = ArrayList()


    fun addTask(task: String, complete: Boolean = false) {
        _todoTasks.add(TodoTask(task, complete))
        notifyItemInserted(_todoTasks.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoTaskHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_one, parent, false)
        return TodoTaskHolder(view)
    }

    override fun getItemCount(): Int {
        return _todoTasks.size
    }

    override fun onBindViewHolder(holder: TodoTaskHolder, position: Int) {
        val task = _todoTasks[position]
        holder.taskText.text = task.taskString

        val popMsg = Toast.makeText(
            holder.itemView.context,
            "TODO " + holder.taskText.text + " is now DONE. BOOM!",
            Toast.LENGTH_SHORT
        )

        holder.card.setOnClickListener {
            if (!holder.taskComplete.isChecked) {
                popMsg.show()
                holder.taskComplete.isChecked = true
            }
        }
    }
}