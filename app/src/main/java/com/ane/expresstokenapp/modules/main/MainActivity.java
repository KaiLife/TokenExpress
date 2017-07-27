package com.ane.expresstokenapp.modules.main;

import android.os.Bundle;

import com.ane.expresstokenapp.R;
import com.ane.expresstokenapp.base.BaseMvpActivity;

public class MainActivity extends BaseMvpActivity<MainContract.Presenter> implements MainContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void componentInject() {
//        getActivityComponent().inject(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }
}
