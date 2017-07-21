package com.ane.lib_uiframework.di.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/7/21 0021.
 */
@Module
public final class AppModule {

    private final Context mContext;

    AppModule(Context mContext) {
        this.mContext = mContext;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }
}
