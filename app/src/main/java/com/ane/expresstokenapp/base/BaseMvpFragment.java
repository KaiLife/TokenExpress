package com.ane.expresstokenapp.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ane.expresstokenapp.R;
import com.ane.expresstokenapp.utils.ToastUtil;
import com.ane.expresstokenapp.widget.loadingdialog.LoadingDialog;
import com.trello.rxlifecycle2.components.support.RxFragment;

import javax.inject.Inject;

public abstract class BaseMvpFragment<P extends BasePresenter> extends RxFragment implements BaseView {

    protected final String TAG = this.getClass().getSimpleName();

    @Inject
    protected P mPresenter;
    protected View mView;
    protected Activity mActivity;
    protected Context mContext;
    protected boolean isInited = false;

    protected LoadingDialog loadingDialog;

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
        hideProgressBar();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }

    @Override
    public void showToast(String text) {
        ToastUtil.showShort(text);
    }

    @Override
    public void showLoading(String text, boolean cancelable) {
        hideProgressBar();
        loadingDialog = new LoadingDialog(mActivity);
        loadingDialog.setSuccessText(getString(R.string.dialog_load_success))
                .setFailedText(getString(R.string.dialog_load_failed))
                .setInterceptBack(cancelable)
                .setLoadingText(TextUtils.isEmpty(text) ? getString(R.string.dialog_loading) : text);
        loadingDialog.show();
    }

    @Override
    public void hideProgressBar() {
        if (loadingDialog != null) {
            loadingDialog.close();
            loadingDialog = null;
        }
    }

    @Override
    public void showLoadSuccess() {
        if (loadingDialog != null) {
            loadingDialog.loadSuccess();
            loadingDialog = null;
        }
    }

    @Override
    public void showLoadFailed() {
        if (loadingDialog != null) {
            loadingDialog.loadFailed();
            loadingDialog = null;
        }
    }

    protected abstract void componentInject();

    protected abstract int getContentViewId();

    protected abstract void initView();

    protected abstract void initData();
}
