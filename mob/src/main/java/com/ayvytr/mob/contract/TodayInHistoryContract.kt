package com.ayvytr.mob.contract

import com.ayvytr.mob.TodayInHistory
import com.ayvytr.mvp.IModel
import com.ayvytr.mvp.IView
import io.reactivex.Observable

class TodayInHistoryContract {
    interface Model : IModel {
        fun requestTodayInHistory(): Observable<TodayInHistory>
    }

    interface View : IView {
        abstract fun showTodayInHistory(t: List<TodayInHistory.ResultBean>)
    }
}
