package com.kavrin.to_doapp.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kavrin.to_doapp.R
import com.kavrin.to_doapp.data.models.Priority
import com.kavrin.to_doapp.data.models.ToDoData
import com.kavrin.to_doapp.databinding.RowLayoutBinding

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    // The Adapter needs to know which data it should set to which item
    var dataList = emptyList<ToDoData>()

    /**
     * My view holder
     *
     * A ViewHolder will be used to hold the views of the RecyclerView; [row_layout.xml]
     *
     */
    class MyViewHolder(val binding: RowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    /**
     * On create view holder
     *
     * This function will be called when RecyclerView needs a new ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
//        return MyViewHolder(view)
        val view = RowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    /**
     * On bind view holder
     *
     * Bind the data to the items.
     * It takes the data from the list set it to the corresponding view
     */
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = dataList[position]
//        val resources = context!!.resources
        holder.binding.titleTxt.text = item.title
        holder.binding.descriptionTxt.text = item.description

        when (item.priority) {
            Priority.HIGH -> {
                holder
                    .binding
                    .priorityIndicator
                    .setCardBackgroundColor(
                        ContextCompat.getColor(
                            holder.binding.root.context,
                            R.color.red
                        )
                    )
            }
            Priority.MEDIUM -> {
                holder
                    .binding
                    .priorityIndicator
                    .setCardBackgroundColor(
                        ContextCompat.getColor(
                            holder.binding.root.context,
                            R.color.yellow
                        )
                    )
            }
            Priority.LOW -> {
                holder
                    .binding
                    .priorityIndicator
                    .setCardBackgroundColor(
                        ContextCompat.getColor(
                            holder.binding.root.context,
                            R.color.green
                        )
                    )
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    /**
     * Set data
     *
     * It will be used from ListFragment when we are observing LiveData object
     */
    fun setData(toDoData: List<ToDoData>) {
        this.dataList = toDoData
        // Notify RecyclerView about Changes
        notifyDataSetChanged()
    }
}

/**
 * The Adapter job is create views for the item in RecyclerView and
 * to set the contents of the items accordingly
 *
 *
 */