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

    class ToDoDiffCallback(
        var oldList: List<ToDoData>,
        var newList: List<ToDoData>
    ) : DiffUtil.Callback() {

        /**
         * Returns the size of the old list.
         *
         * @return The size of the old list.
         */
        override fun getOldListSize(): Int {
            return oldList.size
        }

        /**
         * Returns the size of the new list.
         *
         * @return The size of the new list.
         */
        override fun getNewListSize(): Int {
            return newList.size
        }

        /**
         * Called by the DiffUtil to decide whether two object represent the same Item.
         *
         *
         * For example, if your items have unique ids, this method should check their id equality.
         *
         * @param oldItemPosition The position of the item in the old list
         * @param newItemPosition The position of the item in the new list
         * @return True if the two items represent the same object or false if they are different.
         */
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldList[oldItemPosition].id == newList[newItemPosition].id)
        }

        /**
         * Called by the DiffUtil when it wants to check whether two items have the same data.
         * DiffUtil uses this information to detect if the contents of an item has changed.
         *
         *
         * DiffUtil uses this method to check equality instead of [Object.equals]
         * so that you can change its behavior depending on your UI.
         * For example, if you are using DiffUtil with a
         * [RecyclerView.Adapter], you should
         * return whether the items' visual representations are the same.
         *
         *
         * This method is called only if [.areItemsTheSame] returns
         * `true` for these items.
         *
         * @param oldItemPosition The position of the item in the old list
         * @param newItemPosition The position of the item in the new list which replaces the
         * oldItem
         * @return True if the contents of the items are the same or false if they are different.
         */
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
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
        val oldList = dataList
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            ToDoDiffCallback(
                oldList,
                toDoData
            )
        )
        this.dataList = toDoData

        diffResult.dispatchUpdatesTo(this)
    }
}

/**
 * The Adapter job is create views for the item in RecyclerView and
 * to set the contents of the items accordingly
 *
 *
 */