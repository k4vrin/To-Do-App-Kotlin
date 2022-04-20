package com.kavrin.to_doapp.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kavrin.to_doapp.data.models.Priority
import kotlinx.parcelize.Parcelize


@Entity(tableName = "todo_table")
@Parcelize
data class ToDoData(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var priority: Priority,
    var description: String
) : Parcelable

/**
 * Room is part of android architecture components and it provides an abstraction layer
 * or SqlLite to allow fluent database access
 * 1.Entity: Represents a table within the database. Room creates a table
 * for each class that has @Entity annotation, the fields in the class
 * correspond to columns in the table. Therefore, the entity classes
 * tend to be small model classes that don't contain any logic.
 *
 * 2.[Dao]: DAOs are responsible for defining the methods that access the
 * database. This is the place where we write our queries.
 *
 * 3.[Database]: Contains the database holder and serves as the main access point
 * for the underlying connection to your app's data.
 */
