package com.ayvytr.girl.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ayvytr.commonlibrary.GankType;
import com.ayvytr.commonlibrary.bean.Gank;
import com.ayvytr.commonlibrary.constant.GirlsConstant;
import com.ayvytr.girl.R;
import com.ayvytr.girl.R2;
import com.ayvytr.mvp.BaseMvpActivity;
import com.ayvytr.mvp.IPresenter;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

import butterknife.BindView;

/**
 * 图片浏览Activity.
 * TODO: 支持双击放大/还原
 *
 * @author admin
 */
@Route(path = GirlsConstant.PHOTO_VIEW)
public class PhotoViewActivity extends BaseMvpActivity {

    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    @BindView(R2.id.vp)
    ViewPager mVp;
//    @BindView(R2.id.btnSavePhoto)
//    Button mBtnSavePhoto;

    private List<Gank> list;
    private int position;

    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    @Override
    public void initExtra() {
        list = getIntent().getParcelableArrayListExtra(GirlsConstant.EXTRA_PHOTOS);
        position = getIntent().getIntExtra(GirlsConstant.EXTRA_PHOTO_POSITION, 0);
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(GankType.GIRLS.toString());
        if(list == null) {
            showMessage(R.string.no_photos);
            return;
        }
        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mVp.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                PhotoView photoView = new PhotoView(getContext());
                Glide.with(getContext())
                     .load(list.get(position).getUrl())
                     .into(photoView);
                container.addView(photoView);
                return photoView;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }
        });
        mVp.setCurrentItem(position);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public int getContentViewRes() {
        return R.layout.activity_photo_view;
    }
}
