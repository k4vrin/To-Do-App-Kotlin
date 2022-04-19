package com.kavrin.to_doapp.fragments

import android.app.Application
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.kavrin.to_doapp.R
import com.kavrin.to_doapp.data.models.Priority

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    // Create listener for Spinner color change
    val listener: AdapterView.OnItemSelectedListener = object :
        AdapterView.OnItemSelectedListener {

        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            when(position) {
                0 -> {
                    (parent?.getChildAt(0) as TextView)
                        .setTextColor(ContextCompat.getColor(application, R.color.red))
                }
                1 -> {
                    (parent?.getChildAt(0) as TextView)
                        .setTextColor(ContextCompat.getColor(application, R.color.yellow))
                }
                2 -> {
                    (parent?.getChildAt(0) as TextView)
                        .setTextColor(ContextCompat.getColor(application, R.color.green))
                }
            }
        }
        // We don't need this
        override fun onNothingSelected(parent: AdapterView<*>?) {}

    }


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

    /**
     * Parse priority
     *
     * Parse the Priority object to the corresponding number
     */
    fun parsePriorityToInt(priority: Priority): Int {
        return when(priority) {
            Priority.HIGH -> 0
            Priority.MEDIUM -> 1
            Priority.LOW -> 2
        }
    }

}