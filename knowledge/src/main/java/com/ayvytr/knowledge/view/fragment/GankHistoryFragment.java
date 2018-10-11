package com.ayvytr.knowledge.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import com.ayvytr.knowledge.R;
import com.ayvytr.knowledge.adapter.GankHistoryAdapter;
import com.ayvytr.knowledge.contract.GankHistoryContract;
import com.ayvytr.knowledge.presenter.GankHistoryPresenter;
import com.ayvytr.mvp.BaseListFragment;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

/**
 * @author admin
 */
public class GankHistoryFragment extends BaseListFragment<GankHistoryPresenter, String>
        implements GankHistoryContract.View {
    @Override
    protected GankHistoryPresenter getPresenter() {
        return new GankHistoryPresenter(this);
    }

    @Override
    public void initExtra() {

    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mRvList.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mAdapter = new GankHistoryAdapter(getContext());
        mAdapter.setEmptyView(R.layout.layout_empty);
        mRvList.setAdapter(mAdapter);
        mSmartRefreshLayout.setEnableLoadMore(false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.requestGankHistory();
    }

    @Override
    public int getContentViewRes() {
        return R.layout.layout_list;
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
        mPresenter.requestGankHistory();
    }

    @Override
    public void showGankHistory(List<String> results) {
        updateList(results);
    }
}
