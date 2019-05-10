package com.ayvytr.knowledge.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;

import com.ayvytr.commonlibrary.bean.Gank;
import com.ayvytr.commonlibrary.bean.Listing;
import com.ayvytr.commonlibrary.util.NetworkState;
import com.ayvytr.knowledge.datasource.AndroidRepository;

/**
 * @author wangdunwei
 */
public class AndroidViewModel extends ViewModel {

    private AndroidRepository androidRepository;

    private MutableLiveData<String> gankTypeLiveData = new MutableLiveData<>();

    private LiveData<NetworkState> networkState;

    private final LiveData<Listing<Gank>> repoResult;
    private final LiveData<PagedList<Gank>> posts;

    public AndroidViewModel(final AndroidRepository androidRepository) {
        this.androidRepository = androidRepository;

        repoResult = Transformations.map(gankTypeLiveData, new Function<String, Listing<Gank>>() {
            @Override
            public Listing<Gank> apply(String input) {
                return androidRepository.getGankByType(input);
            }
        });
        posts = Transformations.switchMap(repoResult, new Function<Listing<Gank>, LiveData<PagedList<Gank>>>() {
            @Override
            public LiveData<PagedList<Gank>> apply(Listing<Gank> input) {
                return input.getPagedList();
            }
        });

        networkState = Transformations
                .switchMap(repoResult, new Function<Listing<Gank>, LiveData<NetworkState>>() {
                    @Override
                    public LiveData<NetworkState> apply(Listing<Gank> input) {
                        return input.getNetworkState();
                    }
                });
    }


    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<Listing<Gank>> getRepoResult() {
        return repoResult;
    }

    public LiveData<PagedList<Gank>> getPosts() {
        return posts;
    }

    public void showGankByType(String gankType) {
        gankTypeLiveData.setValue(gankType);
    }

    public void refresh() {
        repoResult.getValue().getRefresh().invoke();
    }
}
