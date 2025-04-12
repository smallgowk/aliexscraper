package com.phanduy.aliexscrap.api;

public class ApiResponse<T> {
    public int status;

    public String message;

    public String error;

    private T data; // Có thể đổi thành kiểu dữ liệu phù hợp

    public boolean isSuccess() {
        return error == null;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
