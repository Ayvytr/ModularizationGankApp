package com.ayvytr.knowledge.view.fragment

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.ayvytr.baselist.BaseListFragment
import com.ayvytr.commonlibrary.GankType
import com.ayvytr.commonlibrary.bean.BaseGank
import com.ayvytr.commonlibrary.bean.Gank
import com.ayvytr.commonlibrary.callback.GankCallback
import com.ayvytr.commonlibrary.constant.WebConstant
import com.ayvytr.knowledge.R
import com.ayvytr.knowledge.adapter.AndroidAdapter
import com.ayvytr.knowledge.contract.AndroidContract
import com.ayvytr.knowledge.presenter.AndroidPresenter
import com.scwang.smartrefresh.layout.api.RefreshLayout

/**
 * @author admin
 */
class AndroidFragment : BaseListFragment<AndroidPresenter, Gank>(), AndroidContract.View {
    private var gankType: String? = null

    override fun getPresenter(): AndroidPresenter {
        return AndroidPresenter(this)
    }

    override fun initExtra() {
        gankType = arguments!!.getString(GANK_TYPE)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mRvList.layoutManager = LinearLayoutManager(context)
        mAdapter = AndroidAdapter(context!!)
        mRvList.adapter = mAdapter

        mRvList.itemAnimator = DefaultItemAnimator()
        mRvList.addItemDecoration(DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL))
        mAdapter.setOnItemClickListener { view, holder, position ->
            val gank = mAdapter.getItemAt(position)
            ARouter.getInstance().build(WebConstant.WEB)
                .withString(WebConstant.EXTRA_TITLE, gank.desc)
                .withString(WebConstant.EXTRA_URL, gank.url)
                .navigation(context)
        }
        mSmartRefreshLayout.autoRefresh()
    }

    override fun initData(savedInstanceState: Bundle?) {
        showLoading()
    }

    override fun getContentViewRes(): Int {
        return R.layout.fragment_android
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        super.onRefresh(refreshLayout)
        mPresenter.requestGankByType(gankType!!, mPageSize, 1)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        super.onLoadMore(refreshLayout)
        mPresenter.requestGankByType(gankType!!, mPageSize, mCurrentPage + 1)
    }

    override fun showGank(baseGank: BaseGank, currentPage: Int) {
        mCurrentPage = currentPage
        updateList(baseGank.results, GankCallback(mAdapter.datas, baseGank.results!!))
    }

    companion object {
        private val GANK_TYPE = "gank_type"

        fun newInstance(gankType: GankType): AndroidFragment {
            val af = AndroidFragment()
            val bundle = Bundle()
            bundle.putString(GANK_TYPE, gankType.toString())
            af.arguments = bundle
            return af
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //内存泄漏
        if(mContentView != null){
            (mContentView.parent as ViewGroup).removeView(mContentView)
        }
    }
}
