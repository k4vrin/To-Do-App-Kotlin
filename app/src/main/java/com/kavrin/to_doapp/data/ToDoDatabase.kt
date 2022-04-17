package com.kavrin.to_doapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kavrin.to_doapp.data.models.ToDoData

// For exportSchema we should specific the pass in gradle file
@Database(entities = [ToDoData::class], version = 1, exportSchema = true)
@TypeConverters(Converter::class) // Converter for Priority objects
abstract class ToDoDatabase : RoomDatabase() {

    abstract fun toDoDao(): ToDoDao

    /**
     * [Singleton] our user database will have only one instance
     */
    companion object {

        @Volatile  // Writes to this field are immediately made visible to other threads
        private var INSTANCE: ToDoDatabase? = null

        fun getDatabase(context: Context): ToDoDatabase {

            val tempInstance = INSTANCE

            // Our instance is already created. Having multiple instance is expensive for performance
            if (tempInstance != null) return tempInstance

            // everything within this block will be protected from concurrent execution by multiple threads
            synchronized(this) {
                // Create an instance of our room database
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ToDoDatabase::class.java,
                    "todo_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

/**
 * [synchronized]: When a thread calls synchronized, it acquires the lock of that
 * synchronized block. Other threads don't have permission to call that same synchronized
 * block as long as previous thread which had acquired the lock, does not release the lock.
 *
 */

/**
 * @[Database] contains the database holder and serves as the main access point for
 * the connection to application
 *
 */