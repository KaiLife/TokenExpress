package com.ane.expresstokenapp.rx;

import android.util.Log;

import com.ane.expresstokenapp.base.BaseView;
import com.ane.expresstokenapp.net.ApiException;
import com.ane.expresstokenapp.net.HttpResult;
import com.ane.expresstokenapp.net.HttpUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxUtil {

    private final static ObservableTransformer sObservableTransformer = new ObservableTransformer() {
        @Override
        public ObservableSource apply(Observable upstream) {
            return upstream.retry(0).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }
    };

    /**
     * 指定线程处理（compose）(Observable)
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> io2MainObservable() {
        return sObservableTransformer;
    }

/*    public static <T> ObservableTransformer<T, T> io2MainObservable() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }*/


    private final static FlowableTransformer sFlowableTransformer = new FlowableTransformer() {
        @Override
        public Publisher apply(Flowable upstream) {
            return upstream.retry(0).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }
    };

    /**
     * 指定线程处理（compose）(Flowable)
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> io2MainFlowable() {
        return sFlowableTransformer;
    }

    /**
     * 返回结果处理（compose）(Observable)
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<HttpResult<T>, T> handleHttpResultObservable() {
        return new ObservableTransformer<HttpResult<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<HttpResult<T>> upstream) {
                return upstream.concatMap(new Function<HttpResult<T>, ObservableSource<? extends T>>() {
                    @Override
                    public ObservableSource<? extends T> apply(HttpResult<T> tHttpResult) throws Exception {
                        if (tHttpResult.isResult()) {
                            return createDataObservable(tHttpResult.getResultInfo());
                        } else {
                            if (HttpUtil.needShowNote(tHttpResult.getResultCode())) {
                                RxBusHelper.post(new ApiException(tHttpResult.getReason(), tHttpResult.getResultCode()));
                            }
                            return Observable.error(new ApiException(tHttpResult.getReason(), tHttpResult.getResultCode()));
                        }
                    }
                });
            }
        };
    }

    /**
     * 返回结果处理（compose）(Flowable)
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<HttpResult<T>, T> handleHttpResultFlowable() {
        return new FlowableTransformer<HttpResult<T>, T>() {
            @Override
            public Publisher<T> apply(Flowable<HttpResult<T>> upstream) {
                return upstream.concatMap(new Function<HttpResult<T>, Publisher<? extends T>>() {
                    @Override
                    public Publisher<? extends T> apply(HttpResult<T> tHttpResult) throws Exception {
                        if (tHttpResult.isResult()) {
                            return createDataFlowable(tHttpResult.getResultInfo());
                        } else {
                            if (HttpUtil.needShowNote(tHttpResult.getResultCode())) {
                                RxBusHelper.post(new ApiException(tHttpResult.getReason(), tHttpResult.getResultCode()));
                            }
                            return Flowable.error(new ApiException(tHttpResult.getReason(), tHttpResult.getResultCode()));
                        }
                    }
                });
            }
        };
    }

    /**
     * 返回结果处理（compose）(Observable)---只返回json中的msg数据
     *
     * @return
     */
    public static ObservableTransformer<HttpResult<Object>, String> handleHttpResultMessage() {
        return new ObservableTransformer<HttpResult<Object>, String>() {
            @Override
            public ObservableSource<String> apply(Observable<HttpResult<Object>> upstream) {
                return upstream.concatMap(new Function<HttpResult<Object>, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(HttpResult<Object> tHttpResult) throws Exception {
                        if (tHttpResult.isResult()) {
                            return createDataObservable(tHttpResult.getReason());
                        } else {
                            if (HttpUtil.needShowNote(tHttpResult.getResultCode())) {
                                RxBusHelper.post(new ApiException(tHttpResult.getReason(), tHttpResult.getResultCode()));
                            }
                            return Observable.error(new ApiException(tHttpResult.getReason(), tHttpResult.getResultCode()));
                        }
                    }
                });
            }
        };

    }

    /**
     * 返回结果处理（compose）(Flowable)---只返回json中的msg数据
     *
     * @return
     */
    public static FlowableTransformer<HttpResult<Object>, String> handleHttpResultMessageFlowable() {
        return new FlowableTransformer<HttpResult<Object>, String>() {
            @Override
            public Publisher<String> apply(Flowable<HttpResult<Object>> upstream) {
                return upstream.concatMap(new Function<HttpResult<Object>, Publisher<String>>() {
                    @Override
                    public Publisher<String> apply(HttpResult<Object> tHttpResult) throws Exception {
                        if (tHttpResult.isResult()) {
                            return createDataFlowable(tHttpResult.getReason());
                        } else {
                            if (HttpUtil.needShowNote(tHttpResult.getResultCode())) {
                                RxBusHelper.post(new ApiException(tHttpResult.getReason(), tHttpResult.getResultCode()));
                            }
                            return Flowable.error(new ApiException(tHttpResult.getReason(), tHttpResult.getResultCode()));
                        }
                    }
                });
            }
        };

    }

    /**
     * 返回结果处理（compose）(Flowable)---返回data数据
     *
     * @return
     */
    public static FlowableTransformer<HttpResult, Object> handleObjectFlowable() {
        return new FlowableTransformer<HttpResult, Object>() {
            @Override
            public Publisher<Object> apply(Flowable<HttpResult> upstream) {
                return upstream.concatMap(new Function<HttpResult, Publisher<Object>>() {
                    @Override
                    public Publisher<Object> apply(HttpResult tHttpResult) throws Exception {
                        if (tHttpResult.isResult()) {
                            return createDataFlowable(tHttpResult.getResultInfo());
                        } else {
                            if (HttpUtil.needShowNote(tHttpResult.getResultCode())) {
                                RxBusHelper.post(new ApiException(tHttpResult.getReason(), tHttpResult.getResultCode()));
                            }
                            return Flowable.error(new ApiException(tHttpResult.getReason(), tHttpResult.getResultCode()));
                        }
                    }
                });
            }
        };

    }

    /**
     * 返回结果处理（compose）(Observable)---返回data数据
     *
     * @return
     */
    public static ObservableTransformer<HttpResult, Object> handleObjectObservable() {
        return new ObservableTransformer<HttpResult, Object>() {
            @Override
            public ObservableSource<Object> apply(Observable<HttpResult> upstream) {
                return upstream.concatMap(new Function<HttpResult, ObservableSource<Object>>() {
                    @Override
                    public ObservableSource<Object> apply(HttpResult tHttpResult) throws Exception {
                        if (tHttpResult.isResult()) {
                            return createDataObservable(tHttpResult.getResultInfo());
                        } else {
                            if (HttpUtil.needShowNote(tHttpResult.getResultCode())) {
                                RxBusHelper.post(new ApiException(tHttpResult.getReason(), tHttpResult.getResultCode()));
                            }
                            return Observable.error(new ApiException(tHttpResult.getReason(), tHttpResult.getResultCode()));
                        }
                    }
                });
            }
        };

    }

    /**
     * 返回结果处理（compose）(Flowable)
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<HttpResult, T> handleHttpResultCustom(final Class<T> clazz) {
        return new ObservableTransformer<HttpResult, T>() {
            @Override
            public ObservableSource<T> apply(Observable<HttpResult> upstream) {
                return upstream.concatMap(new Function<HttpResult, ObservableSource<? extends T>>() {
                    @Override
                    public ObservableSource<? extends T> apply(HttpResult httpResult) throws Exception {
                        String dataStr = httpResult.getResultInfo().toString();
                        if (httpResult.isResult()) {
                            T data;
                            if (clazz == String.class) {
                                data = (T) dataStr;
                            } else if (clazz == JSONObject.class) {
                                data = (T) new JSONObject(dataStr);
                            } else if (clazz == JSONArray.class) {
                                data = (T) new JSONArray(dataStr);
                            } else {
                                data = new Gson().fromJson(dataStr, clazz);
                            }
                            return createDataObservable(data);
                        } else {
                            if (HttpUtil.needShowNote(httpResult.getResultCode())) {
                                RxBusHelper.post(new ApiException(httpResult.getReason(), httpResult.getResultCode()));
                            }
                            return Observable.error(new ApiException(httpResult.getReason(), httpResult.getResultCode()));
                        }
                    }
                });
            }
        };
    }

    /**
     * 生成Observable
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableSource<T> createDataObservable(final T t) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> o) throws Exception {
                if (t != null) {
                    o.onNext(t);
                    o.onComplete();
                } else {
                    Log.e("Result", " t is null");
                    o.onError(new ApiException("", ""));
                }
            }
        });
    }

    /**
     * 生成 Flowable
     * 默认采用BackpressureStrategy.LATEST
     *
     * @param <T>
     * @return
     */
    public static <T> Publisher<T> createDataFlowable(final T t) {
        return createDataFlowable(t, BackpressureStrategy.LATEST);
    }

    /**
     * 生成 Flowable
     *
     * @param t
     * @param mode
     * @param <T>
     * @return
     */
    public static <T> Publisher<T> createDataFlowable(final T t, BackpressureStrategy mode) {
        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(FlowableEmitter<T> s) throws Exception {
                if (t != null) {
                    s.onNext(t);
                    s.onComplete();
                } else {
                    s.onError(new ApiException("", ""));
                }
            }
        }, mode);
    }

    /**
     * 指定开始和结束时显示进度条的Observable（compose）(Observable)
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> progressDialogObservable(final BaseView view, final String text, final boolean cancelable) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
//                        if (view != null)
//                            view.showProgressBar(text, cancelable);
                    }
                }).doOnTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        if (view != null)
                            view.hideProgressBar();
                    }
                });
            }
        };
    }

    /**
     * 指定开始和结束时显示进度条的Observable（compose）(Observable)
     *
     * @param view
     * @param text
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> progressDialogObservable(final BaseView view, final String text) {
        return progressDialogObservable(view, text, true);
    }


    /**
     * 指定开始和结束时显示进度条的Flowable（compose）(Flowable)
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> progressDialogFlowable(final BaseView view, final String text, final boolean cancelable) {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
//                        if (view != null)
//                            view.showProgressBar(text, cancelable);
                    }
                }).doOnTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        if (view != null)
                            view.hideProgressBar();
                    }
                });
            }
        };
    }

    /**
     * 指定开始和结束时显示进度条的Flowable（compose）(Flowable)
     *
     * @param view
     * @param text
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> progressDialogFlowable(final BaseView view, final String text) {
        return progressDialogFlowable(view, text, true);
    }

}
