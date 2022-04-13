package com.kavrin.to_doapp.data.repository

import androidx.lifecycle.LiveData
import com.kavrin.to_doapp.data.ToDoDao
import com.kavrin.to_doapp.data.models.ToDoData

class ToDoRepository(private val toDoDao: ToDoDao) {

    val getAllData: LiveData<List<ToDoData>> = toDoDao.getAllData()

    suspend fun insertData(toDoData: ToDoData) {
        toDoDao.insertData(toDoData)
    }
}