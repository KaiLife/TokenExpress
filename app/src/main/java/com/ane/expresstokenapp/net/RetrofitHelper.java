package com.ane.expresstokenapp.net;

import com.ane.expresstokenapp.net.api.FileUploadApis;
import com.ane.expresstokenapp.net.api.MainApis;

import io.reactivex.Observable;

public class RetrofitHelper {
    MainApis mainApis;
    FileUploadApis fileUploadApis;

    public RetrofitHelper(MainApis mainApis, FileUploadApis fileUploadApis) {
        this.mainApis = mainApis;
        this.fileUploadApis = fileUploadApis;
    }

    public Observable<HttpResult> commonPostRequest(HttpParams httpParams) {
        return mainApis.commonPostRequest(httpParams);
    }
}
