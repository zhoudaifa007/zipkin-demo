package com.frank.cloud.message.common.model;

/**
 * Created by  Frank on 2017-12-18.
 */
public class ServerResponse<T>  {
    private Integer code = 200;
    private String msg = "ok";
    private T result;

    public ServerResponse(T result) {
        this.result = result;
    }

    public ServerResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ServerResponse(Integer errorCode, String msg, T result) {
        this.code = errorCode;
        this.msg = msg;
        this.result = result;
    }

    public static ServerResponse successObject() {
        return new ServerResponse(new Object());
    }

    public static ServerResponse successArray() {
        return new ServerResponse(new Object[0]);
    }

    public static <T> ServerResponse<T> successWithData(T result) {
        return new ServerResponse(result);
    }

    public static ServerResponse failureWithCodeMsg(Integer code, String msg) {
        return new ServerResponse(code, msg);
    }

    public static <T> ServerResponse<T> failureWithCodeMsg(Integer errorCode, String msg, T result) {
        return new ServerResponse(errorCode, msg, result);
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

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}