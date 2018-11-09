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
import com.ayvytr.baseadapter.MultiItemTypeAdapter;
import com.ayvytr.commonlibrary.GankType;
import com.ayvytr.commonlibrary.bean.BaseGank;
import com.ayvytr.commonlibrary.bean.Gank;
import com.ayvytr.commonlibrary.constant.WebConstant;
import com.ayvytr.knowledge.R;
import com.ayvytr.knowledge.adapter.AndroidAdapter;
import com.ayvytr.knowledge.contract.AndroidContract;
import com.ayvytr.knowledge.presenter.AndroidPresenter;
import com.ayvytr.mvpbaselist.BaseListFragment;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

/**
 * @author admin
 */
public class AndroidFragment extends BaseListFragment<AndroidPresenter, Gank> implements AndroidContract.View {
    private static final String GANK_TYPE = "gank_type";
    private String gankType;

    public static AndroidFragment newInstance(GankType gankType) {
        AndroidFragment af = new AndroidFragment();
        Bundle bundle = new Bundle();
        bundle.putString(GANK_TYPE, gankType.toString());
        af.setArguments(bundle);
        return af;
    }

    @Override
    protected AndroidPresenter getPresenter() {
        return new AndroidPresenter(this);
    }

    @Override
    public void initExtra() {
        gankType = getArguments().getString(GANK_TYPE);
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mRvList.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new AndroidAdapter(getContext());
        mRvList.setAdapter(mAdapter);

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
        mSmartRefreshLayout.autoRefresh();
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        showLoading();
    }

    @Override
    public int getContentViewRes() {
        return R.layout.fragment_android;
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
        mPresenter.requestGankByType(gankType, pageSize, currentPage);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        super.onLoadMore(refreshLayout);
        mPresenter.requestGankByType(gankType, pageSize, currentPage);
    }

    @Override
    public void showGank(BaseGank baseGank) {
        updateList(baseGank.getResults());
    }
}
