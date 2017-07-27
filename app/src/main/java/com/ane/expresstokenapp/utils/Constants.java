package com.ane.expresstokenapp.utils;

import com.ane.expresstokenapp.App;

import java.io.File;

/**
 * Created by Administrator on 2017/7/26 0026.
 */

public class Constants {

    public static final String PATH_DATA = App.getApp().getCacheDir().getAbsolutePath() + File.separator + "data";
    public static final String PATH_CACHE = PATH_DATA + "/networkCache";

    public static String SOCKET_TIMEOUT_EXCEPTION = "";
    public static String CONNECT_EXCEPTION = "";
    public static String UNKNOWN_HOST_EXCEPTION = "";
    public static String NULL_POINT_EREXCEPTION = "";
    public static String HTTP_EXCEPTION = "";
    public static String OTHER_EXCEPTION = "";

    //加密字段
    public static final String NET_ENCRYPT_KEY = "ane";
    public static final String NET_ENCRYPT_SECRET = "123456";
}
