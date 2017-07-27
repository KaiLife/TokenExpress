package com.ane.expresstokenapp.rx;


import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.subscribers.SerializedSubscriber;

public class RxBus {

    private final FlowableProcessor<Object> mBus;
    private static volatile RxBus sRxBus = null;

    private RxBus() {
        //调用toSerialized()方法，保证线程安全
        mBus = PublishProcessor.create().toSerialized();
    }

     protected static synchronized RxBus getDefault() {
        if (sRxBus == null) {
            synchronized (RxBus.class) {
                if (sRxBus == null) {
                    sRxBus = new RxBus();
                }
            }
        }
        return sRxBus;
    }


    /**
     * 发送消息
     * @param o
     */
    protected void post(Object o) {
        new SerializedSubscriber<>(mBus).onNext(o);
    }

    /**
     * 确定接收消息的类型
     * @param aClass
     * @param <T>
     * @return
     */
    protected <T> Flowable<T> toFlowable(Class<T> aClass) {
        return mBus.ofType(aClass);
    }


    /**
     * 判断是否有订阅者
     * @return
     */
    protected boolean hasSubscribers() {
        return mBus.hasSubscribers();
    }

}
