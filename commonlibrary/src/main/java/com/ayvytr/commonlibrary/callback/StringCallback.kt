package com.ayvytr.commonlibrary.callback

import android.support.v7.util.DiffUtil

/**
 * @author Do
 */
class StringCallback(private val oldList: List<String>,
                     private val list: List<String>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return list.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == list[newItemPosition]
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == list[newItemPosition]
    }
}
