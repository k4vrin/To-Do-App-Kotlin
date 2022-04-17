package com.kavrin.to_doapp.fragments

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel
import com.kavrin.to_doapp.data.models.Priority

class SharedViewModel(application: Application) : AndroidViewModel(application) {


    /**
     * Verify data from user
     *
     * If title [or] description were empty it return false.
     * If title [and] description were not empty return true
     */
    fun verifyDataFromUser(title: String, description: String): Boolean {
        // android.text.TextUtils
        return if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) false
        // kotlin.text
        else !(title.isEmpty() || description.isEmpty())
    }

    /**
     * Parse priority
     *
     * Convert Priority string to Priority object
     */
    fun parsePriority(priority: String): Priority {
        return when(priority) {
            "High Priority" -> Priority.HIGH
            "Medium Priority" -> Priority.MEDIUM
            "Low Priority" -> Priority.LOW
            else -> Priority.LOW
        }
    }

}