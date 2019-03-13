package com.ayvytr.girl.adapter.callback;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import com.ayvytr.commonlibrary.bean.Gank;

import java.util.List;

/**
 * @author Do
 */
public class GirlsCallback extends DiffUtil.Callback {
    private List<Gank> oldList;
    private List<Gank> list;

    public GirlsCallback(@NonNull List<Gank> oldList, @NonNull List<Gank> list) {
        this.oldList = oldList;
        this.list = list;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return list.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).get_id().equals(list.get(newItemPosition).get_id());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).get_id().equals(list.get(newItemPosition).get_id());
//        return oldList.get(oldItemPosition).getDesc().equals(list.get(newItemPosition).getDesc());
    }
}
