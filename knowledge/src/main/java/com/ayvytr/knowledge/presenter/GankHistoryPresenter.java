package com.ayvytr.knowledge.presenter;

import com.ayvytr.commonlibrary.BaseObserver;
import com.ayvytr.commonlibrary.bean.GankHistory;
import com.ayvytr.knowledge.R;
import com.ayvytr.knowledge.contract.GankHistoryContract;
import com.ayvytr.knowledge.model.GankHistoryModel;
import com.ayvytr.mvpbase.BasePresenter;
import com.ayvytr.mvprxlifecycle.RxUtils;

/**
 * @author admin
 */
public class GankHistoryPresenter extends BasePresenter<GankHistoryContract.Model, GankHistoryContract.View> {
    public GankHistoryPresenter(GankHistoryContract.View view) {
        super(new GankHistoryModel(), view);
    }

    public void requestGankHistory() {
        mModel.getGankHistory()
              .compose(RxUtils.<GankHistory>bindToLifecycle(mView))
              .compose(RxUtils.<GankHistory>subscribeIo(mView))
              .subscribe(new BaseObserver<GankHistory>() {
                  @Override
                  public void onNext(GankHistory gankHistory) {
                      if(gankHistory.isError()) {
                          mView.showError(R.string.request_data_error);
                      } else {
                         mView.showGankHistory(gankHistory.getResults());
                      }
                  }
              });
    }
}
