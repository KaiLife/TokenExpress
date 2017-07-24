package com.ane.lib_uiframework.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/7/24 0024.
 */

public abstract class BaseFragment<P extends BasePresenter> extends RxFragment implements BaseView {

    protected final String TAG = this.getClass().getSimpleName();

    @Inject
    protected P mPresenter;
    protected View mView;
    protected Activity mActivity;
    protected Context mContext;
    protected boolean isInited = false;

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getContentViewId(), null);
        componentInject();
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        if (savedInstanceState == null) {
            if (!isHidden()) {
                isInited = true;
                initView();
                initData();
            }
        } else {
            if (!isVisible()) {
                isInited = true;
                initView();
                initData();
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!isInited && !hidden) {
            isInited = true;
            initView();
            initData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }

    protected abstract void componentInject();

    protected abstract int getContentViewId();

    protected abstract void initView();

    protected abstract void initData();
}
