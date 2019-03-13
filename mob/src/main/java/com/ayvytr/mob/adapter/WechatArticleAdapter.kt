package com.ayvytr.mob.adapter

import android.content.Context
import com.ayvytr.baseadapter.CommonAdapter
import com.ayvytr.baseadapter.ViewHolder
import com.ayvytr.commonlibrary.bean.WechatArticle
import com.ayvytr.mob.R
import com.bumptech.glide.Glide

/**
 * @author Do
 */
class WechatArticleAdapter(context: Context) : CommonAdapter<WechatArticle.ResultBean.ListBean>(
    context, R.layout.item_wechat_article) {

    override fun convert(holder: ViewHolder, t: WechatArticle.ResultBean.ListBean, position: Int) {
        holder.setText(R.id.tvTitle, "${t.pubTime}  ${t.title}")
        Glide.with(mContext)
            .load(t.thumbnails)
            .into(holder.getView(R.id.iv))
    }

}