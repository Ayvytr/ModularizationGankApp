package com.ayvytr.knowledge.model;

import com.ayvytr.commonlibrary.bean.BaseGank;
import com.ayvytr.commonlibrary.server.GankApi;
import com.ayvytr.knowledge.contract.AndroidContract;
import com.ayvytr.network.ApiClient;

import io.reactivex.Observable;

/**
 * @author admin
 */
public class AndroidModel implements AndroidContract.Model {

    private GankApi mGankApi;

    public AndroidModel() {
        mGankApi = ApiClient.getInstance().create(GankApi.class);
    }

    @Override
    public void onDestroy() {
        mGankApi = null;
    }

    @Override
    public Observable<BaseGank> getGankByType(String gankType, int pageSize, int currentPage) {
        return mGankApi.getDataByType(gankType, pageSize, currentPage);
    }
}
