package com.ane.expresstokenapp.modules.main;

import com.ane.expresstokenapp.base.RxPresenter;
import com.ane.expresstokenapp.net.RetrofitHelper;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/7/27 0027.
 */
public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {

    @Inject
    public MainPresenter(RetrofitHelper retrofitHelper) {
        super(retrofitHelper);
    }

    @Override
    public void siteListService() {

    }
}
