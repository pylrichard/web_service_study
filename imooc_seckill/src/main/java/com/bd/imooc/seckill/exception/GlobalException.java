package com.bd.imooc.seckill.exception;

import com.bd.imooc.seckill.result.CodeMsg;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private CodeMsg codeMsg;

    public GlobalException(CodeMsg codeMsg) {
        super(codeMsg.toString());
        this.codeMsg = codeMsg;
    }
}
