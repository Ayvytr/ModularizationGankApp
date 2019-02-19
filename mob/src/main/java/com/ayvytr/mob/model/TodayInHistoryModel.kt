package com.ayvytr.mob.model

import com.ayvytr.commonlibrary.api.MobApi
import com.ayvytr.commonlibrary.client.MobClient
import com.ayvytr.mob.TodayInHistory
import com.ayvytr.mob.contract.TodayInHistoryContract
import io.reactivex.Observable
import java.util.*

class TodayInHistoryModel : TodayInHistoryContract.Model {
    val api = MobClient.getInstance().create(MobApi::class.java)

    override fun requestTodayInHistory(): Observable<TodayInHistory> {
        val calendar = Calendar.getInstance()
        return api.todayOfHistory(MobApi.API_KEY, String.format("%02d%02d", calendar.get(Calendar.MONTH + 1),
                                                                calendar.get(Calendar.DAY_OF_MONTH)))
    }

    override fun onDestroy() {

    }
}
