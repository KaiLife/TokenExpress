package com.ane.expresstokenapp.net;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.ane.expresstokenapp.App;
import com.ane.expresstokenapp.utils.Base64;
import com.ane.expresstokenapp.utils.CompressUtil;
import com.ane.expresstokenapp.utils.Constants;
import com.ane.expresstokenapp.utils.MD5Util;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class HttpUtil {

    /**
     * 加密请求数据
     * @param urlType 请求服务类型
     * @param postParam json数据格式  请求参数
     * @param bCompress 是否压缩
     * @return
     */
    public static HttpParams encodeData(String urlType, String postParam, boolean bCompress) {
        HttpParams entity = new HttpParams();
        if (bCompress) {
            byte[] data = new byte[0];
            try {
                data = CompressUtil.compress(postParam);
            } catch (Exception e) {
                e.printStackTrace();
            }
            entity.setScanParams(data);
            StringBuffer sb = new StringBuffer();
            for (byte b : data) {
                sb.append(b);
            }
            String digest = "";
            try {
                digest = Base64.encode(MD5Util.encryption(sb.toString() + Constants.NET_ENCRYPT_KEY
                        + Constants.NET_ENCRYPT_SECRET).getBytes("UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            entity.setDigest(digest);
            entity.setType(urlType);
            entity.setTimestamp(new Date().getTime());
        } else {
            entity.setParams(postParam);
            String digest = "";
            try {
                digest = Base64.encode(MD5Util.encryption(postParam + Constants.NET_ENCRYPT_KEY
                        + Constants.NET_ENCRYPT_SECRET).getBytes("UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            entity.setDigest(digest);
            entity.setType(urlType);
            entity.setTimestamp(new Date().getTime());
        }
        return entity;
    }

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
