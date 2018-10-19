package com.ayvytr.knowledge.view.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ayvytr.commonlibrary.GankType;
import com.ayvytr.commonlibrary.bean.Gank;
import com.ayvytr.commonlibrary.bean.GankHistoryContent;
import com.ayvytr.commonlibrary.constant.KnowledgeConstant;
import com.ayvytr.commonlibrary.constant.WebConstant;
import com.ayvytr.easykotlin.context.ScreenKt;
import com.ayvytr.knowledge.R;
import com.ayvytr.knowledge.R2;
import com.ayvytr.knowledge.adapter.GankHistoryContentAdapter;
import com.ayvytr.knowledge.contract.GankHistoryContentContract;
import com.ayvytr.knowledge.presenter.GankHistoryContentPresenter;
import com.ayvytr.logger.L;
import com.ayvytr.mvp.BaseMvpActivity;
import com.ayvytr.mvp.RxUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

@Route(path = KnowledgeConstant.GANK_HISTORY_CONTENT)
public class GankHistoryContentActivity extends BaseMvpActivity<GankHistoryContentPresenter>
        implements GankHistoryContentContract.View {


    @BindView(R2.id.iv_header)
    ImageView mIvHeader;
    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    @BindView(R2.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @BindView(R2.id.ct_layout)
    CollapsingToolbarLayout mCtLayout;
    @BindView(R2.id.rv_list)
    RecyclerView mRvList;

    private String date;
    private GankHistoryContentAdapter mContentAdapter;

    @Override
    protected GankHistoryContentPresenter getPresenter() {
        return new GankHistoryContentPresenter(this);
    }

    @Override
    public void initExtra() {
        date = getIntent().getStringExtra(KnowledgeConstant.EXTRA_DATE);
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mCtLayout.setTitle(date);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRvList.setLayoutManager(new LinearLayoutManager(getContext()));
        mContentAdapter = new GankHistoryContentAdapter(getContext());
        mContentAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Gank gank = mContentAdapter.getItemAt(position);
                if(gank.isHeader()) {
                    return;
                }

                ARouter.getInstance().build(WebConstant.WEBVIEW)
                       .withString(WebConstant.EXTRA_URL, gank.getUrl())
                       .withString(WebConstant.EXTRA_TITLE, gank.getDesc())
                       .navigation(getContext());
            }
        });
        mRvList.setAdapter(mContentAdapter);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if(TextUtils.isEmpty(date)) {
            showError(R.string.no_gank_date);
        }
        mPresenter.requestGankByDate(date);
    }

    @Override
    public int getContentViewRes() {
        return R.layout.activity_gank_history_content;
    }

    @Override
    public void showGankByDate(GankHistoryContent gankHistoryContent) {
        HashMap<String, List<Gank>> results = gankHistoryContent.getResults();
        L.e(results);
        List<Gank> girls = results.get(GankType.GIRLS.toString());
        showHeaderImage(girls);
        results.remove(GankType.GIRLS.toString());
        showContent(results);
    }

    private void showContent(HashMap<String, List<Gank>> results) {
        Observable.just(results)
                  .compose(RxUtils.<HashMap<String, List<Gank>>>applySchedulers(this))
                  .compose(RxUtils.<HashMap<String, List<Gank>>>bindToLifecycle(this))
                  .map(new Function<HashMap<String, List<Gank>>, List<Gank>>() {
                      @Override
                      public List<Gank> apply(HashMap<String, List<Gank>> map) {
                          Iterator<String> iterator = map.keySet().iterator();
                          List<Gank> list = new ArrayList<>();
                          while(iterator.hasNext()) {
                              List<Gank> ganks = map.get(iterator.next());
                              if(!ganks.isEmpty()) {
                                  list.add(new Gank(ganks.get(0).getType(), true));
                                  list.addAll(ganks);
                              }
                          }

                          return list;
                      }
                  })
                  .subscribe(new Consumer<List<Gank>>() {
                      @Override
                      public void accept(List<Gank> ganks) {
                          mContentAdapter.updateList(ganks);
                      }
                  });
    }

    private void showHeaderImage(List<Gank> girls) {
        Glide.with(getContext())
             .asBitmap()
             .load(girls.get(0).getUrl())
             .listener(new RequestListener<Bitmap>() {
                 @Override
                 public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target,
                                             boolean isFirstResource) {
                     return false;
                 }

                 @Override
                 public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target,
                                                DataSource dataSource, boolean isFirstResource) {
                     int height = ScreenKt.getScreenWidth(getContext()) * resource.getHeight() / resource
                             .getWidth();
                     ViewGroup.LayoutParams lp = mIvHeader.getLayoutParams();
                     lp.height = height;
                     return false;
                 }
             })
             .into(mIvHeader);
    }
}
