package com.ayvytr.knowledge.adapter;

import android.content.Context;

import com.ayvytr.commonlibrary.bean.Gank;
import com.ayvytr.knowledge.R;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapperAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
 */
public class AndroidAdapter extends EmptyWrapperAdapter<Gank> {
    public AndroidAdapter(Context context, int layoutResId) {
        this(context, layoutResId, new ArrayList<Gank>(0));
    }

    public AndroidAdapter(Context context, int layoutResId, List<Gank> list) {
        super(context, layoutResId, list);
    }

    @Override
    public void convert(ViewHolder holder, Gank gank, int position) {
        holder.setText(R.id.tv_title, gank.getDesc());
        holder.setText(R.id.tv_date, gank.getPublishedAt());
        holder.setText(R.id.tv_who, gank.getWho());
    }
}
