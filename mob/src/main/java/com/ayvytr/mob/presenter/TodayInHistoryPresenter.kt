package com.ayvytr.mob.presenter

import com.ayvytr.commonlibrary.BaseObserver
import com.ayvytr.mob.TodayInHistory
import com.ayvytr.mob.contract.TodayInHistoryContract
import com.ayvytr.mvp.BasePresenter
import com.ayvytr.mvprxlifecycle.kotlin.bindToLifecycle
import com.ayvytr.rxlifecycle.RxUtils

class TodayInHistoryPresenter : BasePresenter<TodayInHistoryContract.Model, TodayInHistoryContract.View> {
    fun requestTodayInHistory() {
        mModel.requestTodayInHistory()
            .bindToLifecycle(mView)
            .compose(RxUtils.ofDefault())
            .subscribe(object: BaseObserver<TodayInHistory>() {
                override fun onNext(t: TodayInHistory) {
                    if(t.isSucceed()) {
                        mView.showTodayInHistory(t)
                    } else {
                        mView.showError(t.msg)
                    }
                }
            })
    }

    constructor() {
    }

    constructor(model: TodayInHistoryContract.Model, view: TodayInHistoryContract.View) : super(model, view) {
        ;
    }

    constructor(view: TodayInHistoryContract.View) : super(view) {
    }
}
