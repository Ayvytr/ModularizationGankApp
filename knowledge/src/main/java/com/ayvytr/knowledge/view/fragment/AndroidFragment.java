package com.ayvytr.knowledge.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ayvytr.commonlibrary.bean.BaseGank;
import com.ayvytr.commonlibrary.bean.Gank;
import com.ayvytr.commonlibrary.constant.WebConstant;
import com.ayvytr.knowledge.R;
import com.ayvytr.knowledge.adapter.AndroidAdapter;
import com.ayvytr.knowledge.contract.AndroidContract;
import com.ayvytr.knowledge.presenter.AndroidPresenter;
import com.ayvytr.mvp.BaseListFragment;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

/**
 * @author admin
 */
public class AndroidFragment extends BaseListFragment<AndroidPresenter, Gank> implements AndroidContract.View {

    @Override
    protected AndroidPresenter getPresenter() {
        return new AndroidPresenter(this);
    }

    @Override
    public void initExtra() {

    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mRvList.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new AndroidAdapter(getContext(), R.layout.layout_item_android);
        mRvList.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.layout_empty);

        mRvList.setItemAnimator(new DefaultItemAnimator());
        mRvList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Gank gank = mAdapter.getItemAt(position);
                ARouter.getInstance().build(WebConstant.WEBVIEW)
                       .withString(WebConstant.EXTRA_TITLE, gank.getDesc())
                       .withString(WebConstant.EXTRA_URL, gank.getUrl())
                       .navigation(getContext());
            }
        });
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.requestAndroidGank(pageSize, currentPage);
    }

    @Override
    public int getContentViewRes() {
        return R.layout.fragment_android;
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
        mPresenter.requestAndroidGank(pageSize, currentPage);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        super.onLoadMore(refreshLayout);
        mPresenter.requestAndroidGank(pageSize, currentPage);
    }

    @Override
    public void showAndroidGank(BaseGank baseGank) {
        updateList(baseGank.getResults());
    }
}
