package com.ane.expresstokenapp.di.module;

import com.ane.expresstokenapp.App;
import com.ane.expresstokenapp.BuildConfig;
import com.ane.expresstokenapp.net.RetrofitHelper;
import com.ane.expresstokenapp.net.api.FileUploadApis;
import com.ane.expresstokenapp.net.api.MainApis;
import com.ane.expresstokenapp.utils.Constants;
import com.ane.expresstokenapp.widget.loadingdialog.StyleManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;

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

    @Singleton
    @Provides
    RealmConfiguration provideRealmConfiguration(RealmMigration migration) {
        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(BuildConfig.DB_VERSION)
                .name(Constants.DB_NAME)
//                .migration(migration) //指定迁移操作的迁移类
                .deleteRealmIfMigrationNeeded() //声明版本冲突时自动删除原数据库
                .build();
        return config;
    }

    @Singleton
    @Provides
    RealmMigration provideRealmMigration() {
        //升级数据库
        RealmMigration migration = (realm, oldVersion, newVersion) -> {
            if (oldVersion > newVersion) {//数据库降级

            } else if (oldVersion < newVersion) {//数据库升级

            }
        };
        return migration;
    }
}
