package com.ayvytr.girl.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ayvytr.baseadapter.MultiItemTypeAdapter;
import com.ayvytr.commonlibrary.bean.BaseGank;
import com.ayvytr.commonlibrary.bean.Gank;
import com.ayvytr.commonlibrary.constant.GirlsConstant;
import com.ayvytr.girl.R;
import com.ayvytr.girl.adapter.GirlsAdapter;
import com.ayvytr.girl.contract.GirlsContract;
import com.ayvytr.girl.presenter.GirlsPresenter;
import com.ayvytr.mvpbaselist.BaseListFragment;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * @author admin
 */
public class GirlsFragment extends BaseListFragment<GirlsPresenter, Gank> implements GirlsContract.View {
    @Override
    protected GirlsPresenter getPresenter() {
        return new GirlsPresenter(this);
    }

    @Override
    public void initExtra() {

    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        ButterKnife.bind(this, mContentView);
        mAdapter = new GirlsAdapter(getContext(), R.layout.layout_item_girl);
        mRvList.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                ArrayList list = new ArrayList<Gank>();
                list.addAll(mAdapter.getDatas());

                ARouter.getInstance()
                       .build(GirlsConstant.PHOTO_VIEW)
                       .withParcelableArrayList(GirlsConstant.EXTRA_PHOTOS, list)
                       .withInt(GirlsConstant.EXTRA_PHOTO_POSITION, position)
                       .navigation();
            }
        });
        mRvList.setAdapter(mAdapter);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.requestGankMm(pageSize, currentPage);
    }

    @Override
    public int getContentViewRes() {
        return R.layout.layout_list;
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        super.onLoadMore(refreshLayout);
        mPresenter.requestGankMm(pageSize, currentPage);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
        mPresenter.requestGankMm(pageSize, currentPage);
    }

    @Override
    public void showGankMm(BaseGank gank) {
        updateList(gank.getResults());
    }
}
