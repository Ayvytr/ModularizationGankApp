package com.ayvytr.knowledge.presenter;

import com.ayvytr.commonlibrary.BaseObserver;
import com.ayvytr.commonlibrary.bean.GankHistoryContent;
import com.ayvytr.knowledge.R;
import com.ayvytr.knowledge.contract.GankHistoryContentContract;
import com.ayvytr.knowledge.model.GankHistoryContentModel;
import com.ayvytr.mvp.BasePresenter;
import com.ayvytr.mvp.RxUtils;

/**
 * @author admin
 */
public class GankHistoryContentPresenter
        extends BasePresenter<GankHistoryContentContract.Model, GankHistoryContentContract.View> {
    public GankHistoryContentPresenter(GankHistoryContentContract.View view) {
        super(new GankHistoryContentModel(), view);
    }

    public void requestGankByDate(String date) {
        String[] split = date.split("-");
        mModel.getGankByDate(split[0], split[1], split[2])
              .compose(RxUtils.<GankHistoryContent>applySchedulers(mView))
              .compose(RxUtils.<GankHistoryContent>bindToLifecycle(mView))
              .subscribe(new BaseObserver<GankHistoryContent>() {
                  @Override
                  public void onNext(GankHistoryContent gankHistoryContent) {
                      if(gankHistoryContent.isError()) {
                          mView.showMessage(R.string.request_data_error);
                      } else {
                          mView.showGankByDate(gankHistoryContent);
                      }
                  }
              });
    }
}
