package com.ane.lib_uiframework.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public abstract class BaseActivity<P extends BasePresenter> extends RxAppCompatActivity implements BaseView {

    protected final String TAG = this.getClass().getSimpleName();

    @Inject
    protected P mPresenter;
    protected Activity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        mActivity = this;
        componentInject();
        initView(savedInstanceState);
        initData();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        hideProgressBar();
    }

    protected abstract void componentInject();

    protected abstract int getContentViewId();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initData();
}
