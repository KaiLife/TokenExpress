package com.ane.expresstokenapp.modules.login;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/7/27 0027.
 */
@Module
public class LoginActivityModule {

    @Provides
    public User provideUserModel() {
        return new User("jack", 18);
    }

}
