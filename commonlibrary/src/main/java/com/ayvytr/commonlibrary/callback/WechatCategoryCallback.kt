package com.ayvytr.commonlibrary.callback

import android.support.v7.util.DiffUtil
import com.ayvytr.commonlibrary.bean.WechatCategory

/**
 * @author Do
 */
class WechatCategoryCallback(private val oldList: List<WechatCategory.ResultBean>,
                             private val list: List<WechatCategory.ResultBean>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return list.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].cid == list[newItemPosition].cid
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].cid == list[newItemPosition].cid
    }
}