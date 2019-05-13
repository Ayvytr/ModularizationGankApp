package com.ayvytr.knowledge.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.ayvytr.commonlibrary.GankType
import com.ayvytr.knowledge.R
import com.ayvytr.logger.L
import com.ayvytr.mvp.IPresenter
import com.ayvytr.rxlifecycle.BaseMvpFragment
import kotlinx.android.synthetic.main.fragment_classify_gank.*
import java.util.*

/**
 * @author admin
 */
class ClassifyGankFragment : BaseMvpFragment<IPresenter>() {

    private lateinit var mFragments: MutableList<Fragment>
    private lateinit var mTitles: MutableList<String>

    override fun getPresenter(): IPresenter? {
        return null
    }

    override fun initExtra() {

    }

    override fun initView(savedInstanceState: Bundle?) {
        mFragments = ArrayList()
        mTitles = ArrayList()

        val values = GankType.values()
        for (i in values.indices) {
            if (values[i] != GankType.GIRLS) {
                mFragments.add(AndroidFragment.newInstance(values[i]))
                mTitles.add(values[i].toString())
            }
        }

        vp_type.adapter = object : FragmentPagerAdapter(childFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return mFragments[position]
            }

            override fun getCount(): Int {
                return mFragments.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return mTitles[position]
            }
        }

        tl_type.setupWithViewPager(vp_type)

        L.e()
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun getContentViewRes(): Int {
        return R.layout.fragment_classify_gank
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        L.e()
        if (isVisibleToUser) {
            (mFragments[vp_type.currentItem] as BaseMvpFragment<*>).initData(null)
        }
    }

    override fun onDestroyView() {
        vp_type.adapter = null
        tl_type.setupWithViewPager(null)
        mFragments.clear()
        mTitles.clear()
        super.onDestroyView()
    }
}
