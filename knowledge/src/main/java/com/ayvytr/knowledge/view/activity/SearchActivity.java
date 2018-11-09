package com.ayvytr.knowledge.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ayvytr.baseadapter.MultiItemTypeAdapter;
import com.ayvytr.commonlibrary.bean.Gank;
import com.ayvytr.commonlibrary.constant.KnowledgeConstant;
import com.ayvytr.commonlibrary.constant.WebConstant;
import com.ayvytr.knowledge.R;
import com.ayvytr.knowledge.R2;
import com.ayvytr.knowledge.adapter.AndroidAdapter;
import com.ayvytr.knowledge.contract.SearchContract;
import com.ayvytr.knowledge.presenter.SearchPresenter;
import com.ayvytr.mvpbaselist.BaseListActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = KnowledgeConstant.SEARCH)
public class SearchActivity extends BaseListActivity<SearchPresenter, Gank> implements SearchContract.View {

    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.rv_list)
    RecyclerView mRvList;
    @BindView(R2.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;

    SearchView mSearchView;

    @Override
    protected SearchPresenter getPresenter() {
        return new SearchPresenter(this);
    }

    @Override
    public void initExtra() {

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitle(R.string.search);

        mRvList.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new AndroidAdapter(getContext());
        mRvList.setAdapter(mAdapter);
        mAdapter.showEmpty();
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

    private void initSearchView() {
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                resetPage();
                mPresenter.search(query, currentPage, pageSize);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mSearchView.setQueryHint(getString(R.string.please_input_search_content));
        mSearchView.setSubmitButtonEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_search);
        //通过MenuItem得到SearchView
        mSearchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        menuItem.expandActionView();
        initSearchView();

        return true;
    }

    @Override
    public int getContentViewRes() {
        return R.layout.activity_search;
    }

    @Override
    public void showSearchResult(List<Gank> results) {
        updateList(results);

        if(results.isEmpty()) {
            mAdapter.showEmpty();
        }
    }
}
