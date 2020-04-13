package com.example.ex2

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*


data class TodoTask(
    var taskString: String = "",
    var complete: Boolean = false,
    val createTime: String = SimpleDateFormat("dd.MM.yyyy 'at' HH:mm").format(Calendar.getInstance().time),
    var editTime: String = createTime,
    var id: String = ""
)


class TodoTaskHolder(view: View) : RecyclerView.ViewHolder(view) {
    val card: RelativeLayout = view.findViewById(R.id.todo_card)
    val taskText: TextView = view.findViewById(R.id.todo_text)
    val taskComplete: CheckBox = view.findViewById(R.id.todo_check)
}


class TodoTaskAdapter(private val appContext: Context) :
    RecyclerView.Adapter<TodoTaskHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoTaskHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_one, parent, false)
        return TodoTaskHolder(view)
    }

    override fun getItemCount(): Int {
        return TodoData.todoTasks.size
    }

    override fun onBindViewHolder(holder: TodoTaskHolder, position: Int) {
        val task = TodoData.todoTasks[position]
        holder.taskText.text = task.taskString
        holder.taskComplete.isChecked = TodoData.todoTasks[position].complete

        holder.card.setOnClickListener {
            val intent = if (holder.taskComplete.isChecked) {
                Intent(appContext, CompletedActivity::class.java)
            } else {
                Intent(appContext, NotCompletedActivity::class.java)
            }
            intent.putExtra("position", position)
            appContext.startActivity(intent)
        }

    }

}

