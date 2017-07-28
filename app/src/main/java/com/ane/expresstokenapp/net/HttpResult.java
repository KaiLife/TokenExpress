package com.ane.expresstokenapp.net;

public class HttpResult<T> {

    private boolean result;
    private String reason;
    private String resultCode;

    private T resultInfo;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public T getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(T resultInfo) {
        this.resultInfo = resultInfo;
    }
}
