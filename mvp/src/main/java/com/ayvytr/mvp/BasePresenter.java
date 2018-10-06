package com.ayvytr.mvp;

import android.arch.lifecycle.LifecycleObserver;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author ayvytr
 */
public class BasePresenter<M extends IModel, V extends IView> implements IPresenter, LifecycleObserver {
    protected M mModel;
    protected V mView;

    protected CompositeDisposable mCompositeDisposable;

    public BasePresenter() {
        onCreate();
    }

    /**
     * 如果当前页面同时需要 Model 层和 View 层,则使用此构造函数(默认)
     *
     * @param model
     * @param rootView
     */
    public BasePresenter(M model, V rootView) {
        Preconditions.checkNotNull(model, "%s cannot be null", IModel.class.getName());
        Preconditions.checkNotNull(rootView, "%s cannot be null", IView.class.getName());
        this.mModel = model;
        this.mView = rootView;
        onCreate();
    }

    /**
     * 如果当前页面不需要操作数据,只需要 View 层,则使用此构造函数
     *
     * @param rootView
     */
    public BasePresenter(V rootView) {
        Preconditions.checkNotNull(rootView, "%s cannot be null", IView.class.getName());
        this.mView = rootView;
        onCreate();
    }


    @Override
    public void onCreate() {
        //将 LifecycleObserver 注册给 LifecycleOwner 后 @OnLifecycleEvent 才可以正常使用
//        if (mView != null && mView instanceof LifecycleOwner) {
//            ((LifecycleOwner) mView).getLifecycle().addObserver(this);
//            if (mModel!= null && mModel instanceof LifecycleObserver){
//                ((LifecycleOwner) mView).getLifecycle().addObserver((LifecycleObserver) mModel);
//            }
//        }
    }

    @Override
    public void onDestroy() {
        dispose();
        if (mModel != null) {
            mModel.onDestroy();
            this.mModel = null;
        }
    }

    public void addDispose(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);//将所有 Disposable 放入集中处理
    }

    /**
     * 停止集合中正在执行的 RxJava 任务
     */
    public void dispose() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();//保证 Activity 结束时取消所有正在执行的订阅
            mCompositeDisposable.dispose();
            mCompositeDisposable = null;
        }
    }
}
