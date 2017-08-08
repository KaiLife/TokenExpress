package com.ane.expresstokenapp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ane.expresstokenapp.App;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.umeng.analytics.MobclickAgent;

public abstract class BaseSimpleActivity extends RxAppCompatActivity {

    protected final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getActivityManage().addActivity(this);
        setContentView(getContentViewId());
        init(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //友盟页面统计
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //友盟页面统计
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getActivityManage().finishActivity(this);
        App.getRefWatcher().watch(this);
    }

    protected abstract int getContentViewId();

    protected abstract void init(Bundle savedInstanceState);
}
