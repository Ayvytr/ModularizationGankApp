package com.ayvytr.knowledge.adapter;

import android.content.Context;

import com.ayvytr.commonlibrary.bean.Gank;
import com.ayvytr.knowledge.R;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;

/**
 * @author admin
 */
public class GankHistoryContentAdapter extends MultiItemTypeAdapter<Gank> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_CONTENT = 1;

    public GankHistoryContentAdapter(final Context context) {
        super(context, new ArrayList<Gank>(0));
        addItemViewDelegate(TYPE_CONTENT, new ItemViewDelegate<Gank>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.layout_item_android;
            }

            @Override
            public boolean isForViewType(Gank item, int position) {
                return getItemViewType(position) == TYPE_CONTENT;
            }

            @Override
            public void convert(ViewHolder holder, Gank gank, int position) {
                holder.setText(R.id.tv_title, gank.getDesc());
                holder.setText(R.id.tv_date, gank.getPublishedAt());
                holder.setText(R.id.tv_who, gank.getWho());
            }
        });
        addItemViewDelegate(TYPE_HEADER, new ItemViewDelegate<Gank>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.layout_item_gank_history;
            }

            @Override
            public boolean isForViewType(Gank item, int position) {
                return getItemViewType(position) == TYPE_HEADER;
            }

            @Override
            public void convert(ViewHolder holder, Gank gank, int position) {
                holder.setText(R.id.tv, gank.getType());
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if(getItemAt(position).isHeader()) {
            return TYPE_HEADER;
        }

        return TYPE_CONTENT;
    }
}
