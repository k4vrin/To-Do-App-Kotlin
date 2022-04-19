package com.kavrin.to_doapp.data.repository

import androidx.lifecycle.LiveData
import com.kavrin.to_doapp.data.ToDoDao
import com.kavrin.to_doapp.data.models.ToDoData

// Our Repository get reference to the DAO
class ToDoRepository(private val toDoDao: ToDoDao) {

    // Get allData from DAO
    val getAllData: LiveData<List<ToDoData>> = toDoDao.getAllData()

    // Insert data to database through DAO
    suspend fun insertData(toDoData: ToDoData) {
        toDoDao.insertData(toDoData)
    }

    // Update data on database through DAO
    suspend fun updateData(toDoData: ToDoData) {
        toDoDao.updateData(toDoData)
    }
}

/**
 * [Repository] a repository is a class that abstracts the access to multiple data sources.
 * it's not part of android architecture components but is a suggested best practice for code separation
 * and architecture.
 * a Repository class provides clean API for data access to the rest of the application
 */