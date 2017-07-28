package com.ane.expresstokenapp.net.api;


import com.ane.expresstokenapp.net.HttpParams;
import com.ane.expresstokenapp.net.HttpResult;
import com.ane.expresstokenapp.net.HttpUrlManager;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MainApis {
    String BASE_URL = HttpUrlManager.BASE_URL;

    @POST(HttpUrlManager.BASE_PATH)
    Observable<HttpResult> commonPostRequest(@Body HttpParams httpParams);
}
