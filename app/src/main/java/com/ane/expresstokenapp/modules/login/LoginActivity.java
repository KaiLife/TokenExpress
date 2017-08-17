package com.ane.expresstokenapp.modules.login;

import android.os.Bundle;

import com.ane.expresstokenapp.App;
import com.ane.expresstokenapp.R;
import com.ane.expresstokenapp.base.BaseMvpvmActivity;
import com.ane.expresstokenapp.databinding.ActivityLoginBinding;

public class LoginActivity extends BaseMvpvmActivity<LoginPresenter, ActivityLoginBinding> implements LoginContract.View {

    @Override
    protected void componentInject() {
        DaggerLoginActivityComponent.builder()
                .appComponent(((App) getApplication()).getAppComponent())
                .build().inject(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
        mBind.setPresenter(mPresenter);
    }
}
