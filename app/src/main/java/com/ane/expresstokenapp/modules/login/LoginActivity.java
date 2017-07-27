package com.ane.expresstokenapp.modules.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ane.expresstokenapp.R;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity {

    @Inject
    Test test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
