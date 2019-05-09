package com.ayvytr.girl.model

import com.ayvytr.commonlibrary.GankType
import com.ayvytr.commonlibrary.bean.BaseGank
import com.ayvytr.commonlibrary.api.GankApi
import com.ayvytr.girl.contract.GirlsContract
import com.ayvytr.network.ApiClient

import io.reactivex.Observable

/**
 * @author admin
 */
class GirlsModel : GirlsContract.Model {

    private var mGankApi: GankApi? = null

    init {
        mGankApi = ApiClient.getInstance().create(GankApi::class.java)
    }

    override fun onDestroy() {
        mGankApi = null
    }

    override fun getGankMm(pageSize: Int, currentPage: Int): Observable<BaseGank> {
        return mGankApi!!.getDataByType(GankType.GIRLS.toString(), pageSize, currentPage)
    }
}
