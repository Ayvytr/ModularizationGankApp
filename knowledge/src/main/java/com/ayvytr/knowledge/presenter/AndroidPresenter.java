package com.ayvytr.knowledge.presenter;


import com.ayvytr.commonlibrary.bean.BaseGank;
import com.ayvytr.knowledge.R;
import com.ayvytr.knowledge.contract.AndroidContract;
import com.ayvytr.knowledge.model.AndroidModel;
import com.ayvytr.mvp.BasePresenter;
import com.ayvytr.mvp.RxUtils;
import com.ayvytr.commonlibrary.BaseObserver;

/**
 * @author admin
 */
public class AndroidPresenter extends BasePresenter<AndroidContract.Model, AndroidContract.View> {
    public AndroidPresenter(AndroidContract.View rootView) {
        super(new AndroidModel(), rootView);
    }

    public void requestAndroidGank(int pageSize, int currentPage) {
        mModel.getAndroidGank(pageSize, currentPage)
              .compose(RxUtils.<BaseGank>applySchedulers(mView))
              .compose(RxUtils.<BaseGank>bindToLifecycle(mView))
              .subscribe(new BaseObserver<BaseGank>() {
                  @Override
                  public void onNext(BaseGank gank) {
                      if(gank.isError()) {
                          mView.showMessage(R.string.request_data_error);
                      } else {
                          mView.showAndroidGank(gank);
                      }
                  }
              });
    }
}
