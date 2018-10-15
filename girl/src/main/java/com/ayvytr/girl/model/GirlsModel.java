package com.ayvytr.girl.model;

import com.ayvytr.commonlibrary.GankType;
import com.ayvytr.commonlibrary.bean.BaseGank;
import com.ayvytr.commonlibrary.server.GankApi;
import com.ayvytr.girl.contract.GirlsContract;
import com.ayvytr.network.ApiClient;

import io.reactivex.Observable;

/**
 * @author admin
 */
public class GirlsModel implements GirlsContract.Model {

    private GankApi mGankApi;

    public GirlsModel() {
        mGankApi = ApiClient.getInstance().create(GankApi.class);
    }

    @Override
    public void onDestroy() {
        mGankApi = null;
    }

    @Override
    public Observable<BaseGank> getGankMm(int pageSize, int currentPage) {
        return mGankApi.getDataByType(GankType.GIRLS.toString(), pageSize, currentPage);
    }
}
