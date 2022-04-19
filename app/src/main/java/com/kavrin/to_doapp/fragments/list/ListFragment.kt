package com.kavrin.to_doapp.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kavrin.to_doapp.R
import com.kavrin.to_doapp.data.models.ToDoData
import com.kavrin.to_doapp.data.viewmodel.ToDoViewModel
import com.kavrin.to_doapp.databinding.FragmentListBinding


class ListFragment : Fragment() {

    // Initialize ToDoViewModel
    private val mToDoViewModel: ToDoViewModel by viewModels()

    // Initialize ListAdapter
    private val adapter: ListAdapter by lazy { ListAdapter() }

    // SetUp ViewBinding
    private var _binding: FragmentListBinding? = null
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
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root

        // Initialize RecyclerView
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        // We are gonna notify when there is change in our database and apply it to the recyclerview
        mToDoViewModel.getAllData.observe(viewLifecycleOwner, Observer { data: List<ToDoData> ->
            adapter.setData(data)
        })

        // setOnClickListener for Floating Action Button
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        // Set Menu
        setHasOptionsMenu(true)

        return view
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_delete_all -> confirmRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Confirm removal
     *
     * Show AlertDialog to confirm All item removal
     *
     */
    private fun confirmRemoval() {
        // Pop Up a dialog asking the user that he is sure or not
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            // Delete the data from db
            mToDoViewModel.deleteAllData()
            Toast.makeText(
                requireContext(),
                "Successfully Removed Everything!",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No") {_, _ -> }
        builder.setTitle("Delete everything?")
        builder.setMessage("Are you sure you want to remove everything?")
        builder.create().show()
    }

    // SetUp ViewBinding
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }
}