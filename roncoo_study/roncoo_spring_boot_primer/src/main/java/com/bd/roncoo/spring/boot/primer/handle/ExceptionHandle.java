package com.bd.roncoo.spring.boot.primer.handle;

import com.bd.roncoo.spring.boot.primer.bean.Result;
import com.bd.roncoo.spring.boot.primer.enums.ResultEnum;
import com.bd.roncoo.spring.boot.primer.exception.RoncooExcepiton;
import com.bd.roncoo.spring.boot.primer.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理
 */
@ControllerAdvice
public class ExceptionHandle {
    protected static final Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e) {
        if (e instanceof RoncooExcepiton) {
            RoncooExcepiton roncooExcepiton = (RoncooExcepiton) e;
            return ResultUtil.fail(roncooExcepiton.getCode(), roncooExcepiton.getMessage());
        } else {
            logger.error("system exception {}", e);
            return ResultUtil.fail(ResultEnum.UNKNOWN_ERROR.getCode(), e.getMessage());
        }
    }
}
