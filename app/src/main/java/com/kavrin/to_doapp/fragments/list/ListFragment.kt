package com.kavrin.to_doapp.fragments.list

import android.app.AlertDialog
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.kavrin.to_doapp.R
import com.kavrin.to_doapp.data.models.ToDoData
import com.kavrin.to_doapp.data.viewmodel.ToDoViewModel
import com.kavrin.to_doapp.databinding.FragmentListBinding
import com.kavrin.to_doapp.fragments.SharedViewModel
import com.kavrin.to_doapp.fragments.list.adapter.ListAdapter
import com.kavrin.to_doapp.utils.hideKeyboard
import com.kavrin.to_doapp.utils.observeOnce
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator


class ListFragment : Fragment(), SearchView.OnQueryTextListener {


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

        // FAB animation Icon Change
        val fab = activity?.findViewById<FloatingActionButton>(R.id.floating_action_button)
        fab?.show()
        fab?.setOnClickListener {
            fab.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.add_to_check_anim))
            val drawable: Drawable = fab.drawable
            val avd: AnimatedVectorDrawable = drawable as AnimatedVectorDrawable
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
            avd.start()
        }

        // Set Menu
        setHasOptionsMenu(true)

        // Hide Soft keyboard
        hideKeyboard(requireActivity())

        return binding.root
    }

    private fun setUpRecyclerView() {
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        //Linear Layout
//        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        // Grid Layout
//        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        // StaggeredGrid Layout
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        // Setup Animation
        recyclerView.itemAnimator = SlideInUpAnimator().apply {
            addDuration = 300L
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
//                adapter.notifyItemRemoved(viewHolder.adapterPosition) // With DiffUtil we don't need this
                // Restore deleted data
                restoreDeletedData(viewHolder.itemView, deletedItem)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restoreDeletedData(view: View, deletedItem: ToDoData) {
        val snackBar = Snackbar.make(
            view,
            "Deleted '${deletedItem.title}'",
            Snackbar.LENGTH_LONG
        )
        snackBar.setAction("Undo") {
            mToDoViewModel.insertData(deletedItem)
//            adapter.notifyItemChanged(position) // This line crash the app if it's used with StaggeredGrid
        }
        snackBar.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            // When delete all selected
            R.id.menu_delete_all -> confirmRemoval()
            // When Sort Selected
            R.id.menu_priority_high -> sort("high")
            R.id.menu_priority_low -> sort("low")
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

        // Setup search
        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    /**
     * Called when the user submits the query. This could be due to a key press on the
     * keyboard or due to pressing a submit button.
     * The listener can override the standard behavior by returning true
     * to indicate that it has handled the submit request. Otherwise return false to
     * let the SearchView handle the submission by launching any associated intent.
     *
     * @param query the query text that is to be submitted
     *
     * @return true if the query has been handled by the listener, false to let the
     * SearchView perform the default action.
     */
    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) searchThroughDatabase(query)
        return true
    }

    /**
     * Called when the query text is changed by the user.
     *
     * @param newText the new content of the query text field.
     *
     * @return false if the SearchView should perform the default action of showing any
     * suggestions if available, true if the action was handled by the listener.
     */
    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) searchThroughDatabase(newText)
        return true
    }

    /**
     * Search through database
     *
     * Search and update the UI with new list
     */
    private fun searchThroughDatabase(query: String) {
        val searchQuery = "%$query%"
        // observeOnce for search undo bug
        mToDoViewModel.searchDatabase(searchQuery).observeOnce(viewLifecycleOwner) { list ->
            list?.let {
                adapter.setData(it)
            }
        }
    }

    /**
     * Sort
     * Observe the data and call the adapter to update the data
     */
    private fun sort(priority: String) {
        when (priority) {
            "high" -> {
                mToDoViewModel
                    .sortByHighPriority
                    .observe(viewLifecycleOwner) {
                        adapter.setData(it)
                    }
            }
            "low" -> {
                mToDoViewModel
                    .sortByLowPriority
                    .observe(viewLifecycleOwner) {
                        adapter.setData(it)
                    }
            }
        }
    }
}