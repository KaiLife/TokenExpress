package com.ane.expresstokenapp.base;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.ane.expresstokenapp.App;
import com.ane.expresstokenapp.R;
import com.ane.expresstokenapp.utils.ToastUtil;
import com.ane.expresstokenapp.widget.loadingdialog.LoadingDialog;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.umeng.analytics.MobclickAgent;

import javax.inject.Inject;

import io.realm.Realm;

public abstract class BaseMvpvmActivity<P extends BasePresenter, V extends ViewDataBinding> extends RxAppCompatActivity implements BaseView {

    protected final String TAG = this.getClass().getSimpleName();

    @Inject
    protected P mPresenter;
    protected Activity mActivity;
    protected V mBind;

    protected LoadingDialog loadingDialog;
    protected Realm realm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getActivityManage().addActivity(this);
        mBind = DataBindingUtil.setContentView(this, getContentViewId());
        mActivity = this;
        componentInject();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
//        realm = Realm.getDefaultInstance();//需要的数据库操作在子类initData()方法里调用此方法
        initView(savedInstanceState);
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG);
        //友盟页面统计
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
        //友盟页面统计
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideProgressBar();
        if (realm != null) {
            realm.close();
            realm = null;
        }
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        App.getActivityManage().finishActivity(this);

        App.getRefWatcher().watch(this);
    }

    @Override
    public void showToast(String text) {
        ToastUtil.showShort(text);
    }

    @Override
    public void showLoading(String text, boolean cancelable) {
        hideProgressBar();
        loadingDialog = new LoadingDialog(this);
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

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initData();
}
