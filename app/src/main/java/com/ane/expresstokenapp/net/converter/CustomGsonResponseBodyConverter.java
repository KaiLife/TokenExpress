package com.ane.expresstokenapp.net.converter;

import com.ane.expresstokenapp.net.HttpResult;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.stream.JsonReader;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import static okhttp3.internal.Util.UTF_8;

final class CustomGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    CustomGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        MediaType contentType = value.contentType();
        Charset charset = contentType != null ? contentType.charset(UTF_8) : UTF_8;
        InputStream inputStream = new ByteArrayInputStream(response.getBytes());
        Reader reader = new InputStreamReader(inputStream, charset);
        JsonReader jsonReader = gson.newJsonReader(reader);

        try {
            T t = adapter.read(jsonReader);
            try {
                HttpResult httpResult = (HttpResult) t;
                Object data = httpResult.getResultInfo();
                if (data instanceof List) {
                    List list = (List) data;
                    if (list.size() > 0 && list.get(0) instanceof LinkedTreeMap) {
                        JSONObject jsonObject = new JSONObject(response);
                        String string = jsonObject.getString("resultInfo");
                        httpResult.setResultInfo(string);
                        t = (T) httpResult;
                    }
                } else if (data instanceof LinkedTreeMap) {
                    JSONObject jsonObject = new JSONObject(response);
                    String string = jsonObject.getString("resultInfo");
                    httpResult.setResultInfo(string);
                    t = (T) httpResult;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return t;
        } finally {
            value.close();
        }
    }
}