package com.ane.expresstokenapp.modules.login;

import android.view.View;

import com.ane.expresstokenapp.base.RxPresenter;
import com.ane.expresstokenapp.net.RetrofitHelper;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/8/10 0010.
 */

public class LoginPresenter extends RxPresenter<LoginContract.View> implements LoginContract.Presenter{

    @Inject
    public LoginPresenter(RetrofitHelper retrofitHelper) {
        super(retrofitHelper);
    }

    @Override
    public void login(View v) {
        mView.showToast("登录");
    }
}
