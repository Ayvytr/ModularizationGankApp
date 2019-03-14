package com.ayvytr.commonlibrary.callback

import android.support.v7.util.DiffUtil
import com.ayvytr.commonlibrary.bean.WechatArticle

/**
 * @author Do
 */
class WechatArticleCallback(private val oldList: List<WechatArticle.ResultBean.ListBean>,
                            private val list: List<WechatArticle.ResultBean.ListBean>): DiffUtil.Callback(){
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