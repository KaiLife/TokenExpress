package com.ane.expresstokenapp.di.module;

import com.ane.expresstokenapp.App;
import com.ane.expresstokenapp.modules.login.User;
import com.ane.expresstokenapp.net.RetrofitHelper;
import com.ane.expresstokenapp.net.api.FileUploadApis;
import com.ane.expresstokenapp.net.api.MainApis;

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
    public App getApplication() {
        return mApplication;
    }

    @Singleton
    @Provides
    RetrofitHelper provideRetrofitHelper(MainApis mainApis, FileUploadApis fileUploadApis) {
        return new RetrofitHelper(mainApis, fileUploadApis);
    }

    @Singleton
    @Provides
    public User provideUserModel() {
        return new User("jack", 18);
    }
}
