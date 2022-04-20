package com.kavrin.to_doapp.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kavrin.to_doapp.R
import com.kavrin.to_doapp.data.models.ToDoData
import com.kavrin.to_doapp.data.viewmodel.ToDoViewModel
import com.kavrin.to_doapp.databinding.FragmentListBinding
import com.kavrin.to_doapp.fragments.SharedViewModel
import com.kavrin.to_doapp.fragments.list.adapter.ListAdapter
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator


class ListFragment : Fragment() {

    // Initialize ToDoViewModel
    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

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
    ): View {
        // Inflate the layout for this fragment via DataBinding
        _binding = FragmentListBinding.inflate(inflater, container, false)
        // Connect layout mSharedViewModel to thi Fragment mSharedViewModel
        // Instead of of observing emptyDatabase from Fragment we are gonna observe it from layout
        binding.lifecycleOwner = this
        binding.mSharedViewModel = mSharedViewModel

        // Initialize RecyclerView
        setUpRecyclerView()

        // We are gonna notify when there is change in our database and apply it to the recyclerview
        mToDoViewModel.getAllData.observe(viewLifecycleOwner) { data: List<ToDoData> ->
            // Check if database is empty
            mSharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        }

        // Set Menu
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun setUpRecyclerView() {
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        // Setup Animation
        recyclerView.itemAnimator = SlideInUpAnimator().apply {
            addDuration = 300
        }

        // Swipe
        swipeToDelete(recyclerView)
    }

    /**
     * Swipe to delete
     */
    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // Store the item that we are swiping
                val deletedItem = adapter.dataList[viewHolder.adapterPosition]
                // Delete Item
                mToDoViewModel.deleteData(deletedItem)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
                // Restore deleted data
                restoreDeletedData(viewHolder.itemView, deletedItem, viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restoreDeletedData(view: View, deletedItem: ToDoData, position: Int) {
        val snackBar = Snackbar.make(
            view,
            "Deleted '${deletedItem.title}'",
            Snackbar.LENGTH_LONG
        )
        snackBar.setAction("Undo") {
            mToDoViewModel.insertData(deletedItem)
            adapter.notifyItemChanged(position)
        }
        snackBar.show()
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

    // set binding to null to avoid memory leaks
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }
}