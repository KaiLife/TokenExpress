package com.ane.expresstokenapp.di.component;


import com.ane.expresstokenapp.App;
import com.ane.expresstokenapp.di.module.AppModule;
import com.ane.expresstokenapp.di.module.HttpModule;
import com.ane.expresstokenapp.net.RetrofitHelper;
import com.ane.expresstokenapp.widget.loadingdialog.StyleManager;

import javax.inject.Singleton;

import dagger.Component;
import io.realm.RealmConfiguration;

@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {
    App getContext();

    RetrofitHelper retrofitHelper();

    StyleManager getStyleManager();

    RealmConfiguration getRealmConfiguration();
}
