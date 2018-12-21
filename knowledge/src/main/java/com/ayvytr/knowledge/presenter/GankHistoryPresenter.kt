package com.ayvytr.knowledge.presenter

import com.ayvytr.commonlibrary.BaseObserver
import com.ayvytr.commonlibrary.bean.GankHistory
import com.ayvytr.knowledge.R
import com.ayvytr.knowledge.contract.GankHistoryContract
import com.ayvytr.knowledge.model.GankHistoryModel
import com.ayvytr.mvp.BasePresenter
import com.ayvytr.rxlifecycle.RxUtils

/**
 * @author admin
 */
class GankHistoryPresenter(view: GankHistoryContract.View) :
    BasePresenter<GankHistoryContract.Model, GankHistoryContract.View>(GankHistoryModel(), view) {

    fun requestGankHistory() {
        mModel.gankHistory
            .compose(RxUtils.bindToLifecycle(mView))
            .compose(RxUtils.ofDefault(mView))
            .subscribe(object : BaseObserver<GankHistory>() {
                override fun onNext(gankHistory: GankHistory) {
                    if (gankHistory.isError) {
                        mView.showError(R.string.request_data_error)
                    } else {
                        mView.showGankHistory(gankHistory.results!!)
                    }
                }
            })
    }
}
