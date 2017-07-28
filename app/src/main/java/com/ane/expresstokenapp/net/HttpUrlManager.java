package com.ane.expresstokenapp.net;


import com.ane.expresstokenapp.BuildConfig;

public class HttpUrlManager {

    private HttpUrlManager() {
    }

    public static final String BASE_URL = BuildConfig.URL;//服务器地址
    public static final String BASE_PATH = BuildConfig.PATH;//公共路径

    /**
     * 根据部门编码获取名称
     */
    public static final String SiteServiceImpl = "siteServiceImpl";
}