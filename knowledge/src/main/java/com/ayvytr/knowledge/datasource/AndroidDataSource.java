package com.ayvytr.knowledge.datasource;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PositionalDataSource;
import android.support.annotation.NonNull;

import com.ayvytr.commonlibrary.BaseObserver;
import com.ayvytr.commonlibrary.api.GankApi;
import com.ayvytr.commonlibrary.bean.BaseGank;
import com.ayvytr.commonlibrary.bean.Gank;
import com.ayvytr.commonlibrary.util.NetworkState;
import com.ayvytr.commonlibrary.util.Paging;
import com.ayvytr.logger.L;
import com.ayvytr.network.ApiClient;

/**
 * TODO: 接口参数无法传
 * @author Now
 */
public class AndroidDataSource extends PositionalDataSource<Gank> {
    private final GankApi gankApi;
    private int currentPage;
    private String gankType;

    private MutableLiveData<NetworkState> networkState = new MutableLiveData<>();

    public AndroidDataSource(String gankType) {
        this.gankType = gankType;
        gankApi = ApiClient.getInstance().create(GankApi.class);
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull final LoadInitialCallback<Gank> callback) {

        gankApi.getDataByType(gankType, Paging.DEFAULT_PAGE_SIZE, 1)
               .subscribe(new BaseObserver<BaseGank>() {
                   @Override
                   public void onNext(BaseGank baseGank) {
                       L.e("1", baseGank);
                       networkState.postValue(NetworkState.of(baseGank.isSucceed()));
                       if(baseGank.isSucceed()) {
                           currentPage = 1;
                           callback.onResult(baseGank.getResults(), 0);
                       }
                   }
               });
    }

    @Override
    public void loadRange(@NonNull final LoadRangeParams params, @NonNull final LoadRangeCallback<Gank> callback) {
        final int newPage = currentPage + 1;

        gankApi.getDataByType(gankType, Paging.DEFAULT_PAGE_SIZE, newPage)
               .subscribe(new BaseObserver<BaseGank>() {
                   @Override
                   public void onNext(BaseGank baseGank) {
                       L.e(newPage, "loadRange", baseGank);
                       networkState.postValue(NetworkState.of(baseGank.isSucceed()));
                       if(baseGank.isSucceed()) {
                           currentPage = newPage;
                           callback.onResult(baseGank.getResults());
                       }
                   }
               });
    }

    public MutableLiveData<NetworkState> getNetworkState() {
        return networkState;
    }
}
