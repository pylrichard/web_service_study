package com.bd.imooc.permission.rbac.controller;

import com.bd.imooc.permission.rbac.beans.PageQuery;
import com.bd.imooc.permission.rbac.beans.PageResult;
import com.bd.imooc.permission.rbac.common.JsonData;
import com.bd.imooc.permission.rbac.model.SysUser;
import com.bd.imooc.permission.rbac.param.UserParam;
import com.bd.imooc.permission.rbac.service.SysRoleService;
import com.bd.imooc.permission.rbac.service.SysTreeService;
import com.bd.imooc.permission.rbac.service.SysUserService;
import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/sys/user")
public class SysUserController {
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysTreeService sysTreeService;
    @Resource
    private SysRoleService sysRoleService;

    @GetMapping("/noAuth.page")
    public ModelAndView noAuth() {
        return new ModelAndView("noAuth");
    }

    @GetMapping("/save.json")
    public JsonData saveUser(UserParam param) {
        sysUserService.saveUser(param);

        return JsonData.success();
    }

    @GetMapping("/update.json")
    public JsonData updateUser(UserParam param) {
        sysUserService.updateUser(param);

        return JsonData.success();
    }

    @GetMapping("/page.json")
    public JsonData getPageByDeptId(@RequestParam("deptId") int deptId, PageQuery pageQuery) {
        PageResult<SysUser> result = sysUserService.getPageByDeptId(deptId, pageQuery);

        return JsonData.success(result);
    }

    /**
     * 获取用户拥有的角色和权限
     */
    @GetMapping("/acls.json")
    public JsonData getUserRoleAndAcls(@RequestParam("userId") int userId) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("acls", sysTreeService.userAclTree(userId));
        map.put("roles", sysRoleService.getRoleListByUserId(userId));

        return JsonData.success(map);
    }
}
