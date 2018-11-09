package com.ayvytr.knowledge.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.ayvytr.commonlibrary.GankType;
import com.ayvytr.knowledge.R;
import com.ayvytr.knowledge.R2;
import com.ayvytr.mvpbase.IPresenter;
import com.ayvytr.mvprxlifecycle.BaseMvpFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author admin
 */
public class ClassifyGankFragment extends BaseMvpFragment {
    @BindView(R2.id.tl_type)
    TabLayout mTlType;
    @BindView(R2.id.vp_type)
    ViewPager mVpType;

    private List<Fragment> mFragments;
    private List<String> mTitles;

    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    @Override
    public void initExtra() {

    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mFragments = new ArrayList<>();
        mTitles = new ArrayList<>();

        GankType[] values = GankType.values();
        for(int i = 0; i < values.length; i++) {
            if(values[i] != GankType.GIRLS) {
                mFragments.add(AndroidFragment.newInstance(values[i]));
                mTitles.add(values[i].toString());
            }
        }

        mVpType.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles.get(position);
            }
        });

        mTlType.setupWithViewPager(mVpType);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public int getContentViewRes() {
        return R.layout.fragment_classify_gank;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            ((BaseMvpFragment) mFragments.get(mVpType.getCurrentItem())).initData(null);
        }
    }

}
