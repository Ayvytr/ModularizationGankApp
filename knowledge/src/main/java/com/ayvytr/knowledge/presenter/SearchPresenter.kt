package com.ayvytr.knowledge.presenter

import com.ayvytr.commonlibrary.BaseObserver
import com.ayvytr.commonlibrary.bean.BaseGank
import com.ayvytr.knowledge.R
import com.ayvytr.knowledge.contract.SearchContract
import com.ayvytr.knowledge.model.SearchModel
import com.ayvytr.mvp.BasePresenter
import com.ayvytr.rxlifecycle.RxUtils


/**
 * @author wangdunwei
 */
class SearchPresenter(view: SearchContract.View) :
    BasePresenter<SearchContract.Model, SearchContract.View>(SearchModel(), view) {

    fun search(key: String, currentPage: Int, pageSize: Int) {
        mModel.search(key, currentPage, pageSize)
            .compose(RxUtils.ofDefault(mView))
            .compose(RxUtils.bindToLifecycle(mView))
            .subscribe(object : BaseObserver<BaseGank>() {
                override fun onNext(baseGank: BaseGank) {
                    if (baseGank.isError) {
                        mView.showError(R.string.search_failed)
                    } else {
                        mView.showSearchResult(baseGank.results!!)
                    }
                }
            })
    }
}
