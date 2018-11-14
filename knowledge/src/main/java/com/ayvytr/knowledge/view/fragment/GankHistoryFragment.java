package com.ayvytr.knowledge.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ayvytr.baseadapter.MultiItemTypeAdapter;
import com.ayvytr.commonlibrary.constant.KnowledgeConstant;
import com.ayvytr.knowledge.R;
import com.ayvytr.knowledge.adapter.GankHistoryAdapter;
import com.ayvytr.knowledge.contract.GankHistoryContract;
import com.ayvytr.knowledge.presenter.GankHistoryPresenter;
import com.ayvytr.mvpbaselist.BaseListFragment;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

import butterknife.ButterKnife;

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
        ButterKnife.bind(this, mContentView);
        mRvList.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mAdapter = new GankHistoryAdapter(getContext());
//        mAdapter.setEmptyView(R.layout.layout_empty);
        mRvList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                ARouter.getInstance()
                       .build(KnowledgeConstant.GANK_HISTORY_CONTENT)
                       .withString(KnowledgeConstant.EXTRA_DATE, mAdapter.getItemAt(position))
                       .navigation(getContext());
            }
        });
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
