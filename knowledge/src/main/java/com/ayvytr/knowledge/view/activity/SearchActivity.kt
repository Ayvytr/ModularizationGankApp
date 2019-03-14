package com.ayvytr.knowledge.view.activity

import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.Menu
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.ayvytr.baselist.BaseListActivity
import com.ayvytr.commonlibrary.bean.Gank
import com.ayvytr.commonlibrary.constant.KnowledgeConstant
import com.ayvytr.commonlibrary.constant.WebConstant
import com.ayvytr.knowledge.R
import com.ayvytr.knowledge.adapter.AndroidAdapter
import com.ayvytr.knowledge.contract.SearchContract
import com.ayvytr.knowledge.presenter.SearchPresenter

@Route(path = KnowledgeConstant.SEARCH)
class SearchActivity : BaseListActivity<SearchPresenter, Gank>(), SearchContract.View {

    internal lateinit var toolbar: Toolbar

    internal lateinit var mSearchView: SearchView

    override fun getPresenter(): SearchPresenter {
        return SearchPresenter(this)
    }

    override fun initExtra() {

    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_back_white)
        toolbar.setNavigationOnClickListener { finish() }
        toolbar.setTitle(R.string.search)

        mRvList!!.setLayoutManager(LinearLayoutManager(context))
        mAdapter = AndroidAdapter(context)
        mRvList!!.setAdapter(mAdapter)
//        mAdapter.showEmpty()
        mRvList!!.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        mAdapter.setOnItemClickListener { view, holder, position ->
            val gank = mAdapter.getItemAt(position)
            ARouter.getInstance().build(WebConstant.WEB)
                .withString(WebConstant.EXTRA_TITLE, gank.desc)
                .withString(WebConstant.EXTRA_URL, gank.url)
                .navigation(context)
        }
    }

    private fun initSearchView() {
        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                resetPage()
                mPresenter.search(query, mCurrentPage, mPageSize)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        mSearchView.queryHint = getString(R.string.please_input_search_content)
        mSearchView.isSubmitButtonEnabled = true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val menuItem = menu.findItem(R.id.menu_search)
        //通过MenuItem得到SearchView
        mSearchView = MenuItemCompat.getActionView(menuItem) as SearchView
        menuItem.expandActionView()
        initSearchView()

        return true
    }

    override fun getContentViewRes(): Int {
        return R.layout.activity_search
    }

    override fun showSearchResult(results: List<Gank>) {
        updateList(results, null)

//        if (results.isEmpty()) {
//            mAdapter.showEmpty()
//        }
    }
}
