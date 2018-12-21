package com.ayvytr.knowledge.model

import com.ayvytr.commonlibrary.bean.GankHistory
import com.ayvytr.commonlibrary.server.GankApi
import com.ayvytr.knowledge.contract.GankHistoryContract
import com.ayvytr.network.ApiClient

import io.reactivex.Observable

/**
 * @author admin
 */
class GankHistoryModel : GankHistoryContract.Model {
    private var mGankApi: GankApi? = null

    override val gankHistory: Observable<GankHistory>
        get() = mGankApi!!.publishDate

    init {
        mGankApi = ApiClient.getInstance().create(GankApi::class.java)
    }

    override fun onDestroy() {
        mGankApi = null
    }
}
