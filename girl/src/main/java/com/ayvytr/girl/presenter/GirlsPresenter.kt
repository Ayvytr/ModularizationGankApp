package com.ayvytr.girl.presenter

import com.ayvytr.commonlibrary.BaseObserver
import com.ayvytr.commonlibrary.bean.BaseGank
import com.ayvytr.girl.R
import com.ayvytr.girl.contract.GirlsContract
import com.ayvytr.girl.model.GirlsModel
import com.ayvytr.mvp.BasePresenter
import com.ayvytr.rxlifecycle.RxUtils

/**
 * @author admin
 */
class GirlsPresenter(view: GirlsContract.View) :
    BasePresenter<GirlsContract.Model, GirlsContract.View>(GirlsModel(), view) {

    override fun onCreate() {

    }

    override fun onDestroy() {

    }

    fun requestGankMm(pageSize: Int, currentPage: Int) {
        mModel.getGankMm(pageSize, currentPage)
            .compose(RxUtils.ofDefault(mView))
            .compose(RxUtils.bindToLifecycle(mView))
            .subscribe(object : BaseObserver<BaseGank>(this@GirlsPresenter) {
                override fun onNext(gank: BaseGank) {
                    if (gank.isError) {
                        mView.showError(R.string.request_data_error)
                    } else {
                        mView.showGankMm(gank, currentPage)
                    }
                }
            })
    }
}
