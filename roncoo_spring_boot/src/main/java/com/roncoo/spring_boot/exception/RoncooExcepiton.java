package com.roncoo.spring_boot.exception;

import com.roncoo.spring_boot.enums.ResultEnum;

/**
 * Spring对RuntimeException进行事务回滚，Exception不会回滚
 */
public class RoncooExcepiton extends RuntimeException {
    private Integer code;

    public RoncooExcepiton(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
