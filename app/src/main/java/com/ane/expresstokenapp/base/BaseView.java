package com.ane.expresstokenapp.base;

public interface BaseView {
    void showToast(String text);

    void showLoading(String text, boolean cancelable);

    void hideProgressBar();

    void showLoadSuccess();

    void showLoadFailed();
}
