package com.kavrin.to_doapp.fragments.update

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.kavrin.to_doapp.R
import com.kavrin.to_doapp.data.models.Priority
import com.kavrin.to_doapp.databinding.FragmentListBinding
import com.kavrin.to_doapp.databinding.FragmentUpdateBinding
import com.kavrin.to_doapp.fragments.SharedViewModel

class UpdateFragment : Fragment() {

    // Fetching the argument from ListFragment
    private val args by navArgs<UpdateFragmentArgs>()

    // Initialize SharedViewModel
    val mSharedViewModel: SharedViewModel by viewModels()

    // SetUp ViewBinding
    private var _binding: FragmentUpdateBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    // Here, get() means this property is "get-only".
    // That means you can get the value, but once assigned (as it is here), you can't assign it to something else.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        val view = binding.root

        // Set Menu
        setHasOptionsMenu(true)

        // Set the text of Title to ToDoData object Title that has been passed through safe args
        binding.currentTitleEt.setText(args.currentItem.title)
        // Set the Priority of the to-do to ToDoData object Priority that has been passed through safe args
        binding.currentPrioritiesSpinner.setSelection(parsePriority(args.currentItem.priority))
        // Set the text of Description to ToDoData object Description that has been passed through safe args
        binding.currentDescriptionEt.setText(args.currentItem.description)
        // Change the color of the Spinner Item
        binding.currentPrioritiesSpinner.onItemSelectedListener = mSharedViewModel.listener

        return view
    }

    // SetUp ViewBinding
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    /**
     * Parse priority
     *
     * Parse the Priority object to the corresponding number
     */
    private fun parsePriority(priority: Priority): Int {
        return when(priority) {
            Priority.HIGH -> 0
            Priority.MEDIUM -> 1
            Priority.LOW -> 2
        }
    }
}