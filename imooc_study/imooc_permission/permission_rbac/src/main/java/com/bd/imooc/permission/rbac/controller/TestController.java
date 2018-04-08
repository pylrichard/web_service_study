package com.bd.imooc.permission.rbac.controller;

import com.bd.imooc.permission.rbac.common.ApplicationContextHelper;
import com.bd.imooc.permission.rbac.common.JsonData;
import com.bd.imooc.permission.rbac.dao.SysAclModuleMapper;
import com.bd.imooc.permission.rbac.exception.ParamException;
import com.bd.imooc.permission.rbac.model.SysAclModule;
import com.bd.imooc.permission.rbac.param.TestVo;
import com.bd.imooc.permission.rbac.util.BeanValidator;
import com.bd.imooc.permission.rbac.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
    @GetMapping("/hello.json")
    public JsonData hello() {
        log.info("hello");
//        throw new PermissionException("test exception");
        return JsonData.success("hello permission");
    }

    @GetMapping("/validate.json")
    public JsonData validate(TestVo vo) throws ParamException {
        log.info("validate");
        SysAclModuleMapper moduleMapper = ApplicationContextHelper.popBean(SysAclModuleMapper.class);
        SysAclModule module = moduleMapper.selectByPrimaryKey(1);
        log.info(JsonMapper.obj2String(module));
        BeanValidator.check(vo);

        return JsonData.success("test validate");
    }
}
