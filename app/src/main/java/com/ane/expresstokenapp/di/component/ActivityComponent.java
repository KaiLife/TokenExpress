package com.ane.expresstokenapp.di.component;

import android.app.Activity;

import com.ane.expresstokenapp.modules.main.MainActivity;

//@ActivityScope
//@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();

    void inject(MainActivity mainActivity);
}