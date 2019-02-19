package com.ayvytr.mob.adapter

import android.content.Context
import com.ayvytr.baseadapter.ViewHolder
import com.ayvytr.baseadapter.wrapper.EmptyWrapperAdapter
import com.ayvytr.mob.R
import com.ayvytr.mob.TodayInHistory

/**
 * @author Do
 */
class TodayInHistoryAdapter(context: Context) :
    EmptyWrapperAdapter<TodayInHistory.ResultBean>(context, R.layout.item_today_in_history) {

    override fun convert(holder: ViewHolder, t: TodayInHistory.ResultBean, position: Int) {
        holder.setText(R.id.tvTitle, t.title)
        holder.setText(R.id.tvContent, t.event)
    }

}