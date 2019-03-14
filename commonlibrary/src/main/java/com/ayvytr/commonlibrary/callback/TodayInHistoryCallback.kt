package com.ayvytr.commonlibrary.callback

import android.support.v7.util.DiffUtil

import com.ayvytr.mob.TodayInHistory

/**
 * @author Do
 */
class TodayInHistoryCallback(private val oldList: List<TodayInHistory.ResultBean>,
                             private val list: List<TodayInHistory.ResultBean>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return list.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == list[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].title == list[newItemPosition].title
    }
}
