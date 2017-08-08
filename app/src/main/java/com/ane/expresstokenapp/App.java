package com.ane.expresstokenapp;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.ane.expresstokenapp.di.component.AppComponent;
import com.ane.expresstokenapp.di.component.DaggerAppComponent;
import com.ane.expresstokenapp.di.module.AppModule;
import com.ane.expresstokenapp.di.module.HttpModule;
import com.ane.expresstokenapp.utils.ActivityManage;
import com.ane.expresstokenapp.utils.log.CrashHandler;
import com.ane.expresstokenapp.widget.loadingdialog.LoadingDialog;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import io.realm.Realm;
import io.realm.log.RealmLog;

/**
 * Created by Administrator on 2017/7/26 0026.
 */

public class App extends Application {

    private static App app;
    private static ActivityManage activityManage;
    private AppComponent appComponent;
    private RefWatcher refWatcher;

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

        Realm.init(this);
        Realm.deleteRealm(getAppComponent().getRealmConfiguration());
        Realm.setDefaultConfiguration(getAppComponent().getRealmConfiguration());

        CrashHandler.getInstance().init(getApplicationContext());

        if (BuildConfig.DEBUG) {
            RealmLog.setLevel(Log.DEBUG);

            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return;
            }
            refWatcher = LeakCanary.install(this);
        } else {
            refWatcher = RefWatcher.DISABLED;
        }
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

    public static RefWatcher getRefWatcher() {
        return getApp().refWatcher;
    }

    public static ActivityManage getActivityManage() {
        return activityManage;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
