package com.ayvytr.mob.adapter

import android.content.Context
import com.ayvytr.baseadapter.ViewHolder
import com.ayvytr.baseadapter.wrapper.EmptyWrapperAdapter
import com.ayvytr.commonlibrary.bean.WechatCategory
import com.ayvytr.mob.R

/**
 * @author Do
 */
class WechatCategoryAdapter constructor(context: Context) :
    EmptyWrapperAdapter<WechatCategory.ResultBean>(context, R.layout.item_wechat_category) {

    override fun convert(holder: ViewHolder, t: WechatCategory.ResultBean, position: Int) {
        holder.setText(R.id.tvTitle, t.name)
    }
}