package com.ane.expresstokenapp.modules.login;

import android.os.Bundle;
import com.ane.expresstokenapp.R;
import com.ane.expresstokenapp.App;
import com.ane.expresstokenapp.base.BaseMvpActivity;

public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.View {

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
    }
}
