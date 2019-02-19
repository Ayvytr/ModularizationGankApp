package com.ayvytr.mob.model

import com.ayvytr.commonlibrary.api.MobApi
import com.ayvytr.commonlibrary.bean.WechatCategory
import com.ayvytr.commonlibrary.client.MobClient
import com.ayvytr.mob.contract.WechatCategoryContract
import io.reactivex.Observable

class WechatCategoryModel : WechatCategoryContract.Model {
    val api = MobClient.getInstance().create(MobApi::class.java)

    override fun requestWechatCategory(): Observable<WechatCategory> {
        return api.getWechatCategory(MobApi.API_KEY)
    }

    override fun onDestroy() {

    }
}
