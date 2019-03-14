package com.ayvytr.mob.view.fragment

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.ayvytr.baselist.BaseListFragment
import com.ayvytr.commonlibrary.callback.TodayInHistoryCallback
import com.ayvytr.mob.R
import com.ayvytr.mob.TodayInHistory
import com.ayvytr.mob.adapter.TodayInHistoryAdapter
import com.ayvytr.mob.contract.TodayInHistoryContract
import com.ayvytr.mob.model.TodayInHistoryModel
import com.ayvytr.mob.presenter.TodayInHistoryPresenter
import com.scwang.smartrefresh.layout.api.RefreshLayout

class TodayInHistoryFragment : BaseListFragment<TodayInHistoryPresenter, TodayInHistory.ResultBean>(),
    TodayInHistoryContract.View {
    override fun getPresenter(): TodayInHistoryPresenter {
        return TodayInHistoryPresenter(TodayInHistoryModel(), this)
    }

    override fun initExtra() {

    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mSmartRefreshLayout.setEnableLoadMore(false)
        mSmartRefreshLayout.setEnableAutoLoadMore(false)
        mRvList.layoutManager = LinearLayoutManager(context)
        mAdapter = TodayInHistoryAdapter(context!!)
        mRvList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        mRvList.adapter = mAdapter
    }

    override fun initData(savedInstanceState: Bundle?) {
        mPresenter.requestTodayInHistory()
    }

    override fun getContentViewRes(): Int {
        return R.layout.layout_list
    }

    override fun showTodayInHistory(t: List<TodayInHistory.ResultBean>) {
        updateList(t, TodayInHistoryCallback(mAdapter.datas, t))
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        super.onRefresh(refreshLayout)
        initData(null)
    }
}
