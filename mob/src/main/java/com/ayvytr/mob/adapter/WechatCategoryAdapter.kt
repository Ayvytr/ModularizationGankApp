package com.ayvytr.mob.adapter

import android.content.Context
import com.ayvytr.baseadapter.CommonAdapter
import com.ayvytr.baseadapter.ViewHolder
import com.ayvytr.commonlibrary.bean.WechatCategory
import com.ayvytr.mob.R

/**
 * @author Do
 */
class WechatCategoryAdapter constructor(context: Context) :
    CommonAdapter<WechatCategory.ResultBean>(context, R.layout.item_wechat_category) {

    override fun convert(holder: ViewHolder, t: WechatCategory.ResultBean, position: Int) {
        holder.setText(R.id.tvTitle, t.name)
    }
}