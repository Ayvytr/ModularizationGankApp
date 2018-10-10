package com.ayvytr.knowledge.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ayvytr.commonlibrary.bean.BaseGank;
import com.ayvytr.commonlibrary.constant.WebConstant;
import com.ayvytr.knowledge.R;
import com.ayvytr.knowledge.adapter.AndroidAdapter;
import com.ayvytr.knowledge.contract.AndroidContract;
import com.ayvytr.knowledge.presenter.AndroidPresenter;
import com.ayvytr.mvp.BaseMvpFragment;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

/**
 * @author admin
 */
public class AndroidFragment extends BaseMvpFragment<AndroidPresenter> implements AndroidContract.View {
    private AndroidAdapter androidAdapter;

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
        androidAdapter = new AndroidAdapter(getContext(), R.layout.layout_item_android);
        mRvList.setAdapter(androidAdapter);
        androidAdapter.setEmptyView(R.layout.layout_empty);

        mRvList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        androidAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                BaseGank.Gank gank = androidAdapter.getItemAt(position);
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
        currentPage = 1;
        mPresenter.requestAndroidGank(pageSize, currentPage);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        super.onLoadMore(refreshLayout);
        mPresenter.requestAndroidGank(pageSize, currentPage);
    }

    @Override
    public void showAndroidGank(BaseGank baseGank) {
        if(currentPage == 1) {
            androidAdapter.updateList(baseGank.getResults());
            mSmartRefreshLayout.setEnableLoadMore(baseGank.getResults().size() == pageSize);
        } else {
            androidAdapter.addList(baseGank.getResults());
            mSmartRefreshLayout.setEnableLoadMore(baseGank.getResults().size() == pageSize);
        }

        currentPage++;
        finishRefresh();
    }
}
