package com.ayvytr.knowledge.view.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.ayvytr.baselist.BaseListFragment
import com.ayvytr.commonlibrary.GankType
import com.ayvytr.commonlibrary.bean.BaseGank
import com.ayvytr.commonlibrary.bean.Gank
import com.ayvytr.commonlibrary.constant.WebConstant
import com.ayvytr.commonlibrary.util.NetworkState
import com.ayvytr.knowledge.R
import com.ayvytr.knowledge.adapter.AndroidAdapter
import com.ayvytr.knowledge.adapter.AndroidPagedListAdapter
import com.ayvytr.knowledge.contract.AndroidContract
import com.ayvytr.knowledge.datasource.AndroidDataSourceFactory
import com.ayvytr.knowledge.presenter.AndroidPresenter
import com.ayvytr.knowledge.viewmodel.AndroidViewModel
import com.scwang.smartrefresh.layout.api.RefreshLayout
import kotlinx.android.synthetic.main.activity_gank_history_content.*

/**
 * @author admin
 */
class AndroidFragment : BaseListFragment<AndroidPresenter, Gank>(), AndroidContract.View {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        androidPagedListAdapter = AndroidPagedListAdapter()
        androidViewModel = ViewModelProviders.of(this).get(AndroidViewModel::class.java)
        androidViewModel.androidLiveData.observe(viewLifecycleOwner, Observer {
            androidPagedListAdapter.submitList(it)
        })
        AndroidDataSourceFactory.networkState.observe(viewLifecycleOwner, Observer {
            when (it) {
                NetworkState.LOADING -> status_view.showLoading()
                NetworkState.SUCCESS -> status_view.showContent()
                NetworkState.FAILED  -> status_view.showError()
            }
        })
        super.onViewCreated(view, savedInstanceState)
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

        mStatusView.showLoading()
    }

    override fun initData(savedInstanceState: Bundle?) {
        showLoading()
    }

    override fun getContentViewRes(): Int {
        return R.layout.fragment_android
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        super.onRefresh(refreshLayout)
        //        mPresenter.requestGankByType(gankType!!, mPageSize, 1)
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
