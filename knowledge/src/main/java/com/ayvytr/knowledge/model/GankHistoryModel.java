package com.ayvytr.knowledge.model;

import com.ayvytr.commonlibrary.bean.GankHistory;
import com.ayvytr.commonlibrary.server.GankApi;
import com.ayvytr.knowledge.contract.GankHistoryContract;
import com.ayvytr.network.ApiClient;

import io.reactivex.Observable;

/**
 * @author admin
 */
public class GankHistoryModel implements GankHistoryContract.Model {
    private GankApi mGankApi;

    public GankHistoryModel() {
        mGankApi = ApiClient.getInstance().create(GankApi.class);
    }

    @Override
    public void onDestroy() {
        mGankApi = null;
    }

    @Override
    public Observable<GankHistory> getGankHistory() {
        return mGankApi.getPublishDate();
    }
}
