package com.ayvytr.mob.presenter

import com.ayvytr.commonlibrary.BaseObserver
import com.ayvytr.commonlibrary.bean.WechatCategory
import com.ayvytr.mob.contract.WechatCategoryContract
import com.ayvytr.mvp.BasePresenter
import com.ayvytr.mvprxlifecycle.kotlin.bindToLifecycle
import com.ayvytr.rxlifecycle.RxUtils

class WechatCategoryPresenter : BasePresenter<WechatCategoryContract.Model, WechatCategoryContract.View> {

    constructor() {
    }

    constructor(model: WechatCategoryContract.Model, view: WechatCategoryContract.View) : super(model, view) {
    }

    constructor(view: WechatCategoryContract.View) : super(view) {
    }

    fun requestWechatCategory() {
        mModel.requestWechatCategory()
            .bindToLifecycle(mView)
            .compose(RxUtils.ofDefault())
            .subscribe(object : BaseObserver<WechatCategory>() {
                override fun onNext(t: WechatCategory) {
                    if (t.isSucceed()) {
                        mView.showWechatCategory(t)
                    } else {
                        mView.showWechatCategoryFailed(t.msg)
                    }
                }
            })
    }
}
