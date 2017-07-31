package com.ane.expresstokenapp.modules.main;

import android.os.Bundle;

import com.ane.expresstokenapp.App;
import com.ane.expresstokenapp.R;
import com.ane.expresstokenapp.base.BaseMvpActivity;
import com.ane.expresstokenapp.widget.viewgroup.TitleBar;

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
        findViewById(R.id.btn_test).setOnClickListener(v -> mPresenter.siteListService(0, "0906002", 0));

        TitleBar titleBar = (TitleBar) findViewById(R.id.titlebar);
        titleBar.setDelegate(new TitleBar.Delegate() {
            @Override
            public void onClickLeftCtv() {
                onBackPressed();
            }

            @Override
            public void onClickTitleCtv() {

            }

            @Override
            public void onClickRightCtv() {

            }

            @Override
            public void onClickRightSecondaryCtv() {

            }
        });
    }

    @Override
    protected void initData() {

    }
}
