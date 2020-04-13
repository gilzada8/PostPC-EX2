package com.example.ex2

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_completed.*
import kotlinx.android.synthetic.main.animation_layout.view.*

class CompletedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_completed)


        val position = intent.extras?.getInt("position")
        taskNameValue.text = TodoData.todoTasks[position!!].taskString
        taskIsCompleteValue.isChecked = TodoData.todoTasks[position].complete
        val layoutBuilder = LayoutInflater.from(this).inflate(R.layout.animation_layout, null)

        Done.setOnClickListener { finish() }

        taskIsCompleteValue.setOnClickListener {
            TodoData.markTaskComplete(position, false)
            AlertDialog.Builder(this).setView(layoutBuilder).setTitle("Keep pushing ! ").show()
            layoutBuilder.deleted_animation.visibility = View.INVISIBLE
            layoutBuilder.success_animation.visibility = View.VISIBLE
            layoutBuilder.success_animation.progress = 1F
            layoutBuilder.success_animation.speed = 2F
            layoutBuilder.success_animation.reverseAnimationSpeed()
            layoutBuilder.success_animation.playAnimation()
            Handler().postDelayed({ -> finish() }, 1500)

        }

        deleteTask.setOnClickListener {
            val builder =
                AlertDialog.Builder(this).setTitle("Are You Sure to delete?")
            builder.setPositiveButton("I'm sure") { _, _ ->
                TodoData.deleteTask(position)

                AlertDialog.Builder(this).setView(layoutBuilder)
                    .setTitle("Todo deleted successfully").show()
                layoutBuilder.deleted_animation.visibility = View.VISIBLE
                layoutBuilder.deleted_animation.progress = 0F
                layoutBuilder.deleted_animation.playAnimation()
                Handler().postDelayed({ -> finish() }, 1200)

            }
            builder.setNegativeButton("Nope") { _, _ -> }
            builder.show()
        }
    }
}
