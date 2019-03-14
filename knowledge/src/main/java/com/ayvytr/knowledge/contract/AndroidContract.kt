package com.ayvytr.knowledge.contract

import com.ayvytr.commonlibrary.bean.BaseGank
import com.ayvytr.mvp.IModel
import com.ayvytr.mvp.IView

import io.reactivex.Observable

/**
 * @author admin
 */
class AndroidContract {
    interface Model : IModel {
        fun getGankByType(gankType: String, pageSize: Int, currentPage: Int): Observable<BaseGank>
    }

    interface View : IView {
        fun showGank(gank: BaseGank, currentPage: Int)
    }
}
