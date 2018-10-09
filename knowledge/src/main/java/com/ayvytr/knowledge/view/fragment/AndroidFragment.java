package com.ayvytr.knowledge.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ayvytr.commonlibrary.view.BaseAdapter;
import com.ayvytr.knowledge.R;
import com.ayvytr.knowledge.R2;
import com.ayvytr.knowledge.adapter.AndroidAdapter;
import com.ayvytr.knowledge.contract.AndroidContract;
import com.ayvytr.knowledge.model.bean.AndroidData;
import com.ayvytr.knowledge.presenter.AndroidPresenter;
import com.ayvytr.mvp.BaseMvpFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author admin
 */
public class AndroidFragment extends BaseMvpFragment<AndroidPresenter> implements AndroidContract.View {
    @BindView(R2.id.rv_list)
    RecyclerView mRvList;
    @BindView(R2.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    private BaseAdapter androidAdapter;

    @Override
    protected AndroidPresenter getPresenter() {
        return new AndroidPresenter(this);
    }

    @Override
    public void initExtra() {

    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mRvList.setLayoutManager(new LinearLayoutManager(getContext()));
        List<AndroidData> list = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            list.add(new AndroidData());
        }
        androidAdapter = new AndroidAdapter(getContext(), R.layout.layout_item_android, list);
        mRvList.setAdapter(androidAdapter);
        androidAdapter.setEmptyView(R.layout.layout_empty);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public int getContentViewRes() {
        return R.layout.fragment_android;
    }
}
