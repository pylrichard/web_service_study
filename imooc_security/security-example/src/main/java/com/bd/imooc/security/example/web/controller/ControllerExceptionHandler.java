package com.bd.imooc.security.example.web.controller;

import com.bd.imooc.security.example.exception.UserNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义异常处理
 */
@ControllerAdvice
public class ControllerExceptionHandler {
    //处理UserNotExistException
    @ExceptionHandler(UserNotExistException.class)
    //将返回响应转换为JSON
    @ResponseBody
    //返回状态码为500
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleUserNotExistException(UserNotExistException ex) {
        Map<String, Object> result = new HashMap<>(16);
        result.put("id", ex.getId());
        result.put("message", ex.getMessage());

        return result;
    }
}
