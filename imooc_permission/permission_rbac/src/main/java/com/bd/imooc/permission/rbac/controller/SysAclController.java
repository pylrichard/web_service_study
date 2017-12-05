package com.bd.imooc.permission.rbac.controller;

import com.bd.imooc.permission.rbac.beans.PageQuery;
import com.bd.imooc.permission.rbac.common.JsonData;
import com.bd.imooc.permission.rbac.model.SysRole;
import com.bd.imooc.permission.rbac.param.AclParam;
import com.bd.imooc.permission.rbac.service.SysAclService;
import com.bd.imooc.permission.rbac.service.SysRoleService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys/acl")
@Slf4j
public class SysAclController {
    @Resource
    private SysAclService sysAclService;
    @Resource
    private SysRoleService sysRoleService;

    @GetMapping("/save.json")
    public JsonData saveAclModule(AclParam param) {
        sysAclService.save(param);

        return JsonData.success();
    }

    @GetMapping("/update.json")
    public JsonData updateAclModule(AclParam param) {
        sysAclService.update(param);

        return JsonData.success();
    }

    @GetMapping("/page.json")
    public JsonData list(@RequestParam("aclModuleId") Integer aclModuleId, PageQuery pageQuery) {
        return JsonData.success(sysAclService.getPageByAclModuleId(aclModuleId, pageQuery));
    }

    @GetMapping("acls.json")
    public JsonData acls(@RequestParam("aclId") int aclId) {
        Map<String, Object> map = Maps.newHashMap();
        List<SysRole> roleList = sysRoleService.getRoleListByAclId(aclId);
        map.put("roles", roleList);
        map.put("users", sysRoleService.getUserListByRoleList(roleList));

        return JsonData.success(map);
    }
}
