package com.example.ex2

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.util.*


data class TodoTask(
    val taskString: String = "",
    var complete: Boolean = false,
    val createTime: String = Calendar.getInstance().time.toString(),
    var editTime: String = createTime,
    var id: String = ""
)


class TodoTaskHolder(view: View) : RecyclerView.ViewHolder(view) {
    val card: RelativeLayout = view.findViewById(R.id.todo_card)
    val taskText: TextView = view.findViewById(R.id.todo_text)
    val taskComplete: CheckBox = view.findViewById(R.id.todo_check)
}


class TodoTaskAdapter(private val appContext: Context, private val todoData: TodoData) :
    RecyclerView.Adapter<TodoTaskHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoTaskHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_one, parent, false)
        return TodoTaskHolder(view)
    }

    override fun getItemCount(): Int {
        return todoData.todoTasks.size
    }

    override fun onBindViewHolder(holder: TodoTaskHolder, position: Int) {
        val task = todoData.todoTasks[position]
        holder.taskText.text = task.taskString
        holder.taskComplete.isChecked = todoData.todoTasks[position].complete

        val popMsg = Toast.makeText(
            holder.itemView.context,
            "TODO " + holder.taskText.text + " is now DONE. BOOM!",
            Toast.LENGTH_SHORT
        )

        holder.card.setOnClickListener {
            if (!holder.taskComplete.isChecked) {
                popMsg.show()
                todoData.modifyTask(position, true)
                holder.taskComplete.isChecked = todoData.todoTasks[position].complete
            }
        }

        holder.card.setOnLongClickListener {
            val builder =
                AlertDialog.Builder(holder.itemView.context).setTitle("Are You Sure to delete?")
            builder.setPositiveButton("I'm sure") { _, _ ->
                todoData.deleteTask(position, this)
            }
            builder.setNegativeButton("Nope") { _, _ -> }
            builder.show()
            true
        }
    }

}

