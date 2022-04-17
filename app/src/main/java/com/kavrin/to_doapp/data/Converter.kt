package com.kavrin.to_doapp.data

import androidx.room.TypeConverter
import com.kavrin.to_doapp.data.models.Priority

class Converter {

    /**
     * Converting our Priority object to String
     */
    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    /**
     * Convert back the String to our Priority object
     */
    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }
}


/**
 * Room provides functionality for converting between primitive and boxed types but
 * does not allow for object references between entities. It means Room Database
 * only allow primitive types like string, int, and... but it does not allow custom
 * objects like our priority class.
 *
 */