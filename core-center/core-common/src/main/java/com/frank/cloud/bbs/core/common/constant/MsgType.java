package com.frank.cloud.bbs.core.common.constant;

public enum MsgType {

    ERROR(0),
    COMMENT(1),
    REPLY(2),
    BOARD(3),
    LETTER(4),
    MODERATOR(5),
    PERSON(6),
    ADMIN(7);

    private Integer code;

    public Integer getCode() {
        return code;
    }

    MsgType(Integer code) {
        this.code = code;
    }
}
