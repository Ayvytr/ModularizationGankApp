package com.ayvytr.knowledge.model;

import com.ayvytr.commonlibrary.bean.GankHistoryContent;
import com.ayvytr.commonlibrary.server.GankApi;
import com.ayvytr.knowledge.contract.GankHistoryContentContract;
import com.ayvytr.network.ApiClient;

import io.reactivex.Observable;

/**
 * @author admin
 */
public class GankHistoryContentModel implements GankHistoryContentContract.Model {
    private GankApi mGankApi;

    public GankHistoryContentModel() {
        mGankApi = ApiClient.getInstance().create(GankApi.class);
    }

    @Override
    public void onDestroy() {
        mGankApi = null;
    }

    @Override
    public Observable<GankHistoryContent> getGankByDate(String year, String month, String dayOfMonth) {
        return mGankApi.getDataByDate(year, month, dayOfMonth);
    }
}
