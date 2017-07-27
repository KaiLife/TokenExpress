package com.ane.expresstokenapp.rx;

import android.util.Log;

import com.ane.expresstokenapp.net.ApiException;
import com.ane.expresstokenapp.utils.Constants;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * Observer，使用此方式，需要进行订阅管理
 *
 */

public abstract class BaseObserver<T> extends DisposableObserver<T> {
    private static final String TAG = "Observer";

    @Override
    public void onNext(T t) {
        try {
            onHandleSuccessful(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable t) {
        String errorTips = "";
        try {
            if (t instanceof SocketTimeoutException) {
                Log.e(TAG, "onError: SocketTimeoutException----" + t.getMessage());
                errorTips = Constants.SOCKET_TIMEOUT_EXCEPTION;
                onShowError(errorTips);
            } else if (t instanceof ConnectException) {
                Log.e(TAG, "onError: ConnectException-----" + t.getMessage());
                errorTips = Constants.CONNECT_EXCEPTION;
                onShowError(errorTips);
            } else if (t instanceof UnknownHostException) {
                Log.e(TAG, "onError: UnknownHostException-----" + t.getMessage());
                errorTips = Constants.UNKNOWN_HOST_EXCEPTION;
                onShowError(errorTips);
            } else if (t instanceof HttpException) {
                Log.e(TAG, "onError: HttpException-----" + t.getMessage());
                errorTips = Constants.HTTP_EXCEPTION;
                onShowError(errorTips);
            } else if (t instanceof ApiException) {
                Log.e(TAG, "onError: ApiException-----" + t.getMessage());
//                onShowError(t.getMessage());
                errorTips = t.getMessage();
                onHandleApiException((ApiException) t);
            } else if (t instanceof NullPointerException) {
                Log.e(TAG, "onError:NullPointerException----" + t.getMessage());
                errorTips = Constants.NULL_POINT_EREXCEPTION;
            } else {
                Log.e(TAG, "onError:----" + t.getClass().getName());
                Log.e(TAG, "onError:----" + t.getMessage());
                errorTips = Constants.OTHER_EXCEPTION;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            onHandleError(t, errorTips);
            onFinish();
        }
    }

    @Override
    public void onComplete() {
        onFinish();
    }

    public abstract void onHandleSuccessful(T t) throws Exception;

    public void onHandleApiException(ApiException e) {
    }

    public abstract void onHandleError(Throwable t, String errorTips);

    /**
     * 请求结束调用此方法，不管请求成功、失败
     */
    public void onFinish() {
    }

    void onShowError(String text) {
//        Log.e("onShowError", text);
    }
}
