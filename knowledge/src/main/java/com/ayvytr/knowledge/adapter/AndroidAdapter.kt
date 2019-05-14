package com.ayvytr.knowledge.adapter

import android.content.Context
import android.view.View
import com.ayvytr.baseadapter.CommonAdapter
import com.ayvytr.baseadapter.ViewHolder
import com.ayvytr.commonlibrary.bean.Gank
import com.ayvytr.commonlibrary.util.toLocalTime
import com.ayvytr.knowledge.R
import com.ayvytr.ktx.ui.show
import com.bumptech.glide.Glide
import java.util.*

/**
 * @author admin
 */
class AndroidAdapter @JvmOverloads constructor(context: Context, layoutResId: Int = R.layout.layout_item_android,
                                               list: List<Gank> = ArrayList(0)) :
    CommonAdapter<Gank>(context, layoutResId, list) {

    override fun convert(holder: ViewHolder, gank: Gank, position: Int) {
        val images = gank.images
        val showImage = images != null && !images.isEmpty()
        if (showImage) {
            Glide.with(mContext)
                .load(images!![0])
                .into(holder.getView(R.id.iv))
        }
        holder.getView<View>(R.id.iv).show(showImage)

        holder.setText(R.id.tv_title, gank.desc)
        holder.setText(R.id.tv_date, gank.publishedAt?.toLocalTime())
        holder.setText(R.id.tv_who, gank.who)
    }
}
