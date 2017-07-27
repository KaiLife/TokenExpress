package com.ane.expresstokenapp.modules.main;

import com.ane.expresstokenapp.base.BasePresenter;
import com.ane.expresstokenapp.base.BaseView;

/**
 * Created by Administrator on 2017/7/26 0026.
 */

public interface MainContract {

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<View> {
        void siteListService();
    }
}
