package com.example.todotitan.ui.models


data class TaskListObj(var taskobj :TaskListModel = TaskListModel() )

data class TaskListModel(var taskList :ArrayList<TaskModel> = arrayListOf())

data class TaskModel(val id :Int =0, val taskDescription :String ="", var isCompleted :Boolean=false)