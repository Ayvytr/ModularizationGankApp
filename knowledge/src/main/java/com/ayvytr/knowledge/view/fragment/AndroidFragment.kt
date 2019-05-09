package com.ayvytr.knowledge.view.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ayvytr.baselist.BaseListFragment2
import com.ayvytr.commonlibrary.GankType
import com.ayvytr.commonlibrary.bean.BaseGank
import com.ayvytr.commonlibrary.bean.Gank
import com.ayvytr.commonlibrary.util.NetworkState
import com.ayvytr.knowledge.R
import com.ayvytr.knowledge.adapter.AndroidPagedListAdapter
import com.ayvytr.knowledge.contract.AndroidContract
import com.ayvytr.knowledge.datasource.AndroidDataSource
import com.ayvytr.knowledge.presenter.AndroidPresenter
import com.ayvytr.knowledge.viewmodel.AndroidViewModel
import com.scwang.smartrefresh.layout.api.RefreshLayout

/**
 * @author admin
 */
class AndroidFragment : BaseListFragment2<AndroidPresenter, Gank>(), AndroidContract.View {
    private var gankType: String? = null
    private lateinit var androidViewModel: AndroidViewModel
    private lateinit var androidPagedListAdapter: AndroidPagedListAdapter

    override fun getPresenter(): AndroidPresenter? {
        //        return AndroidPresenter(this)
        return null
    }

    override fun initExtra() {
        gankType = arguments!!.getString(GANK_TYPE)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mSmartRefreshLayout.setEnableLoadMore(false)

        mRvList.layoutManager = LinearLayoutManager(context)
        androidPagedListAdapter = AndroidPagedListAdapter()
        mRvList.adapter = androidPagedListAdapter

        androidViewModel = ViewModelProviders.of(this).get(AndroidViewModel::class.java)
        androidViewModel.androidLiveData.observe(viewLifecycleOwner, Observer {
            androidPagedListAdapter.submitList(it)
        })

        AndroidDataSource.networkState.observe(viewLifecycleOwner, Observer {
            when (it) {
                NetworkState.LOADING -> mStatusView.showLoading()
                NetworkState.SUCCESS -> mStatusView.showContent()
                NetworkState.FAILED  -> mStatusView.showError()
            }
        })

        AndroidDataSource.networkState.postValue(NetworkState.LOADING)
    }

    override fun initData(savedInstanceState: Bundle?) {
        showLoading()
    }

    override fun getContentViewRes(): Int {
        return R.layout.fragment_android
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
//        super.onRefresh(refreshLayout)
        //        mPresenter.requestGankByType(gankType!!, mPageSize, 1)
        mSmartRefreshLayout.finishRefresh()
//        AndroidDataSourceFactory.networkState.postValue(NetworkState.LOADING)
        androidViewModel.androidDataSource.invalidate()
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        super.onLoadMore(refreshLayout)
        //        mPresenter.requestGankByType(gankType!!, mPageSize, mCurrentPage + 1)
    }

    override fun showGank(baseGank: BaseGank, currentPage: Int) {
        //        mCurrentPage = currentPage
        //        updateList(baseGank.results, GankCallback(mAdapter.datas, baseGank.results!!))
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
}
