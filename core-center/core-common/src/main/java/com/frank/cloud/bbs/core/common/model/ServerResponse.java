package com.frank.cloud.bbs.core.common.model;

/**
 * Created by  Frank on 2017-12-18.
 */
public class ServerResponse<T>  {
    private Integer code = 200;
    private String msg = "ok";
    private T data;

    public ServerResponse(T result) {
        this.data = result;
    }

    public ServerResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ServerResponse(Integer errorCode, String msg, T data) {
        this.code = errorCode;
        this.msg = msg;
        this.data = data;
    }

    public static ServerResponse successObject() {
        return new ServerResponse(new Object());
    }

    public static ServerResponse successArray() {
        return new ServerResponse(new Object[0]);
    }

    public static <T> ServerResponse<T> successWithData(T data) {
        return new ServerResponse(data);
    }

    public static ServerResponse failureWithCodeMsg(Integer code, String msg) {
        return new ServerResponse(code, msg);
    }

    public static <T> ServerResponse<T> failureWithCodeMsg(Integer errorCode, String msg, T data) {
        return new ServerResponse(errorCode, msg, data);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }
}