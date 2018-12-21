package com.ayvytr.knowledge.presenter

import com.ayvytr.commonlibrary.BaseObserver
import com.ayvytr.commonlibrary.bean.GankHistoryContent
import com.ayvytr.knowledge.R
import com.ayvytr.knowledge.contract.GankHistoryContentContract
import com.ayvytr.knowledge.model.GankHistoryContentModel
import com.ayvytr.mvp.BasePresenter
import com.ayvytr.rxlifecycle.RxUtils

/**
 * @author admin
 */
class GankHistoryContentPresenter(view: GankHistoryContentContract.View) :
    BasePresenter<GankHistoryContentContract.Model, GankHistoryContentContract.View>(GankHistoryContentModel(), view) {

    fun requestGankByDate(date: String) {
        val split = date.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        mModel.getGankByDate(split[0], split[1], split[2])
            .compose(RxUtils.ofDefault(mView))
            .compose(RxUtils.bindToLifecycle(mView))
            .subscribe(object : BaseObserver<GankHistoryContent>() {
                override fun onNext(gankHistoryContent: GankHistoryContent) {
                    if (gankHistoryContent.isError) {
                        mView.showError(R.string.request_data_error)
                    } else {
                        mView.showGankByDate(gankHistoryContent)
                    }
                }
            })
    }
}
