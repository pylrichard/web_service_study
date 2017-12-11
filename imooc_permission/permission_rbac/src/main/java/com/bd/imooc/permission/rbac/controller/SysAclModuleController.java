package com.bd.imooc.permission.rbac.controller;

import com.bd.imooc.permission.rbac.common.JsonData;
import com.bd.imooc.permission.rbac.param.AclModuleParam;
import com.bd.imooc.permission.rbac.service.SysAclModuleService;
import com.bd.imooc.permission.rbac.service.SysTreeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
@RequestMapping("/sys/aclModule")
public class SysAclModuleController {
    @Resource
    private SysAclModuleService sysAclModuleService;
    @Resource
    private SysTreeService sysTreeService;

    @GetMapping("/acl.page")
    public ModelAndView page() {
        return new ModelAndView("acl");
    }

    @GetMapping("/save.json")
    public JsonData saveAclModule(AclModuleParam param) {
        sysAclModuleService.saveAclModule(param);

        return JsonData.success();
    }

    @GetMapping("/update.json")
    public JsonData updateAclModule(AclModuleParam param) {
        sysAclModuleService.updateAclModule(param);

        return JsonData.success();
    }

    @GetMapping("/tree.json")
    public JsonData createAclModuleTree() {
        return JsonData.success(sysTreeService.createAclModuleTree());
    }

    @GetMapping("/delete.json")
    public JsonData deleteAclModule(@RequestParam("id") int id) {
        sysAclModuleService.deleteAclModule(id);

        return JsonData.success();
    }
}
