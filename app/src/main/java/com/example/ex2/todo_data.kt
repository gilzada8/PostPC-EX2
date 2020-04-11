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

    fun loadTodoList() {
        todoCollectionRef.get().addOnSuccessListener { result ->
            for (document in result) {
                todoTasks.add(document.toObject<TodoTask>())
            }
        }
        Log.i("TodoLogger", todoTasks.size.toString())
    }

    private fun saveTodoList(context: Context) {
        for (todoTask in todoTasks) {
            todoCollectionRef.add(todoTask) // TODO : add on success / fail
        }
    }

    fun addTask(task: String, rv: TodoTaskAdapter, context: Context) {
        todoTasks.add(TodoTask(task, false))
        saveTodoList(context)
        rv.notifyItemInserted(todoTasks.size - 1)
    }

    fun deleteTask(position: Int, rv: TodoTaskAdapter, context: Context) {
        todoTasks.removeAt(position)
        saveTodoList(context)
        rv.notifyItemRemoved(position)
        rv.notifyItemRangeChanged(position, todoTasks.size)
    }

    fun modifyTask(position: Int, isComplete: Boolean) {
        todoTasks[position].complete = isComplete
    }

}