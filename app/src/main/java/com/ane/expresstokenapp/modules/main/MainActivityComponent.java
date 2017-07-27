package com.ane.expresstokenapp.modules.main;

import com.ane.expresstokenapp.di.component.AppComponent;
import com.ane.expresstokenapp.di.scope.ActivityScope;

import dagger.Component;

/**
 * Created by Administrator on 2017/7/27 0027.
 */
@ActivityScope
@Component(dependencies = AppComponent.class)
public interface MainActivityComponent {
    void inject(MainActivity mainActivity);
}
