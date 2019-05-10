package com.ayvytr.knowledge.datasource;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.ayvytr.commonlibrary.bean.Gank;

public class AndroidDataSourceFactory extends DataSource.Factory<Integer, Gank> {

    private final MutableLiveData<AndroidDataSource> sourceLiveData;
    private String gankType;

    public AndroidDataSourceFactory(String gankType) {
        this.gankType = gankType;
        sourceLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource<Integer, Gank> create() {
        AndroidDataSource androidDataSource = new AndroidDataSource(gankType);
        sourceLiveData.postValue(androidDataSource);
        return androidDataSource;
    }

    public MutableLiveData<AndroidDataSource> getSourceLiveData() {
        return sourceLiveData;
    }
}