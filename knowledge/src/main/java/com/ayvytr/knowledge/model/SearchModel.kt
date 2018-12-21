package com.ayvytr.knowledge.model

import com.ayvytr.commonlibrary.bean.BaseGank
import com.ayvytr.commonlibrary.server.GankApi
import com.ayvytr.knowledge.contract.SearchContract
import com.ayvytr.network.ApiClient

import io.reactivex.Observable

/**
 * @author wangdunwei
 */
class SearchModel : SearchContract.Model {
    private var mGankApi: GankApi? = null

    init {
        mGankApi = ApiClient.getInstance().create(GankApi::class.java)
    }

    override fun onDestroy() {
        mGankApi = null
    }

    override fun search(key: String, currentPage: Int, pageSize: Int): Observable<BaseGank> {
        return mGankApi!!.search(key, "all", pageSize, currentPage)
    }
}
