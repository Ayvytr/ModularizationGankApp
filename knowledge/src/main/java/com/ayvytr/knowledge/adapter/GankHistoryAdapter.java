package com.ayvytr.knowledge.adapter;

import android.content.Context;

import com.ayvytr.baseadapter.ViewHolder;
import com.ayvytr.baseadapter.wrapper.EmptyWrapperAdapter;
import com.ayvytr.knowledge.R;

/**
 * @author admin
 */
public class GankHistoryAdapter extends EmptyWrapperAdapter<String> {
    public GankHistoryAdapter(Context context) {
        super(context, R.layout.layout_item_gank_history);
    }

    @Override
    public void convert(ViewHolder holder, String s, int position) {
        holder.setText(R.id.tv, s);
    }
}
