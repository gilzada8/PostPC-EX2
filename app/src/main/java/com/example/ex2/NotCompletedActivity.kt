package com.example.ex2

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_not_completed.*
import kotlinx.android.synthetic.main.animation_layout.view.*

class NotCompletedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_not_completed)

        val position = intent.extras?.getInt("position")
        taskNameValue.text = TodoData.todoTasks[position!!].taskString
        taskCreationTimeValue.text = TodoData.todoTasks[position].createTime
        taskEditTimeValue.text = TodoData.todoTasks[position].editTime
        taskIsCompleteValue.isChecked = TodoData.todoTasks[position].complete

        checkName(position)
        Done.setOnClickListener { finish() }

        taskIsCompleteValue.setOnClickListener {
            TodoData.markTaskComplete(position, true)

            val layoutBuilder = LayoutInflater.from(this).inflate(R.layout.animation_layout, null)
            AlertDialog.Builder(this).setView(layoutBuilder).setTitle("Good Job Champion !").show()
            layoutBuilder.deleted_animation.visibility = View.INVISIBLE
            layoutBuilder.success_animation.progress = 0F
            layoutBuilder.success_animation.playAnimation()
            Handler().postDelayed({ -> finish() }, 1500)
        }

    }


    private fun checkName(position: Int) {
        taskNameValue.setOnClickListener {
            val builder = AlertDialog.Builder(this).setTitle("Edit task name")
            val newName = EditText(this)
            newName.hint = "Enter new name"
            builder.setView(newName)
            builder.setPositiveButton("Apply") { _, _ ->
                val msg: String
                when {
                    newName.text.toString() == TodoData.todoTasks[position].taskString -> {
                        msg = "Silly, its the same name!"
                    }
                    newName.text.toString() == "" -> {
                        msg = "Empty task might be fun, but not supported"
                    }
                    else -> {
                        msg = "Task name updated successfully"
                        TodoData.modifyTaskName(position, newName.text.toString())
                        taskNameValue.text = TodoData.todoTasks[position].taskString
                        taskEditTimeValue.text = TodoData.todoTasks[position].editTime
                    }
                }
                Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("Cancel") { _, _ -> }
            builder.show()
        }
    }

}
