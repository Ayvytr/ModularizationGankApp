package com.ayvytr.knowledge.contract

import com.ayvytr.commonlibrary.bean.GankHistory
import com.ayvytr.mvp.IModel
import com.ayvytr.mvp.IView

import io.reactivex.Observable

/**
 * @author admin
 */
class GankHistoryContract {
    interface Model : IModel {
        val gankHistory: Observable<GankHistory>
    }

    interface View : IView {
        fun showGankHistory(results: List<String>)
    }
}
