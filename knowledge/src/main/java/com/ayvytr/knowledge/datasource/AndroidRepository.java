package com.ayvytr.knowledge.datasource;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.ayvytr.commonlibrary.bean.Gank;
import com.ayvytr.commonlibrary.bean.Listing;
import com.ayvytr.commonlibrary.util.NetworkState;
import com.ayvytr.commonlibrary.util.Paging;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/**
 * @author wangdunwei
 */
public class AndroidRepository {


    public Listing<Gank> getGankByType(String gankType) {
        final AndroidDataSourceFactory factory = new AndroidDataSourceFactory(gankType);

        LiveData<PagedList<Gank>> pagedListLiveData = new LivePagedListBuilder<>(factory, Paging.config).build();

        LiveData<NetworkState> networkStateLiveData = Transformations.switchMap(factory.getSourceLiveData(),
                new Function<AndroidDataSource, LiveData<NetworkState>>() {
                    @Override
                    public LiveData<NetworkState> apply(AndroidDataSource input) {
                        return input.getNetworkState();
                    }
                });

        return new Listing<>(pagedListLiveData, networkStateLiveData, new Function0() {
            @Override
            public final Object invoke() {
                factory.getSourceLiveData().getValue().invalidate();
                return Unit.INSTANCE;
            }
        });
    }
}
