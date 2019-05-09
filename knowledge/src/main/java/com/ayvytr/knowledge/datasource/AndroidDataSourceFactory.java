package com.ayvytr.knowledge.datasource;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PositionalDataSource;
import android.support.annotation.NonNull;

import com.ayvytr.commonlibrary.BaseObserver;
import com.ayvytr.commonlibrary.GankType;
import com.ayvytr.commonlibrary.api.GankApi;
import com.ayvytr.commonlibrary.bean.BaseGank;
import com.ayvytr.commonlibrary.bean.Gank;
import com.ayvytr.commonlibrary.util.NetworkState;
import com.ayvytr.commonlibrary.util.Paging;
import com.ayvytr.network.ApiClient;
import com.ayvytr.rxlifecycle.RxUtils;

import java.util.ArrayList;
import java.util.List;

public class AndroidDataSourceFactory extends DataSource.Factory<Integer, Gank> {
    public static MutableLiveData<NetworkState> networkState = new MutableLiveData<>();

    @Override
    public DataSource<Integer, Gank> create() {
        return new MyDataSource();
    }

    private class MyDataSource extends PositionalDataSource<Gank> {
        private final GankApi gankApi;
        private int currentPage;

        public MyDataSource() {
            gankApi = ApiClient.getInstance().create(GankApi.class);
        }

        @Override
        public void loadInitial(@NonNull LoadInitialParams params, @NonNull final LoadInitialCallback<Gank> callback) {
            networkState.postValue(NetworkState.LOADING);

            gankApi.getDataByType(GankType.ANDROID.toString(), Paging.DEFAULT_PAGE_SIZE, 1)
                   .compose(RxUtils.<BaseGank>ofDefault())
                   .subscribe(new BaseObserver<BaseGank>() {
                       @Override
                       public void onNext(BaseGank baseGank) {
                           networkState.postValue(NetworkState.of(baseGank.isSucceed()));
                           if(baseGank.isSucceed()) {
                               currentPage = 1;
                               callback.onResult(baseGank.getResults(), 0, baseGank.getResults().size());
                           }
                       }
                   });
        }

        @Override
        public void loadRange(@NonNull final LoadRangeParams params, @NonNull final LoadRangeCallback<Gank> callback) {
            networkState.postValue(NetworkState.LOADING);
            final int newPage = currentPage + 1;


            gankApi.getDataByType(GankType.ANDROID.toString(), Paging.DEFAULT_PAGE_SIZE, newPage)
                   .compose(RxUtils.<BaseGank>ofDefault())
                   .subscribe(new BaseObserver<BaseGank>() {
                       @Override
                       public void onNext(BaseGank baseGank) {
                           networkState.postValue(NetworkState.of(baseGank.isSucceed()));
                           if(baseGank.isSucceed()) {
                               currentPage = newPage;
                               callback.onResult(baseGank.getResults());
                           }
                       }
                   });
        }

        /**
         * 假设这里需要做一些后台线程的数据加载任务。
         *
         * @param startPosition
         * @param count
         * @return
         */
        private List<Gank> loadData(int startPosition, int count) {
            List<Gank> list = new ArrayList();

            for(int i = 0; i < count; i++) {
                Gank data = new Gank();
//                data.id = startPosition + i;
//                data.content = "测试的内容=" + data.id;
                list.add(data);
            }

            return list;
        }

    }
}