package com.ane.expresstokenapp.modules.login;

import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

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
        initState();
    }

    @Override
    protected void initData() {
        mBind.setPresenter(mPresenter);
    }

    /**
     * 沉浸式状态栏
     */
    private void initState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
}
