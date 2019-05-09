package com.ayvytr.knowledge.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.ayvytr.commonlibrary.bean.Gank;
import com.ayvytr.commonlibrary.util.Paging;
import com.ayvytr.knowledge.datasource.AndroidDataSource;

/**
 * @author wangdunwei
 */
public class AndroidViewModel extends ViewModel {
    private LiveData<PagedList<Gank>> androidLiveData;
    private AndroidDataSource androidDataSource;

    public AndroidViewModel() {
        androidLiveData = new LivePagedListBuilder<>(new DataSource.Factory<Integer, Gank>() {
            @Override
            public DataSource<Integer, Gank> create() {
                //这里写法是无奈之举，需要调用invalidate实现下拉刷新
                androidDataSource = new AndroidDataSource();
                return androidDataSource;
            }
        }, Paging.config).build();
    }

    public LiveData<PagedList<Gank>> getAndroidLiveData() {
        return androidLiveData;
    }

    public AndroidDataSource getAndroidDataSource() {
        return androidDataSource;
    }
}
