package com.ane.expresstokenapp.net;

public class ApiException extends Exception {
    private String resultCode;

    public ApiException(String error, String resultCode) {
        super(error);
        this.resultCode = resultCode;
    }

    public ApiException(String error) {
        super(error);
        this.resultCode = "";
    }

    public String getResultCode() {
        return resultCode;
    }
}
