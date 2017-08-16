package com.ane.expresstokenapp.modules.splash;

import android.content.Intent;
import android.os.Bundle;

import com.ane.expresstokenapp.base.BaseSimpleActivity;
import com.ane.expresstokenapp.modules.login.LoginActivity;


public class SplashActivity extends BaseSimpleActivity {

    @Override
    protected int getContentViewId() {
        return 0;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        gotoLogin();
    }

    public void gotoLogin() {
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        finish();
    }
}
