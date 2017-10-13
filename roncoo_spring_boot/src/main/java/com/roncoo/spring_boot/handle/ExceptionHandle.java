package com.roncoo.spring_boot.handle;

import com.roncoo.spring_boot.bean.Result;
import com.roncoo.spring_boot.enums.ResultEnum;
import com.roncoo.spring_boot.exception.RoncooExcepiton;
import com.roncoo.spring_boot.util.ResultUtil;
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
