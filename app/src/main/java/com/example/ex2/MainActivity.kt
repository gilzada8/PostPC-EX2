package com.example.ex2

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.todo_one.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = TodoTaskAdapter()
        adapter.setTasks(createTodoTask())

        TodoTaskRV.adapter = adapter
        TodoTaskRV.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

//      creating Toast once to avoid aggregation
        val popMsg = Toast.makeText(
            applicationContext,
            "Empty task might be fun, but not supported",
            Toast.LENGTH_SHORT
        )

        button.setOnClickListener {
            if (editText.text.isEmpty()) {
                popMsg.show()
            } else {
                adapter.addTask(editText.text.toString())
            }
            editText.text.clear()
        }

    }
}
