package com.ayvytr.knowledge.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.ayvytr.commonlibrary.bean.Gank;
import com.ayvytr.commonlibrary.util.Paging;
import com.ayvytr.knowledge.datasource.AndroidDataSourceFactory;

/**
 * @author wangdunwei
 */
public class AndroidViewModel extends ViewModel {
    private LiveData<PagedList<Gank>> androidLiveData =
            new LivePagedListBuilder<>(new AndroidDataSourceFactory(), Paging.config).build();

    public LiveData<PagedList<Gank>> getAndroidLiveData() {
        return androidLiveData;
    }
}
