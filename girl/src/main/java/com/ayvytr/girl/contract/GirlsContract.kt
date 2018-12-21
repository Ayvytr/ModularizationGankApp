package com.ayvytr.girl.contract

import com.ayvytr.commonlibrary.bean.BaseGank
import com.ayvytr.mvp.IModel
import com.ayvytr.mvp.IView

import io.reactivex.Observable

/**
 * @author admin
 */
class GirlsContract {
    interface Model : IModel {
        fun getGankMm(pageSize: Int, currentPage: Int): Observable<BaseGank>
    }

    interface View : IView {
        fun showGankMm(gank: BaseGank)
    }
}
