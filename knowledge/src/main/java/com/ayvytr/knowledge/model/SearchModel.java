package com.ayvytr.knowledge.model;

import com.ayvytr.commonlibrary.bean.BaseGank;
import com.ayvytr.commonlibrary.server.GankApi;
import com.ayvytr.knowledge.contract.SearchContract;
import com.ayvytr.network.ApiClient;

import io.reactivex.Observable;

/**
 * @author wangdunwei
 */
public class SearchModel implements SearchContract.Model {
    private GankApi mGankApi;

    public SearchModel() {
        mGankApi = ApiClient.getInstance().create(GankApi.class);
    }

    @Override
    public void onDestroy() {
        mGankApi = null;
    }

    @Override
    public Observable<BaseGank> search(String key, int currentPage, int pageSize) {
        return mGankApi.search(key, "all", pageSize, currentPage);
    }
}
