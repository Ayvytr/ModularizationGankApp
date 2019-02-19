package com.ayvytr.mob.model

import com.ayvytr.commonlibrary.api.MobApi
import com.ayvytr.commonlibrary.bean.WechatArticle
import com.ayvytr.commonlibrary.client.MobClient
import com.ayvytr.mob.contract.WechatArticleContract
import io.reactivex.Observable

class WechatArticleModel : WechatArticleContract.Model {
    val api = MobClient.getInstance().create(MobApi::class.java)

    override fun requestWechatArticle(cid: String, currentPage: Int, pageSize: Int): Observable<WechatArticle> {
        return api.getWechatArticle(MobApi.API_KEY, cid, currentPage, pageSize)
    }

    override fun onDestroy() {

    }
}
