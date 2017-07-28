package com.ane.expresstokenapp.di.module;

import com.ane.expresstokenapp.BuildConfig;
import com.ane.expresstokenapp.di.qualifier.FileUploadUrl;
import com.ane.expresstokenapp.di.qualifier.MainUrl;
import com.ane.expresstokenapp.net.api.FileUploadApis;
import com.ane.expresstokenapp.net.api.MainApis;
import com.ane.expresstokenapp.net.converter.CustomGsonConverterFactory;
import com.ane.expresstokenapp.utils.Constants;
import com.ane.expresstokenapp.utils.NetworkUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

@Module
public class HttpModule {

    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    OkHttpClient.Builder provideOkHttpBuilder() {
        return new OkHttpClient.Builder();
    }

    @Singleton
    @Provides
    OkHttpClient provideClient(OkHttpClient.Builder builder) {
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }
        File cacheFile = new File(Constants.PATH_CACHE);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetworkUtils.isNetworkConnected()) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (NetworkUtils.isNetworkConnected()) {
                    int maxAge = 0;
                    // 有网络时, 不缓存, 最大保存时长为0
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    // 无网络时，设置超时为3天
                    int maxStale = 60 * 60 * 24 * 3;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };

        //设置缓存
        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addInterceptor(cacheInterceptor);
//        builder.addInterceptor(new CommonInterceptor());
        builder.cache(cache);
        //设置超时
        builder.connectTimeout(20, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        return builder.build();
    }

    @Singleton
    @Provides
    @MainUrl
    Retrofit provideMainRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, MainApis.BASE_URL);
    }

    @Singleton
    @Provides
    @FileUploadUrl
    Retrofit provideUploadRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, FileUploadApis.BASE_URL);
    }

    private Retrofit createRetrofit(Retrofit.Builder builder, OkHttpClient client, String url) {
        return builder
                .baseUrl(url)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(CustomGsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    MainApis provideMainApis(@MainUrl Retrofit retrofit) {
        return retrofit.create(MainApis.class);
    }

    @Singleton
    @Provides
    FileUploadApis provideFileUploadApis(@FileUploadUrl Retrofit retrofit) {
        return retrofit.create(FileUploadApis.class);
    }
}
