package com.ayvytr.knowledge.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.ayvytr.baseadapter.ViewHolder;
import com.ayvytr.baseadapter.wrapper.EmptyWrapperAdapter;
import com.ayvytr.commonlibrary.bean.Gank;
import com.ayvytr.easykotlin.ui.ViewKt;
import com.ayvytr.knowledge.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
 */
public class AndroidAdapter extends EmptyWrapperAdapter<Gank> {
    public AndroidAdapter(Context context){
        this(context, R.layout.layout_item_android, new ArrayList<Gank>(0));
    }

    public AndroidAdapter(Context context, int layoutResId, List<Gank> list) {
        super(context, layoutResId, list);
    }

    @Override
    public void convert(ViewHolder holder, Gank gank, int position) {
        List<String> images = gank.getImages();
        boolean showImage = images != null && !images.isEmpty();
        if(showImage) {
            Glide.with(mContext)
                 .load(images.get(0))
                 .into((ImageView) holder.getView(R.id.iv));
        }
        ViewKt.show(holder.getView(R.id.iv), showImage);

        holder.setText(R.id.tv_title, gank.getDesc());
        holder.setText(R.id.tv_date, gank.getPublishedAt());
        holder.setText(R.id.tv_who, gank.getWho());
    }
}
