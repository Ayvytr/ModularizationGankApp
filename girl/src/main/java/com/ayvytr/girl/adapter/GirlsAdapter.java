package com.ayvytr.girl.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ayvytr.commonlibrary.bean.Gank;
import com.ayvytr.easykotlin.context.ScreenKt;
import com.ayvytr.girl.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapperAdapter;

import java.util.LinkedHashMap;

/**
 * @author admin
 */
public class GirlsAdapter extends EmptyWrapperAdapter<Gank> {
    private final int halfScreenWidth;

    private LinkedHashMap<String, Integer> mPhotoSizeMap = new LinkedHashMap<>();

    public GirlsAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
        halfScreenWidth = ScreenKt.getScreenWidth(context) / 2;
    }

    @Override
    public void convert(final ViewHolder holder, final Gank gank, int position) {
        final ImageView iv = holder.getView(R.id.iv);
        Glide.with(mContext)
             .asBitmap()
             .load(gank.getUrl())
             .listener(new RequestListener<Bitmap>() {
                 @Override
                 public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target,
                                             boolean isFirstResource) {
                     return false;
                 }

                 @Override
                 public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target,
                                                DataSource dataSource, boolean isFirstResource) {
                     Integer height = mPhotoSizeMap.get(gank.getUrl());
                     if(height == null){
                         height = halfScreenWidth * resource.getHeight() / resource.getWidth();
                         mPhotoSizeMap.put(gank.getUrl(), height);
                     }

                     ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
                     lp.width = halfScreenWidth;
                     lp.height = height;
                     return false;
                 }
             })
             .into(iv);
        iv.getLayoutParams().width = halfScreenWidth;
    }
}
