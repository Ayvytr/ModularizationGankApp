package com.ayvytr.mob.contract

import com.ayvytr.commonlibrary.bean.WechatArticle
import com.ayvytr.mvp.IModel
import com.ayvytr.mvp.IView
import io.reactivex.Observable

class WechatArticleContract {
    interface Model : IModel {
        fun requestWechatArticle(cid: String, currentPage: Int, pageSize: Int): Observable<WechatArticle>
    }

    interface View : IView {
        abstract fun showWechatArticle(t: WechatArticle)
    }
}
