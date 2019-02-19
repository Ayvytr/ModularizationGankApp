package com.ayvytr.mob.presenter

import com.ayvytr.commonlibrary.BaseObserver
import com.ayvytr.commonlibrary.bean.WechatArticle
import com.ayvytr.mob.contract.WechatArticleContract
import com.ayvytr.mvp.BasePresenter
import com.ayvytr.mvprxlifecycle.kotlin.bindToLifecycle
import com.ayvytr.rxlifecycle.RxUtils

class WechatArticlePresenter : BasePresenter<WechatArticleContract.Model, WechatArticleContract.View> {
    constructor() {
    }

    constructor(model: WechatArticleContract.Model, view: WechatArticleContract.View) : super(model, view) {
        ;
    }

    constructor(view: WechatArticleContract.View) : super(view) {
    }

    fun requestWechatArticle(mCid: String, currentPage: Int, pageSize: Int) {
        mModel.requestWechatArticle(mCid, currentPage, pageSize)
            .bindToLifecycle(mView)
            .compose(RxUtils.ofDefault())
            .subscribe(object: BaseObserver<WechatArticle>() {
                override fun onNext(t: WechatArticle) {
                    if(t.isSucceed()) {
                        mView.showWechatArticle(t)
                    } else {
                        mView.showError(t.msg)
                    }
                }
            })
    }
}
