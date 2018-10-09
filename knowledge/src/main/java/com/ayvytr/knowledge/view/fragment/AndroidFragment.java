package com.ayvytr.knowledge.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ayvytr.knowledge.R;
import com.ayvytr.knowledge.contract.AndroidContract;
import com.ayvytr.knowledge.presenter.AndroidPresenter;
import com.ayvytr.mvp.BaseMvpFragment;

/**
 * @author admin
 */
public class AndroidFragment extends BaseMvpFragment<AndroidPresenter> implements AndroidContract.View {
    @Override
    protected AndroidPresenter getPresenter() {
        return new AndroidPresenter(this);
    }

    @Override
    public void initExtra() {

    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public int getContentViewRes() {
        return R.layout.fragment_android;
    }
}
