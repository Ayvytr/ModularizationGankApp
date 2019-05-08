package com.ayvytr.mob.adapter

import android.content.Context
import com.ayvytr.baseadapter.CommonAdapter
import com.ayvytr.baseadapter.ViewHolder
import com.ayvytr.mob.R
import com.ayvytr.commonlibrary.bean.TodayInHistory
import java.text.SimpleDateFormat

/**
 * @author Do
 */
class TodayInHistoryAdapter(context: Context) :
    CommonAdapter<TodayInHistory.ResultBean>(context, R.layout.item_today_in_history) {

    override fun convert(holder: ViewHolder, t: TodayInHistory.ResultBean, position: Int) {
        val date = SimpleDateFormat("yyyyMMdd").parse(t.date)
        val format = SimpleDateFormat("yyyy-MM-dd").format(date)
        holder.setText(R.id.tvTitle, "$format  ${t.title}")
        holder.setText(R.id.tvContent, t.event)
    }

}