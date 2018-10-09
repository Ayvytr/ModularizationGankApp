package com.ayvytr.knowledge.presenter;


import com.ayvytr.knowledge.contract.AndroidContract;
import com.ayvytr.knowledge.model.AndroidModel;
import com.ayvytr.mvp.BasePresenter;

/**
 * @author admin
 */
public class AndroidPresenter extends BasePresenter<AndroidContract.Model, AndroidContract.View> {
    public AndroidPresenter(AndroidContract.View rootView) {
        super(new AndroidModel(), rootView);
    }
}
