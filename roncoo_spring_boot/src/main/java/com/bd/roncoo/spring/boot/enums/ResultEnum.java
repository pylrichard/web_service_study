package com.bd.roncoo.spring.boot.enums;

public enum ResultEnum {
    UNKNOWN_ERROR(-1, "未知错误"),
    SUCCESS(0, "成功"),
    PARAM_NOTBLANK(100, "参数不能为空"),
    SCHOOL(101, "你是中学生"),
    UNIVERSITY(102, "你是大学生")
    ;

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
