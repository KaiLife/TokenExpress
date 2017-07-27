package com.ane.expresstokenapp.modules.login;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/7/27 0027.
 */

public class Test {

    private User user;

    @Inject
    public Test(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
