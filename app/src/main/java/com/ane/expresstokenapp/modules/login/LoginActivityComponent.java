package com.ane.expresstokenapp.modules.login;

import com.ane.expresstokenapp.di.scope.ActivityScope;

import dagger.Component;

/**
 * Created by Administrator on 2017/7/27 0027.
 */
@ActivityScope
@Component(/*dependencies = AppComponent.class, */modules = LoginActivityModule.class)
public interface LoginActivityComponent {
    void inject(LoginActivity loginActivity);
}
