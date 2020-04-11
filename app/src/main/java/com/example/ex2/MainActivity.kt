package com.example.ex2

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val todoData = TodoData(applicationContext)
        val adapter = TodoTaskAdapter(applicationContext, todoData)
        todoData.loadTodoList(applicationContext)
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
                todoData.addTask(editText.text.toString(), adapter, applicationContext)
            }
            editText.text.clear()
        }

    }
}
