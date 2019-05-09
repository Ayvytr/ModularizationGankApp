package com.ayvytr.knowledge.datasource;

import android.arch.paging.DataSource;

import com.ayvytr.commonlibrary.bean.Gank;

public class AndroidDataSourceFactory extends DataSource.Factory<Integer, Gank> {

    @Override
    public DataSource<Integer, Gank> create() {
        return new AndroidDataSource();
    }

}