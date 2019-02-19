package com.ayvytr.mob.presenter

import com.ayvytr.mvp.BasePresenter
import com.ayvytr.mob.contract.WechatArticleContract

class WechatArticlePresenter : BasePresenter<WechatArticleContract.Model, WechatArticleContract.View> {
    constructor() {
    }

    constructor(model: WechatArticleContract.Model, view: WechatArticleContract.View) : super(model, view) {
        ;
    }

    constructor(view: WechatArticleContract.View) : super(view) {
    }
}
