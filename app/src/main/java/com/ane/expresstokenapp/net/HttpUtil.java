package com.ane.expresstokenapp.net;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.ane.expresstokenapp.App;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by bvb on 2017/3/16.
 */

public class HttpUtil {
    public static MultipartBody.Part filesToMultipartBodyPart(File file) {
        String mime = getMimeType(App.getApp(), file);
        if (TextUtils.isEmpty(mime)) {
            mime = "image/*";
            Log.e("MimeType : ", mime);
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse(mime), file);

        MultipartBody.Part part = MultipartBody.Part.createFormData("image_file", file.getName(), requestBody);
        return part;
    }

    public static List<MultipartBody.Part> filesToMultipartBodyParts(List<File> files) {
        List<MultipartBody.Part> parts = new ArrayList<>(files.size());
        for (File file : files) {
            RequestBody requestBody = RequestBody.create(MediaType.parse(getMimeType(App.getApp(), file)), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            parts.add(part);
        }
        return parts;
    }

    public final static String getMimeType(Context context, File file) {
        Uri uri = Uri.fromFile(file);
        ContentResolver cR = context.getContentResolver();
        return cR.getType(uri);

    }

    /**
     * @param code 10000 需要登录
     *             10001 押金不足
     *             10002 用户未认证
     *             10003 需要升级
     * @return
     */
    public final static boolean needShowNote(String code) {
        return false;
    }
}
