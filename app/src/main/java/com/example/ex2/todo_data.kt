package com.example.ex2

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore


data class TodoData(
    val appContext: Context,
    val todoTasks: MutableList<TodoTask> = ArrayList(),
    val db: FirebaseFirestore = FirebaseFirestore.getInstance(),
    val todoCollectionRef: CollectionReference = db.collection("todoData")
) {

    fun loadTodoList(rv: TodoTaskAdapter) {
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

    fun deleteTask(position: Int, rv: TodoTaskAdapter) {
        todoCollectionRef.document(todoTasks[position].id).delete() // TODO : add on fail
        todoTasks.removeAt(position)
        rv.notifyItemRemoved(position)
        rv.notifyItemRangeChanged(position, todoTasks.size)
    }

    // TODO : check what to do when modify
    fun modifyTask(position: Int, isComplete: Boolean) {
        todoTasks[position].complete = isComplete
    }

}