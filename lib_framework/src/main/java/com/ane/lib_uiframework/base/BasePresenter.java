package com.ane.lib_uiframework.base;

public interface BasePresenter<V extends BaseView> {

    void attachView(V view);

    void detachView();

}
