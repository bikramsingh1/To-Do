package com.example.todotitan.ui.repos

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.todotitan.ui.models.TaskListObj
import com.example.todotitan.utills.AppConstants
import com.google.gson.Gson
import javax.inject.Inject

class AddTaskRepo @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) {

    fun saveTaskList(data: TaskListObj) {
        val gson = Gson()
        val obj = gson.toJson(data)
        sharedPreferences.edit { putString(AppConstants.SharedPreferencesName,obj)}
    }

    fun getTaskList(): TaskListObj {
        val userDataString = sharedPreferences.getString(AppConstants.SharedPreferencesName,null)
        val gson = Gson()
        return if(userDataString !=null) gson.fromJson(userDataString, TaskListObj::class.java) else TaskListObj()
    }


}