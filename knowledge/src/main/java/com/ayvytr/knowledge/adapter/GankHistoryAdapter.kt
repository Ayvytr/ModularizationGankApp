package com.ayvytr.knowledge.adapter

import android.content.Context
import com.ayvytr.baseadapter.CommonAdapter
import com.ayvytr.baseadapter.ViewHolder
import com.ayvytr.knowledge.R

/**
 * @author admin
 */
class GankHistoryAdapter(context: Context) : CommonAdapter<String>(context, R.layout.layout_item_gank_history) {

    override fun convert(holder: ViewHolder, s: String, position: Int) {
        holder.setText(R.id.tv, s)
    }
}
