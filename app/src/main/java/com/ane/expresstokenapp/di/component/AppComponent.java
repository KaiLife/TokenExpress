package com.ane.expresstokenapp.di.component;


import com.ane.expresstokenapp.App;
import com.ane.expresstokenapp.di.module.AppModule;
import com.ane.expresstokenapp.di.module.HttpModule;
import com.ane.expresstokenapp.net.RetrofitHelper;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {
    App getContext();

    RetrofitHelper retrofitHelper();
}