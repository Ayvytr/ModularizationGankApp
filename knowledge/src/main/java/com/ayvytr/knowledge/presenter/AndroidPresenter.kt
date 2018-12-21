package com.ayvytr.knowledge.presenter


import com.ayvytr.commonlibrary.BaseObserver
import com.ayvytr.commonlibrary.bean.BaseGank
import com.ayvytr.knowledge.R
import com.ayvytr.knowledge.contract.AndroidContract
import com.ayvytr.knowledge.model.AndroidModel
import com.ayvytr.mvp.BasePresenter
import com.ayvytr.rxlifecycle.RxUtils

/**
 * @author admin
 */
class AndroidPresenter(rootView: AndroidContract.View) :
    BasePresenter<AndroidContract.Model, AndroidContract.View>(AndroidModel(), rootView) {

    fun requestGankByType(gankType: String, pageSize: Int, currentPage: Int) {
        mModel.getGankByType(gankType, pageSize, currentPage)
            .compose(RxUtils.ofDefault(mView))
            .compose(RxUtils.bindToLifecycle(mView))
            .subscribe(object : BaseObserver<BaseGank>(this@AndroidPresenter) {
                override fun onNext(gank: BaseGank) {
                    if (gank.isError) {
                        mView.showError(R.string.request_data_error)
                    } else {
                        mView.showGank(gank)
                    }
                }
            })
    }
}
