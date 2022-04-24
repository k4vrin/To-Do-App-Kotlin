package com.kavrin.to_doapp.fragments.add

import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kavrin.to_doapp.R
import com.kavrin.to_doapp.data.models.ToDoData
import com.kavrin.to_doapp.data.viewmodel.ToDoViewModel
import com.kavrin.to_doapp.databinding.FragmentAddBinding
import com.kavrin.to_doapp.fragments.SharedViewModel

class AddFragment : Fragment() {

    // SetUp ViewBinding
    private var _binding: FragmentAddBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    // Here, get() means this property is "get-only".
    // That means you can get the value, but once assigned (as it is here), you can't assign it to something else.
    private val binding get() = _binding!!

    // Initialize ToDoViewModel
    private val mToDoViewModel: ToDoViewModel by viewModels()
    // Initialize SharedViewModel
    private val mSharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val view = binding.root

        // Initialize the spinner color change
        binding.prioritiesSpinner.onItemSelectedListener = mSharedViewModel.listener

        // FAB animation icon change
        val fab = activity?.findViewById<FloatingActionButton>(R.id.floating_action_button)
        fab?.show()
        fab?.setOnClickListener {
            fab.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.check_to_add_anim))
            val drawable: Drawable = fab.drawable
            val avd: AnimatedVectorDrawable = drawable as AnimatedVectorDrawable
            val insert = insertDataToDb()
            if (insert) avd.start()
        }

        return view
    }

    // SetUp ViewBinding
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Create AddFragment Menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    /**
     * Insert data to [ToDoDatabase]
     *
     */
    private fun insertDataToDb(): Boolean {
        val mTitle = binding.titleEt.text.toString()
        val mPriority = binding.prioritiesSpinner.selectedItem.toString()
        val mDescription = binding.descriptionEt.text.toString()

        // Validating the data
        val validation = mSharedViewModel.verifyDataFromUser(mTitle, mDescription)
        if (validation) {
            // Insert data to db
            val newData = ToDoData(
                0,
                mTitle,
                mSharedViewModel.parsePriority(mPriority),
                mDescription
            )
            // Add new data to db
            mToDoViewModel.insertData(newData)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()
            // Navigate Back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
            return true
        }
        // Not successful
        Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
        return false
    }
}