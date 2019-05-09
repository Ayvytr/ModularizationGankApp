package com.ayvytr.knowledge.adapter;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayvytr.commonlibrary.bean.Gank;
import com.ayvytr.commonlibrary.util.GlideApp;
import com.ayvytr.commonlibrary.util.TimeKt;
import com.ayvytr.easykotlin.context.ContextKt;
import com.ayvytr.knowledge.R;

/**
 * @author wangdunwei
 */
public class AndroidPagedListAdapter extends PagedListAdapter<Gank, AndroidPagedListAdapter.Vh> {
    public AndroidPagedListAdapter() {
        super(new DiffUtil.ItemCallback<Gank>() {
            @Override
            public boolean areItemsTheSame(@NonNull Gank oldItem,@NonNull Gank newItem) {
                Log.d("DiffCallback","areItemsTheSame");
                return oldItem.get_id().equals(newItem.get_id());
            }

            @Override
            public boolean areContentsTheSame(@NonNull Gank oldItem,@NonNull Gank newItem) {
                Log.d("DiffCallback","areContentsTheSame");
                return oldItem.compareByAndroid(newItem);
            }

            @Nullable
            @Override
            public Object getChangePayload(@NonNull Gank oldItem, @NonNull Gank newItem) {
                return newItem;
            }
        });
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Vh(ContextKt.inflateRv(viewGroup.getContext(), R.layout.layout_item_android, viewGroup));
    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, int i) {
        vh.bind(i);
    }

    public class Vh extends RecyclerView.ViewHolder {

        private final ImageView iv;
        private final TextView tvTitle;
        private final TextView tvDate;
        private final TextView tvWho;

        public Vh(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvWho = itemView.findViewById(R.id.tv_who);
        }

        public void bind(int i) {
            Gank gank = getItem(i);
            GlideApp.with(iv.getContext())
                    .load(gank.getUrl())
                    .into(iv);
            tvTitle.setText(gank.getDesc());
            tvDate.setText(TimeKt.toLocalTime(gank.getPublishedAt()));
            tvWho.setText(gank.getWho());
        }
    }
}
