package com.bd.roncoo.spring.boot.primer.exception;

import com.bd.roncoo.spring.boot.primer.enums.ResultEnum;

/**
 * Spring对RuntimeException进行事务回滚，Exception不会回滚
 */
public class RoncooException extends RuntimeException {
    private Integer code;

    public RoncooException(ResultEnum resultEnum) {
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
