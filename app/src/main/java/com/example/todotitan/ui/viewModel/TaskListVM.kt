package com.example.todotitan.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todotitan.ui.models.TaskListObj
import com.example.todotitan.ui.models.TaskModel
import com.example.todotitan.ui.repos.AddTaskRepo
import com.example.todotitan.utills.AppConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class TaskListVM @Inject constructor(private val taskRepo: AddTaskRepo) : ViewModel() {

    private var taskArrayList = ArrayList<TaskModel>()
    val taskListEvent = MutableSharedFlow<ArrayList<TaskModel>>(1)

    init {
        fetchList()
    }

    fun addTask(task: String) {
        viewModelScope.launch {
            val request = TaskModel(id = Random.nextInt(0, AppConstants.RandomNumber), taskDescription = task)
            taskArrayList.add(request)
            saveListFromObj()
            getTaskList()
        }
    }

    fun deleteTask(taskId: Int) {
        viewModelScope.launch {
            taskArrayList.removeIf { it.id == taskId }
            saveListFromObj()
            getTaskList()
        }

    }

    fun markTaskAsComplete(taskId: Int, action: Boolean) {
        viewModelScope.launch {
            for (i in 0 until taskArrayList.size) {
                if (taskArrayList[i].id == taskId) {
                    taskArrayList[i].isCompleted = action
                }
            }
            saveListFromObj()
            getTaskList()
        }
    }


    private fun saveListFromObj() {
        viewModelScope.launch {
            val taskList = TaskListObj()
            taskList.taskobj.taskList = taskArrayList
            taskRepo.saveTaskList(taskList)
        }
    }

    private fun fetchList() {
        viewModelScope.launch {
            taskArrayList = taskRepo.getTaskList().taskobj.taskList
            taskListEvent.tryEmit(taskRepo.getTaskList().taskobj.taskList)
        }
    }

    private fun getTaskList() {
        viewModelScope.launch {
            taskListEvent.tryEmit(taskArrayList)
        }
    }
}