package com.ayvytr.commonlibrary.view;

import android.content.Context;
import android.support.annotation.LayoutRes;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by shenminjie on 2016/10/20.
 * 适配器，模拟列表
 */

public abstract class BaseAdapter<T> extends EmptyWrapper<T> {
    private CommonAdapter mAdapter;

    public BaseAdapter(Context context, @LayoutRes int layoutResId, List<T> list) {
        super();
        mAdapter = new CommonAdapter<T>(context, layoutResId, list) {
            @Override
            protected void convert(ViewHolder holder, T t, int position) {
                BaseAdapter.this.convert(t, position);
            }
        };
        setInnerAdapter(mAdapter);
    }

    protected abstract void convert(T t, int position);
}

