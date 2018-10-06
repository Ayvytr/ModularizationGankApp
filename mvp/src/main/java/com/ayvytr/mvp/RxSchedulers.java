package com.ayvytr.mvp;

import android.support.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 封装常用的Rx线程切换处理
 * </br>
 * Date: 2018/7/23 16:29
 *
 * @author hemin
 */
public class RxSchedulers {
    /**
     * 封装线程切换和loading显示
     *
     * @param view IView
     * @param <T>  返回数据
     * @return ObservableTransformer
     */
    @NonNull
    public static <T> ObservableTransformer<T, T> applySchedulers(final IView view) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                               .doOnSubscribe(new Consumer<Disposable>() {
                                   @Override
                                   public void accept(Disposable disposable) {
                                       // 显示loading
                                       if(view != null) {
                                           view.showLoading();
                                       }
                                   }
                               })
                               //设置doOnSubscribe对应的线程为主线程
                               .subscribeOn(AndroidSchedulers.mainThread())
                               .observeOn(AndroidSchedulers.mainThread())
                               .doFinally(new Action() {
                                   @Override
                                   public void run() {
                                       // 隐藏loading
                                       if(view != null) {
                                           view.hideLoading();
                                       }
                                   }
                               });
            }
        };
    }

}
