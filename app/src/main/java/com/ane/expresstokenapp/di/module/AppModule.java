package com.ane.expresstokenapp.di.module;

import com.ane.expresstokenapp.App;
import com.ane.expresstokenapp.net.RetrofitHelper;
import com.ane.expresstokenapp.net.api.FileUploadApis;
import com.ane.expresstokenapp.net.api.MainApis;
import com.ane.expresstokenapp.widget.loadingdialog.StyleManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private App mApplication;

    public AppModule(App mApplication) {
        this.mApplication = mApplication;
    }

    @Singleton
    @Provides
    App getApplication() {
        return mApplication;
    }

    @Singleton
    @Provides
    StyleManager provideStyleManager() {
        StyleManager s = new StyleManager();
        //在这里调用方法设置s的属性
        s.Anim(false).repeatTime(0).contentSize(-1).intercept(true);
        return s;
    }

    @Singleton
    @Provides
    RetrofitHelper provideRetrofitHelper(MainApis mainApis, FileUploadApis fileUploadApis) {
        return new RetrofitHelper(mainApis, fileUploadApis);
    }
}
