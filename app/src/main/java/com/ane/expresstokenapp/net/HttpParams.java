package com.ane.expresstokenapp.net;

import java.util.Arrays;

/**
 * Created by Administrator on 2017/7/28 0028.
 */

public class HttpParams {

    /**
     * 请求参数
     */
    private String params;

    /**
     * 扫描数据参数
     */
    private byte[] scanParams;

    /**
     * 时间
     */
    private long timestamp;

    /**
     * 摘要
     */
    private String digest;

    /**
     * 请求的服务类型
     */
    private String type;


    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public byte[] getScanParams() {
        return scanParams;
    }

    public void setScanParams(byte[] scanParams) {
        this.scanParams = scanParams;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "HttpParams{" +
                "params='" + params + '\'' +
                ", scanParams=" + Arrays.toString(scanParams) +
                ", timestamp=" + timestamp +
                ", digest='" + digest + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
