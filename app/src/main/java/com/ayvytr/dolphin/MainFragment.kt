package com.ayvytr.dolphin

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import com.ayvytr.commonlibrary.GankType
import com.ayvytr.girl.view.fragment.GirlsFragment
import com.ayvytr.knowledge.view.fragment.AndroidFragment
import com.ayvytr.mvp.IPresenter
import com.ayvytr.rxlifecycle.BaseMvpFragment
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * @author ayvytr
 */
class MainFragment : BaseMvpFragment<IPresenter>(){
    private var mFragments: Array<Fragment>? = arrayOf(AndroidFragment.newInstance(GankType.ANDROID), GirlsFragment())

    override fun initView(savedInstanceState: Bundle?) {
        bottom_navigation!!.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nv_android -> {
                    vp!!.currentItem = 0
                    true
                }
                R.id.nv_girls   -> {
                    vp!!.currentItem = 1
                    true
                }
                //                R.id.nv_settings -> {
                //                    vp!!.currentItem = 2
                //                    true
                //                }
                else            -> false
            }
        }
        vp!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> bottom_navigation!!.selectedItemId = R.id.nv_android
                    1 -> bottom_navigation!!.selectedItemId = R.id.nv_girls
                    //                    else -> bottom_navigation!!.selectedItemId = R.id.nv_settings
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        vp.offscreenPageLimit = mFragments!!.size

        //        llHeader = findViewById(R.id.llHeader)
        //        llHeader.setOnClickListener {
        //            ARouter.getInstance().build(WebConstant.WEB)
        //                .withString(WebConstant.EXTRA_TITLE, getString(R.string.author_ayvytr_github))
        //                .withString(WebConstant.EXTRA_URL, getString(R.string.author_ayvytr_github_url))
        //                .navigation(getContext())
        //        }

        vp!!.adapter = object : FragmentPagerAdapter(fragmentManager) {
            override fun getItem(position: Int): Fragment {
                return mFragments!![position]
            }

            override fun getCount(): Int {
                return mFragments!!.size
            }
        }


    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun getContentViewRes(): Int {
        return R.layout.fragment_main
    }

    override fun getPresenter(): IPresenter? {
        return null
    }

    override fun initExtra() {
    }

    override fun onDestroy() {
        super.onDestroy()
        mFragments = null
    }
}