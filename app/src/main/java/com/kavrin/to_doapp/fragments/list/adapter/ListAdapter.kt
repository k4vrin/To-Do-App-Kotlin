package com.kavrin.to_doapp.fragments.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
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
    class MyViewHolder(private val binding: RowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(toDoData: ToDoData) {
            binding.toDoData = toDoData
            binding.executePendingBindings()
        }

        companion object {
            /**
             * From
             *
             * Inflate rowLayout
             */
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }

    }

    /**
     * On create view holder
     *
     * This function will be called when RecyclerView needs a new ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    /**
     * On bind view holder
     *
     * Bind the data to the items.
     * It takes the data from the list set it to the corresponding view
     */
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // Connect binding toDoData to ListAdapter toDoData
        holder.bind(dataList[position])
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
        // Setup DiffUtil
        val toDoDiffUtil = ToDoDiffUtil(dataList, toDoData)
        val toDoDiffUtilResult = DiffUtil.calculateDiff(toDoDiffUtil)

        this.dataList = toDoData

        // Notify RecyclerView about Changes.
        // Using DiffUtil instead of notifyDataSetChanged()
        toDoDiffUtilResult.dispatchUpdatesTo(this)
    }
}

/**
 * The Adapter job is create views for the item in RecyclerView and
 * to set the contents of the items accordingly
 *
 *
 */