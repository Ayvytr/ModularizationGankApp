package com.ayvytr.knowledge.model

import com.ayvytr.commonlibrary.bean.BaseGank
import com.ayvytr.commonlibrary.api.GankApi
import com.ayvytr.knowledge.contract.AndroidContract
import com.ayvytr.network.ApiClient

import io.reactivex.Observable

/**
 * @author admin
 */
class AndroidModel : AndroidContract.Model {

    private var mGankApi: GankApi? = null

    init {
        mGankApi = ApiClient.getInstance().create(GankApi::class.java)
    }

    override fun onDestroy() {
        mGankApi = null
    }

    override fun getGankByType(gankType: String, pageSize: Int, currentPage: Int): Observable<BaseGank> {
        return mGankApi!!.getDataByType(gankType, pageSize, currentPage)
    }
}
