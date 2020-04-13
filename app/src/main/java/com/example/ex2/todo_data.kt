package com.example.ex2

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


object TodoData {

    val todoTasks: MutableList<TodoTask> = ArrayList()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val todoCollectionRef: CollectionReference = db.collection("todoData")

    fun loadTodoList(rv: TodoTaskAdapter) {
        val size = todoTasks.size
        todoTasks.clear()
        rv.notifyItemRangeRemoved(0, size)
        todoCollectionRef.get().addOnSuccessListener { result ->
            for (document in result) {
                todoTasks.add(document.toObject(TodoTask::class.java))
                rv.notifyItemInserted(todoTasks.size - 1)
            }
        }
        Log.i("TodoLogger", todoTasks.size.toString())
    }

    fun addTask(task: String, rv: TodoTaskAdapter) {
        val todoTask = TodoTask(task, false)
        todoCollectionRef.add(todoTask).addOnSuccessListener { docRef ->
            docRef.update("id", docRef.id)
            todoTask.id = docRef.id
            todoTasks.add(todoTask)
            rv.notifyItemInserted(todoTasks.size - 1)
        } // TODO : add on fail
    }

    fun deleteTask(position: Int) {
        todoCollectionRef.document(todoTasks[position].id).delete() // TODO : add on fail
        todoTasks.removeAt(position)

    }

    fun modifyTaskName(position: Int, newName: String) {
        todoCollectionRef.document(todoTasks[position].id).update(
            "editTime", SimpleDateFormat("dd.MM.yyyy 'at' HH:mm").format(
                Calendar.getInstance().time
            )
        )
        todoCollectionRef.document(todoTasks[position].id).update("taskString", newName)
        todoTasks[position].taskString = newName
//        rv.notifyItemChanged(position)
    }

    fun markTaskComplete(position: Int, complete: Boolean) {
        todoCollectionRef.document(todoTasks[position].id).update(
            "editTime", SimpleDateFormat("dd.MM.yyyy 'at' HH:mm").format(
                Calendar.getInstance().time
            )
        )
        todoCollectionRef.document(todoTasks[position].id).update("complete", complete)
        todoTasks[position].complete = complete
    }

}