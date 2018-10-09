package com.ayvytr.knowledge.adapter;

import android.content.Context;

import com.ayvytr.commonlibrary.view.BaseAdapter;
import com.ayvytr.knowledge.model.bean.AndroidData;

import java.util.List;

/**
 * @author admin
 */
public class AndroidAdapter extends BaseAdapter<AndroidData> {

    public AndroidAdapter(Context context, int layoutResId, List<AndroidData> list) {
        super(context, layoutResId, list);
    }

    @Override
    protected void convert(AndroidData androidData, int position) {

    }
}
