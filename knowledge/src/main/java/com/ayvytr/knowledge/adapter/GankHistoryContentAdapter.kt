package com.ayvytr.knowledge.adapter

import android.content.Context
import com.ayvytr.baseadapter.ItemViewDelegate
import com.ayvytr.baseadapter.MultiItemTypeAdapter
import com.ayvytr.baseadapter.ViewHolder
import com.ayvytr.commonlibrary.bean.Gank
import com.ayvytr.commonlibrary.util.toLocalTime
import com.ayvytr.knowledge.R
import java.util.*

/**
 * @author admin
 */
class GankHistoryContentAdapter(context: Context) : MultiItemTypeAdapter<Gank>(context, ArrayList(0)) {

    init {
        addItemViewDelegate(TYPE_CONTENT, object : ItemViewDelegate<Gank> {
            override fun getItemViewLayoutId(): Int {
                return R.layout.layout_item_android
            }

            override fun isForViewType(item: Gank, position: Int): Boolean {
                return getItemViewType(position) == TYPE_CONTENT
            }

            override fun convert(holder: ViewHolder, gank: Gank, position: Int) {
                holder.setText(R.id.tv_title, gank.desc)
                holder.setText(R.id.tv_date, gank.publishedAt.toLocalTime())
                holder.setText(R.id.tv_who, gank.who)
            }
        })
        addItemViewDelegate(TYPE_HEADER, object : ItemViewDelegate<Gank> {
            override fun getItemViewLayoutId(): Int {
                return R.layout.layout_item_gank_history
            }

            override fun isForViewType(item: Gank, position: Int): Boolean {
                return getItemViewType(position) == TYPE_HEADER
            }

            override fun convert(holder: ViewHolder, gank: Gank, position: Int) {
                holder.setText(R.id.tv, gank.type)
            }
        })
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItemAt(position).isHeader) {
            TYPE_HEADER
        } else TYPE_CONTENT

    }

    companion object {
        private val TYPE_HEADER = 0
        private val TYPE_CONTENT = 1
    }
}
