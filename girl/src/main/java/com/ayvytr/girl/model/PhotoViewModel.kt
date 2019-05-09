package com.ayvytr.girl.model

import com.ayvytr.commonlibrary.api.GankApi
import com.ayvytr.girl.contract.PhotoViewContract
import com.ayvytr.network.ApiClient

import io.reactivex.Observable
import okhttp3.ResponseBody

/**
 * @author admin
 */
class PhotoViewModel : PhotoViewContract.Model {

    private var mGankApi: GankApi? = null

    init {
        mGankApi = ApiClient.getInstance().create(GankApi::class.java)
    }

    override fun onDestroy() {
        mGankApi = null
    }

    override fun getImage(url: String): Observable<ResponseBody> {
        return mGankApi!!.getImageData(url)
    }
}
