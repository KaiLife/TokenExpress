package com.ane.expresstokenapp.di.module;

import android.app.Activity;

import com.ane.expresstokenapp.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @ActivityScope
    @Provides
    public Activity provideActivity() {
        return mActivity;
    }

}
