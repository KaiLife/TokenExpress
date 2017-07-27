package com.ane.expresstokenapp.base;

import com.ane.expresstokenapp.net.RetrofitHelper;
import com.ane.expresstokenapp.rx.OnEventListener;
import com.ane.expresstokenapp.rx.RxBusHelper;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class RxPresenter<V extends BaseView> implements BasePresenter<V> {

    protected RetrofitHelper retrofitHelper;
    protected V mView;
    //保存观察者和订阅者的订阅关系对象
    public CompositeDisposable compositeDisposable;

    public RxPresenter(RetrofitHelper retrofitHelper) {
        this.retrofitHelper = retrofitHelper;
    }

    /**
     * 使用Disposable 必须进行订阅管理
     *
     * @param disposable
     */
    protected void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    protected <U> void addRxBusSubscribe(Class<U> eventType, OnEventListener<U> listener) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        RxBusHelper.doOnMainThread(eventType, compositeDisposable, listener);
//        compositeDisposable.add(RxBus.getDefault().toFlowable(eventType).compose(RxUtil.<U>io2MainFlowable()).subscribe(consumer));
    }

    protected void dispose() {
        //解除compositeDisposable中所有保存的Disposable对象中的订阅关系
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
            compositeDisposable = null;
        }
    }

    @Override
    public void attachView(V view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
        dispose();
    }

}
