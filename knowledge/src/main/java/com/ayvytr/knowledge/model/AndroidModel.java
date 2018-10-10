package com.ayvytr.knowledge.model;

import com.ayvytr.commonlibrary.GankType;
import com.ayvytr.commonlibrary.bean.BaseGank;
import com.ayvytr.commonlibrary.server.GankApi;
import com.ayvytr.knowledge.contract.AndroidContract;
import com.ayvytr.network.ApiClient;

import io.reactivex.Observable;

/**
 * @author admin
 */
public class AndroidModel implements AndroidContract.Model {

    private final GankApi mGankApi;

    public AndroidModel() {
        mGankApi = ApiClient.getInstance().create(GankApi.class);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public Observable<BaseGank> getAndroidGank(int pageSize, int currentPage) {
        return mGankApi.getDataByType(GankType.ANDROID.toString(), pageSize, currentPage);
    }
}
