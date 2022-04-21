package com.kavrin.to_doapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kavrin.to_doapp.data.models.ToDoData

@Dao
interface ToDoDao {

    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<ToDoData>> // LiveData will observe the data and delivers updates only when data changes,and only to active observers.

    @Insert(onConflict = OnConflictStrategy.IGNORE) // When new item comes into our database and it's duplicated, we ignore it
    suspend fun insertData(toDoData: ToDoData) // suspend means this function only should be used inside coroutine or another suspend function

    @Update
    suspend fun updateData(toDoData: ToDoData)

    @Delete
    suspend fun deleteData(toDoData: ToDoData)

    @Query("DELETE FROM todo_table")
    suspend fun deleteAllData()

    // Search the db
    @Query("SELECT * FROM todo_table WHERE title LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): LiveData<List<ToDoData>>

    // For sorting by high
    @Query("SELECT * FROM todo_table ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
    fun sortByHighPriority(): LiveData<List<ToDoData>>

    // For sorting by low
    @Query("SELECT * FROM todo_table ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END")
    fun sortByLowPriority(): LiveData<List<ToDoData>>
}

/**
 * [DAO] stands for data access object.
 * DAOs are responsible for defining the methods that access database.
 * Our DAO contains all SQL queries.
 * There are already some queries that we can access with annotations
 * @[Insert] for inserting data
 * @[Update] for updating
 * @[Delete] for deleting
 * @[Query] fpr custom queries
 *
 */

/**
 * [LiveData]
 * LiveData is an observable data holder class. Unlike a regular observable,
 * LiveData is lifecycle-aware, meaning it respects
 * the lifecycle of other app components,such as activities, fragments, or services.
 * This awareness ensures LiveData only updates app component observers
 * that are in an active lifecycle state.
 * Generally, LiveData delivers updates only when data changes,
 * and only to active observers.
 * An exception to this behavior is that observers also receive an update
 * when they change from an inactive to an active state.
 * Furthermore, if the observer changes from inactive to active a second time,
 * it only receives an update if the value has changed
 * since the last time it became active.
 */

/**
 * LiveData is an observable data holder class.
 * Unlike a regular observable, LiveData is lifecycle-aware,
 * meaning it respects the lifecycle of other app components,
 * such as activities, fragments, or services.
 * This awareness ensures LiveData only updates app component observers that
 * are in an active lifecycle state.
 *
 * [ObservableDataHolderClass] means that LiveData can observed by other components
 * LiveData object will send updates to our observer only if observer(activity) is in active state
 * if observer is paused or destroyed LiveData object will not send updates instead
 * it will wait until our observer come back in active state
 */