package com.ane.expresstokenapp.modules.main;

import android.os.Bundle;
import android.view.View;

import com.ane.expresstokenapp.App;
import com.ane.expresstokenapp.R;
import com.ane.expresstokenapp.base.BaseMvpActivity;

public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void componentInject() {
        DaggerMainActivityComponent.builder().
                appComponent(((App) getApplication()).getAppComponent())
                .build().inject(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.siteListService(0, "0906002", 0);
            }
        });
    }

    @Override
    protected void initData() {

    }
}
