package com.ayvytr.commonlibrary.callback

import android.support.v7.util.DiffUtil

import com.ayvytr.commonlibrary.bean.Gank

/**
 * @author Do
 */
class GankCallback(private val oldList: List<Gank>, private val list: List<Gank>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return list.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition]._id == list[newItemPosition]._id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].desc == list[newItemPosition].desc
    }
}
