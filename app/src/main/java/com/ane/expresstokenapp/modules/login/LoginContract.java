package com.ane.expresstokenapp.modules.login;

import com.ane.expresstokenapp.base.BasePresenter;
import com.ane.expresstokenapp.base.BaseView;

/**
 * Created by Administrator on 2017/8/10 0010.
 */

public interface LoginContract {

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<View> {
        void login(android.view.View v);
    }
}
