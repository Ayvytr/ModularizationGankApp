package com.ayvytr.girl.model;

import com.ayvytr.commonlibrary.server.GankApi;
import com.ayvytr.girl.contract.PhotoViewContract;
import com.ayvytr.network.ApiClient;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * @author admin
 */
public class PhotoViewModel implements PhotoViewContract.Model {

    private GankApi mGankApi;

    public PhotoViewModel() {
        mGankApi = ApiClient.getInstance().create(GankApi.class);
    }

    @Override
    public void onDestroy() {
        mGankApi = null;
    }

    @Override
    public Observable<ResponseBody> getImage(String url) {
        return mGankApi.getImageData(url);
    }
}
