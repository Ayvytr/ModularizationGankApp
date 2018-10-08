package com.ayvytr.mvp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author admin
 */
public abstract class BaseMvpFragment<P extends IPresenter> extends RxFragment
        implements IView, IInit {

    protected P mPresenter;
    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getContentViewRes(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = getPresenter();
        mUnbinder = ButterKnife.bind(this, view);
        initExtra();
        initView(savedInstanceState);
        initData(savedInstanceState);
    }

    protected abstract P getPresenter();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }
    }

    @Override
    public void showMessage(String message) {
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }
}
