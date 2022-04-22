package com.kavrin.to_doapp.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Hide keyboard
 *
 * Hide keyboard when it's not needed
 */
fun hideKeyboard(activity: Activity) {
    val inputMethodManager =
        activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    val currentFocusedView = activity.currentFocus
    currentFocusedView?.let {
        inputMethodManager.hideSoftInputFromWindow(
            currentFocusedView.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, mObserver: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        /**
         * Called when the data is changed.
         */
        override fun onChanged(t: T) {
            mObserver.onChanged(t)
            removeObserver(this)
        }
    })
}