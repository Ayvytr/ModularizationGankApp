package com.ayvytr.knowledge.model

import com.ayvytr.commonlibrary.bean.GankHistoryContent
import com.ayvytr.commonlibrary.server.GankApi
import com.ayvytr.knowledge.contract.GankHistoryContentContract
import com.ayvytr.network.ApiClient

import io.reactivex.Observable

/**
 * @author admin
 */
class GankHistoryContentModel : GankHistoryContentContract.Model {
    private var mGankApi: GankApi? = null

    init {
        mGankApi = ApiClient.getInstance().create(GankApi::class.java)
    }

    override fun onDestroy() {
        mGankApi = null
    }

    override fun getGankByDate(year: String, month: String, dayOfMonth: String): Observable<GankHistoryContent> {
        return mGankApi!!.getDataByDate(year, month, dayOfMonth)
    }
}
