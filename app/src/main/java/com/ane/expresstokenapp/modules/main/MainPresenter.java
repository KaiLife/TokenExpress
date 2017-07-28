package com.ane.expresstokenapp.modules.main;


import com.ane.expresstokenapp.base.RxPresenter;
import com.ane.expresstokenapp.net.HttpParams;
import com.ane.expresstokenapp.net.HttpResult;
import com.ane.expresstokenapp.net.HttpUrlManager;
import com.ane.expresstokenapp.net.HttpUtil;
import com.ane.expresstokenapp.net.RetrofitHelper;
import com.ane.expresstokenapp.rx.BaseObserver;
import com.ane.expresstokenapp.rx.RxUtil;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2017/7/27 0027.
 */
public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {

    @Inject
    public MainPresenter(RetrofitHelper retrofitHelper) {
        super(retrofitHelper);
    }

    @Override
    public void siteListService(long lastTime, String siteCode, int siteId) {
        Map<String, Object> params = new HashMap<>();
        params.put("lastTime", lastTime);
        params.put("siteCode", siteCode);
        params.put("siteId", siteId);
        String postParam = new Gson().toJson(params);
        HttpParams httpParams = HttpUtil.encodeData(HttpUrlManager.SiteServiceImpl, postParam, false);

        Disposable disposable = retrofitHelper.commonPostRequest(httpParams)
                .compose(RxUtil.<HttpResult>io2MainObservable())
                .compose(RxUtil.handleHttpResultCustom(JSONObject.class))
//                .compose(RxUtil.<JSONObject>progressDialogObservable(mView, "", false))
                .subscribeWith(new BaseObserver<JSONObject>() {
                    @Override
                    public void onHandleSuccessful(JSONObject jsonObject) {

                    }

                    @Override
                    public void onHandleError(Throwable t, String errorTips) {
                        mView.showToast(errorTips);
                    }
                });
        addDisposable(disposable);
    }
}
