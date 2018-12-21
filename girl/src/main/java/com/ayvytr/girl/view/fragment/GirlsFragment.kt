package com.ayvytr.girl.view.fragment

import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.StaggeredGridLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.ayvytr.baselist.BaseListFragment
import com.ayvytr.commonlibrary.bean.BaseGank
import com.ayvytr.commonlibrary.bean.Gank
import com.ayvytr.commonlibrary.constant.GirlsConstant
import com.ayvytr.girl.R
import com.ayvytr.girl.adapter.GirlsAdapter
import com.ayvytr.girl.contract.GirlsContract
import com.ayvytr.girl.presenter.GirlsPresenter
import com.scwang.smartrefresh.layout.api.RefreshLayout
import java.util.*


/**
 * @author admin
 */
class GirlsFragment : BaseListFragment<GirlsPresenter, Gank>(), GirlsContract.View {
    override fun getPresenter(): GirlsPresenter {
        return GirlsPresenter(this)
    }

    override fun initExtra() {

    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mAdapter = GirlsAdapter(context!!, R.layout.layout_item_girl)
        mRvList.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mAdapter.setOnItemClickListener { view, holder, position ->
            val list = ArrayList(mAdapter.datas)

            val compat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity!!,
                                                                            view, getString(R.string.trans_photo_name))
            ARouter.getInstance()
                .build(GirlsConstant.PHOTO_VIEW)
                .withParcelableArrayList(GirlsConstant.EXTRA_PHOTOS, list)
                .withInt(GirlsConstant.EXTRA_PHOTO_POSITION, position)
                .withOptionsCompat(compat)
                .navigation(context)
        }
        mRvList.adapter = mAdapter
    }

    override fun initData(savedInstanceState: Bundle?) {
        mPresenter.requestGankMm(pageSize, currentPage)
    }

    override fun getContentViewRes(): Int {
        return R.layout.layout_list
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        super.onLoadMore(refreshLayout)
        mPresenter.requestGankMm(pageSize, currentPage)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        super.onRefresh(refreshLayout)
        mPresenter.requestGankMm(pageSize, currentPage)
    }

    override fun showGankMm(gank: BaseGank) {
        updateList(gank.results)
    }
}
