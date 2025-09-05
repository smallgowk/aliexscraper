package com.phanduy.aliexscrap.model.response;

public class CheckInfoResponse {

    public static final int SUCCESS = 1;

    //Authen
    public static final int AUTHEN_FAIL = -401;

    public static final int FAILURE = -1;
    public static final int FAILURE_ENCRYPT = -2;
    public static final int FAILURE_NO_STORE_INFO = -3;
    public static final int FAILURE_USER_EXISTED = -4;
    public static final int FAILURE_EMAIL_EXISTED = -5;

    public static final int VERSION_INVALID = -7;
    public static final int VERSION_OLD = -8;
    public static final int SERIAL_INVALID = -9;
    public static final int TIME_LIMIT = -10;
    public static final int PRODUCT_LIMIT = -11;
    public static final int PRODUCT_LIMIT_IN_DAY = -12;
    public static final int HOST_EXCEPTION = -100;

    private int resultCode;
    private String message;
    private boolean latest;
    private String latestVersion;
    private int maxThreads;

    private int remainRequest;

    public int getResultCode() {
        return resultCode;
    }

    public String getMessage() {
        return message;
    }

    public boolean isLatest() {
        return latest;
    }

    public String getLatestVersion() {
        return latestVersion;
    }

    public int getMaxThreads() {
        return maxThreads;
    }

    public boolean isSuccess() {
        return resultCode == 0 || resultCode == SUCCESS;
    }

    public int getRemainRequest() {
        return remainRequest;
    }

    public void setRemainRequest(int remainRequest) {
        this.remainRequest = remainRequest;
    }
}
