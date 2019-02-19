package com.ayvytr.mob.contract

import com.ayvytr.commonlibrary.bean.WechatCategory
import com.ayvytr.mvp.IModel
import com.ayvytr.mvp.IView
import io.reactivex.Observable

class WechatCategoryContract {

    interface Model : IModel {
        fun requestWechatCategory(): Observable<WechatCategory>
    }

    interface View : IView {
        fun showWechatCategory(wechatCategory: WechatCategory)
        fun showWechatCategoryFailed(msg: String?)
    }
}
