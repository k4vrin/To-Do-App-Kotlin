package com.kavrin.to_doapp.fragments.add

import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.kavrin.to_doapp.R
import com.kavrin.to_doapp.databinding.FragmentAddBinding

class AddFragment : Fragment() {

    // SetUp ViewBinding
    private var _binding: FragmentAddBinding? = null
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
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val view = binding.root

        // Set Menu
        setHasOptionsMenu(true)

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

    //
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add) insertDataToDb()
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDb() {
        val mTitle = binding.titleEt.text.toString()
    }
}