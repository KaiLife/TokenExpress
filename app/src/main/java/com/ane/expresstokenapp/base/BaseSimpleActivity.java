package com.ane.expresstokenapp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ane.expresstokenapp.App;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

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
    protected void onDestroy() {
        super.onDestroy();
        App.getActivityManage().finishActivity(this);
    }

    protected abstract int getContentViewId();

    protected abstract void init(Bundle savedInstanceState);
}
