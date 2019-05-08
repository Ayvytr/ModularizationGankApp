package com.ayvytr.mob.presenter

import com.ayvytr.commonlibrary.BaseObserver
import com.ayvytr.mob.R
import com.ayvytr.commonlibrary.bean.TodayInHistory
import com.ayvytr.mob.contract.TodayInHistoryContract
import com.ayvytr.mvp.BasePresenter
import com.ayvytr.mvprxlifecycle.kotlin.bindToLifecycle
import com.ayvytr.rxlifecycle.RxUtils
import io.reactivex.Observable

class TodayInHistoryPresenter : BasePresenter<TodayInHistoryContract.Model, TodayInHistoryContract.View> {
    fun requestTodayInHistory() {
        mModel.requestTodayInHistory()
            .bindToLifecycle(mView)
            .compose(RxUtils.ofDefault())
            .concatMap<TodayInHistory.ResultBean> { todayInHistory -> Observable.fromIterable(todayInHistory.result!!) }
            .toSortedList { o1, o2 -> o1.date!!.compareTo(o2.date!!) }
            .toObservable()
            .subscribe(object : BaseObserver<List<TodayInHistory.ResultBean>>() {
                override fun onNext(t: List<TodayInHistory.ResultBean>) {
                    mView.showTodayInHistory(t)
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    mView.showError(R.string.get_today_in_history_error)
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
