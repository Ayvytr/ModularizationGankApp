package com.ayvytr.knowledge.contract

import com.ayvytr.commonlibrary.bean.BaseGank
import com.ayvytr.commonlibrary.bean.Gank
import com.ayvytr.mvp.IModel
import com.ayvytr.mvp.IView

import io.reactivex.Observable


/**
 * @author wangdunwei
 */
class SearchContract {
    interface Model : IModel {
        fun search(key: String, currentPage: Int, pageSize: Int): Observable<BaseGank>
    }

    interface View : IView {
        fun showSearchResult(results: List<Gank>)
    }
}
