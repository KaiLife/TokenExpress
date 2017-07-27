package com.ane.expresstokenapp.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ane.expresstokenapp.App;
import com.ane.expresstokenapp.utils.ToastUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import javax.inject.Inject;

public abstract class BaseMvpActivity<P extends BasePresenter> extends RxAppCompatActivity implements BaseView {

    protected final String TAG = this.getClass().getSimpleName();

    @Inject
    protected P mPresenter;
    protected Activity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getActivityManage().addActivity(this);
        setContentView(getContentViewId());
        mActivity = this;
        componentInject();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        initView(savedInstanceState);
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideProgressBar();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        App.getActivityManage().finishActivity(this);
    }

    @Override
    public void showToast(String text) {
        ToastUtil.showShort(text);
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    protected abstract void componentInject();

    protected abstract int getContentViewId();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initData();
}
