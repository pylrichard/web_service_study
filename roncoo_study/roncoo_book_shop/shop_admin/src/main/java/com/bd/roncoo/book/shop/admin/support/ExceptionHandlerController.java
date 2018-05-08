package com.bd.roncoo.book.shop.admin.support;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 发生异常后，由ExceptionHandlerController处理异常，返回json格式的数据，而不是转到404/500.html
 * Spring MVC采用继承HandlerExceptionResolver
 * Dubbo使用com.alibaba.dubbo.rpc.filter.ExceptionFilter
 */
@RestControllerAdvice
public class ExceptionHandlerController {
    //在Controller层统一处理各种异常，根据不同异常返回不同的消息给前端，由前端决定是切换到相应的页面还是进行其它处理
    @ExceptionHandler(RuntimeException.class)
    //@ResponseStatus默认是500
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public Map<String, Object> handleRuntimeExcepition(RuntimeException exception) {
        Map<String, Object> result = new HashMap<>(16);
        result.put("result", "fail");
        result.put("errMsg", exception.getMessage());

        return result;
    }
}
