package com.ane.expresstokenapp.rx;


public interface OnEventListener<T> {
    void onEvent(T t);

    void onError(Throwable e);
}
