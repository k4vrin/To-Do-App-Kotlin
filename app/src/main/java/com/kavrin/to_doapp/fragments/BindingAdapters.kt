package com.kavrin.to_doapp.fragments

import android.view.View
import android.widget.Spinner
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kavrin.to_doapp.R
import com.kavrin.to_doapp.data.models.Priority
import com.kavrin.to_doapp.data.models.ToDoData
import com.kavrin.to_doapp.fragments.list.ListFragmentDirections

/**
 * Binding adapters
 *
 * Contains all the custom adapters
 */
class BindingAdapters {

    companion object {

        /* ============================= List Fragment ============================= */

        /**
         * Navigate to add fragment
         *
         * setOnClickListener for Floating Action Button with DataBinding to
         * navigate to addFragment from listFragment.
         */
        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(view: FloatingActionButton, navigate: Boolean) {
            view.setOnClickListener {
                if (navigate) {
                    view.findNavController().navigate(R.id.action_listFragment_to_addFragment)
                }
            }
        }

        /**
         * Empty database
         *
         * Observe the db and if it is empty show the no data TextView and ImageView
         */
        @BindingAdapter("android:emptyDatabase")
        @JvmStatic
        fun emptyDatabase(view: View, emptyDatabase: LiveData<Boolean>) {
            // We will pass the emptyDatabase via mSharedViewModel in layout
            when(emptyDatabase.value) {
                true -> view.visibility = View.VISIBLE
                else -> view.visibility = View.INVISIBLE
            }
        }

        /* ============================= Update Fragment ============================= */

        /**
         * Parse priority to int
         *
         * Parse the Priority object to the corresponding number
         */
        @BindingAdapter("android:parsePriorityToInt")
        @JvmStatic
        fun parsePriorityToInt(view: Spinner, priority: Priority) {
            when(priority) {
                Priority.HIGH -> view.setSelection(0)
                Priority.MEDIUM -> view.setSelection(1)
                Priority.LOW -> view.setSelection(2)
            }
        }

        /* ============================= List Adapter ============================= */

        /**
         * Parse priority to int
         *
         * Parse the Priority object to the corresponding number
         */
        @BindingAdapter("android:parsePriorityColor")
        @JvmStatic
        fun parsePriorityColor(cardView: CardView, priority: Priority) {
            when(priority) {
                Priority.HIGH -> cardView.setCardBackgroundColor(cardView.context.getColor(R.color.red))
                Priority.MEDIUM -> cardView.setCardBackgroundColor(cardView.context.getColor(R.color.yellow))
                Priority.LOW -> cardView.setCardBackgroundColor(cardView.context.getColor(R.color.green))
            }
        }

        @BindingAdapter("android:sendDataToUpdateFragment")
        @JvmStatic
        fun sendDataToUpdateFragment(view: ConstraintLayout, currentItem: ToDoData) {
            // SetOnClickListener for each RecyclerView
            view.setOnClickListener {
                // Safe args implementation
                // Pass the ToDoData of the current RecyclerView to the UpdateFragment
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
                view.findNavController().navigate(action)
            }
        }

    }


}