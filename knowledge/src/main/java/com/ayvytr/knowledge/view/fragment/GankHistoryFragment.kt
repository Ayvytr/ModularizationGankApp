package com.ayvytr.knowledge.view.fragment

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.ayvytr.baselist.BaseListFragment
import com.ayvytr.commonlibrary.constant.KnowledgeConstant
import com.ayvytr.knowledge.R
import com.ayvytr.knowledge.adapter.GankHistoryAdapter
import com.ayvytr.knowledge.contract.GankHistoryContract
import com.ayvytr.knowledge.presenter.GankHistoryPresenter
import com.scwang.smartrefresh.layout.api.RefreshLayout

/**
 * @author admin
 */
class GankHistoryFragment : BaseListFragment<GankHistoryPresenter, String>(), GankHistoryContract.View {
    override fun getPresenter(): GankHistoryPresenter {
        return GankHistoryPresenter(this)
    }

    override fun initExtra() {}

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mRvList.layoutManager = LinearLayoutManager(context)
        mRvList.addItemDecoration(DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL))
        mAdapter = GankHistoryAdapter(context!!)
        //        mAdapter.setEmptyView(R.layout.layout_empty);
        mRvList.adapter = mAdapter
        mAdapter.setOnItemClickListener { view, holder, position ->
            ARouter.getInstance()
                .build(KnowledgeConstant.GANK_HISTORY_CONTENT)
                .withString(KnowledgeConstant.EXTRA_DATE, mAdapter.getItemAt(position))
                .navigation(context)
        }
        mSmartRefreshLayout.setEnableLoadMore(false)
    }

    override fun initData(savedInstanceState: Bundle?) {
        mPresenter.requestGankHistory()
    }

    override fun getContentViewRes(): Int {
        return R.layout.layout_list
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        super.onRefresh(refreshLayout)
        mPresenter.requestGankHistory()
    }

    override fun showGankHistory(results: List<String>) {
        updateList(results)
    }
}
