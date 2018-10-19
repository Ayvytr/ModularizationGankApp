package com.ayvytr.girl.view.activity;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ayvytr.commonlibrary.GankType;
import com.ayvytr.commonlibrary.bean.Gank;
import com.ayvytr.commonlibrary.constant.GirlsConstant;
import com.ayvytr.easykotlin.context.ContextKt;
import com.ayvytr.easykotlin.context.ManagerKt;
import com.ayvytr.easykotlin.ui.ViewKt;
import com.ayvytr.girl.R;
import com.ayvytr.girl.R2;
import com.ayvytr.girl.contract.PhotoViewContract;
import com.ayvytr.girl.presenter.PhotoViewPresenter;
import com.ayvytr.mvp.BaseMvpActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.github.chrisbanes.photoview.PhotoView;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;

/**
 * 图片浏览Activity.
 * TODO: 支持双击放大/还原
 *
 * @author admin
 */
@Route(path = GirlsConstant.PHOTO_VIEW)
public class PhotoViewActivity extends BaseMvpActivity<PhotoViewPresenter> implements PhotoViewContract.View {

    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    @BindView(R2.id.vp)
    ViewPager mVp;
    @BindView(R2.id.btnMoreOptions)
    Button mBtnMoreOptions;
    @BindView(R2.id.btnSavePhoto)
    Button mBtnSavePhoto;
    @BindView(R2.id.btnSetToDesktopBg)
    Button mBtnSetToDesktopBg;
    @BindView(R2.id.btnShare)
    Button mBtnShare;
    @BindView(R2.id.nsv)
    NestedScrollView mNsv;

    private List<Gank> mList;
    private BottomSheetBehavior<View> mBottomSheetBehavior;

    private int mPosition;
    private Bitmap mBitmap;

    @Override
    protected PhotoViewPresenter getPresenter() {
        return new PhotoViewPresenter(this);
    }

    @Override
    public void initExtra() {
        mList = getIntent().getParcelableArrayListExtra(GirlsConstant.EXTRA_PHOTOS);
        mPosition = getIntent().getIntExtra(GirlsConstant.EXTRA_PHOTO_POSITION, 0);
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(GankType.GIRLS.toString());
        if(mList == null) {
            showError(R.string.no_photos);
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
                return mList.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                PhotoView photoView = new PhotoView(getContext());
                photoView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        }
                    }
                });
                photoView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mBtnMoreOptions.performClick();
                        return true;
                    }
                });
                Glide.with(getContext())
                     .asBitmap()
                     .load(mList.get(position).getUrl())
                     .into(new BitmapImageViewTarget(photoView) {

                         @Override
                         public void onResourceReady(@NonNull Bitmap resource,
                                                     @Nullable Transition<? super Bitmap> transition) {
                             mBitmap = resource;
                             super.onResourceReady(resource, transition);
                         }
                     });
                container.addView(photoView);
                return photoView;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }
        });
        mVp.setCurrentItem(mPosition);
        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mBottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.nsv));
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch(newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        mBottomSheetBehavior.setPeekHeight(ContextKt.dp2px(getContext(), 10));
                        ViewKt.hide(mBtnMoreOptions);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });

        mBtnMoreOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetBehavior.setPeekHeight(ContextKt.dp2px(getContext(), 10));
                ViewKt.hide(mBtnMoreOptions);
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        mBtnSavePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                if(mBitmap == null) {
                    showError(R.string.no_photo_loaded);
                    return;
                }
                if(!AndPermission.hasPermissions(getContext(), Permission.WRITE_EXTERNAL_STORAGE)) {
                    requestStoragePermission(false);
                } else {
                    mPresenter.savePhoto(mList.get(mPosition).getUrl(), getPackageName(), false);
                }
            }
        });

        mBtnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                mPresenter.getShareIntent(mList.get(mPosition).getUrl(), getPackageName(), getContext());
            }
        });

        mBtnSetToDesktopBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                if(!AndPermission.hasPermissions(getContext(), Permission.WRITE_EXTERNAL_STORAGE)) {
                    requestStoragePermission(true);
                } else {
                    mPresenter.savePhoto(mList.get(mPosition).getUrl(), getPackageName(), true);
                }
            }
        });
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public int getContentViewRes() {
        return R.layout.activity_photo_view;
    }

    private void requestStoragePermission(final boolean isSettingWallpaper) {
        AndPermission.with(getContext())
                     .runtime()
                     .permission(Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE)
                     .onDenied(new Action<List<String>>() {
                         @Override
                         public void onAction(List<String> data) {
                             Toast.makeText(PhotoViewActivity.this, R.string.cannot_get_storage_permission,
                                     Toast.LENGTH_SHORT).show();
                         }
                     })
                     .onGranted(new Action<List<String>>() {
                         @Override
                         public void onAction(List<String> data) {
                             mPresenter.savePhoto(mList.get(mPosition).getUrl(), getPackageName(), isSettingWallpaper);
                         }
                     }).start();
    }

    @Override
    public void onGotShareIntent(Intent intent) {
        startActivity(Intent.createChooser(intent, getString(R.string.share_to)));
    }

    @Override
    public void onSettingWallpaper(final Bitmap bitmap) {
        WallpaperManager wallpaperManager = ManagerKt.getWallpaperManager(getContext());
        try {
            wallpaperManager.setBitmap(bitmap);
        } catch(IOException e) {
            e.printStackTrace();
        }

        showError(R.string.wallpaper_changed);
    }

    @Override
    public void onBackPressed() {
        if(mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            return;
        }

        super.onBackPressed();
    }
}
