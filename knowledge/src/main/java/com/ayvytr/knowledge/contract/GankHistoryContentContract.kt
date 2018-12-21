package com.ayvytr.knowledge.contract

import com.ayvytr.commonlibrary.bean.GankHistoryContent
import com.ayvytr.mvp.IModel
import com.ayvytr.mvp.IView

import io.reactivex.Observable

/**
 * @author admin
 */
class GankHistoryContentContract {
    interface Model : IModel {
        fun getGankByDate(year: String, month: String, dayOfMonth: String): Observable<GankHistoryContent>
    }

    interface View : IView {
        fun showGankByDate(gankHistoryContent: GankHistoryContent)
    }
}
