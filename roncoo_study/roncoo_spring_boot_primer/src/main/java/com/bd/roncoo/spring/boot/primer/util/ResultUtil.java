package com.bd.roncoo.spring.boot.primer.util;

import com.bd.roncoo.spring.boot.primer.bean.Result;
import com.bd.roncoo.spring.boot.primer.enums.ResultEnum;

public class ResultUtil {
    public static Result success(Object object) {
        Result result = new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(object);

        return result;
    }

    public static Result success() {
        return success(null);
    }

    public static Result fail(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);

        return result;
    }
}
