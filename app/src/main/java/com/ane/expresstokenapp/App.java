package com.ane.expresstokenapp;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.ane.expresstokenapp.di.component.AppComponent;
import com.ane.expresstokenapp.di.component.DaggerAppComponent;
import com.ane.expresstokenapp.di.module.AppModule;
import com.ane.expresstokenapp.di.module.HttpModule;
import com.ane.expresstokenapp.utils.ActivityManage;
import com.ane.expresstokenapp.widget.loadingdialog.LoadingDialog;

/**
 * Created by Administrator on 2017/7/26 0026.
 */

public class App extends Application {

    private static App app;
    private static ActivityManage activityManage;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        activityManage = ActivityManage.getInstance();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(getApp()))
                .httpModule(new HttpModule())
                .build();

        LoadingDialog.initStyle(getAppComponent().getStyleManager());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static App getApp() {
        return app;
    }

    public static ActivityManage getActivityManage() {
        return activityManage;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
