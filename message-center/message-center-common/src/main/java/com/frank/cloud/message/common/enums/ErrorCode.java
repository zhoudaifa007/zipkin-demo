package com.frank.cloud.message.common.enums;

public enum ErrorCode {

	BAD_REQUEST(400, "bad Request"),
    AUTH_FAILED(401, "authentication failed"),
    SESSION_INVALID(402, "session id invalid"),
    DECODE_FAILED(403, "decode failed"),
    PINCODE_ERROR(404, "pincode invalid"),
    HAVE_NO_AUTH(407,"you have no authority"),
    ERROR(500, "system error"),
    NOT_FOUND_ARTICLE(501,"can not find the article"),
    SAME_NAME(502,"have the same name"),
    INVALID_PARAMETER(600, "{0} is invalid!"),
    OTHER_ERROR(700, "other error"),
    OTHER_LOGIN(801, "the account login at other place")
    ;

    private Integer code;
    private String msg;

    ErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

	public static ErrorCode getCode(Integer errorCode) {
        for (ErrorCode c : ErrorCode.values()) {
            if (errorCode == c.getCode()) {
                return c;
            }
        }
        return ERROR;
    }
}
