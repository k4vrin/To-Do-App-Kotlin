package com.kavrin.to_doapp.fragments.list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.kavrin.to_doapp.data.models.ToDoData


/**
 * To do diff util
 *
 * This is class that finds the difference between two lists and provides the updated list as an
 * output. This class is used to notify updates to a RecyclerView Adapter.
 *
 * The main problem with notifyDataSetChanged is that it's a big overkill for performance.
 * It is updating all the views inside our RecyclerView whenever we call it. This method
 * will update the whole list all over again.
 *
 * If just one single item is changed, DiffUtil will send update only for that one single item
 * and not for all RecyclerView items.
 */
class ToDoDiffUtil(
    private val oldList: List<ToDoData>,
    private val newList: List<ToDoData>
) : DiffUtil.Callback() {

    /**
     * Returns the size of the old list.
     */
    override fun getOldListSize(): Int {
        return oldList.size
    }

    /**
     * Returns the size of the new list.
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
//        return oldList[oldItemPosition] === newList[newItemPosition] // This equality check cause the whole RecyclerView Update and it's against DiffUtil purpose
        return oldList[oldItemPosition].id == newList[newItemPosition].id
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