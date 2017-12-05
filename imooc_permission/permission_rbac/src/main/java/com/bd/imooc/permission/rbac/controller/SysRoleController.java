package com.bd.imooc.permission.rbac.controller;

import com.bd.imooc.permission.rbac.common.JsonData;
import com.bd.imooc.permission.rbac.model.SysUser;
import com.bd.imooc.permission.rbac.param.RoleParam;
import com.bd.imooc.permission.rbac.service.SysRoleService;
import com.bd.imooc.permission.rbac.service.SysTreeService;
import com.bd.imooc.permission.rbac.service.SysUserService;
import com.bd.imooc.permission.rbac.util.StringUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sys/role")
public class SysRoleController {
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysTreeService sysTreeService;
    @Resource
    private SysRoleAclService sysRoleAclService;
    @Resource
    private SysRoleUserService sysRoleUserService;
    @Resource
    private SysUserService sysUserService;

    @GetMapping("role.page")
    public ModelAndView page() {
        return new ModelAndView("role");
    }

    @GetMapping("/save.json")
    public JsonData saveRole(RoleParam param) {
        sysRoleService.save(param);

        return JsonData.success();
    }

    @GetMapping("/update.json")
    public JsonData updateRole(RoleParam param) {
        sysRoleService.update(param);

        return JsonData.success();
    }

    @GetMapping("/list.json")
    public JsonData list() {
        return JsonData.success(sysRoleService.getAll());
    }

    @GetMapping("/roleTree.json")
    public JsonData roleTree(@RequestParam("roleId") int roleId) {
        return JsonData.success(sysTreeService.roleTree(roleId));
    }

    @GetMapping("/changeAcls.json")
    public JsonData changeAcls(@RequestParam("roleId") int roleId,
                               @RequestParam(value = "aclIds", required = false, defaultValue = "") String aclIds) {
        List<Integer> aclIdList = StringUtil.splitToListInt(aclIds);
        sysRoleAclService.changeRoleAcls(roleId, aclIdList);
        return JsonData.success();
    }

    @GetMapping("/changeUsers.json")
    public JsonData changeUsers(@RequestParam("roleId") int roleId,
                                @RequestParam(value = "userIds", required = false, defaultValue = "") String userIds) {
        List<Integer> userIdList = StringUtil.splitToListInt(userIds);
        sysRoleUserService.changeRoleUsers(roleId, userIdList);
        return JsonData.success();
    }

    @GetMapping("/users.json")
    public JsonData users(@RequestParam("roleId") int roleId) {
        List<SysUser> selectedUserList = sysRoleUserService.getListByRoleId(roleId);
        List<SysUser> allUserList = sysUserService.getAll();
        List<SysUser> unselectedUserList = Lists.newArrayList();

        Set<Integer> selectedUserIdSet = selectedUserList.stream().map(sysUser -> sysUser.getId()).collect(Collectors.toSet());
        for (SysUser sysUser : allUserList) {
            if (sysUser.getStatus() == 1 && !selectedUserIdSet.contains(sysUser.getId())) {
                unselectedUserList.add(sysUser);
            }
        }
        //selectedUserList = selectedUserList.stream().filter(sysUser -> sysUser.getStatus() != 1).collect(Collectors.toList());
        Map<String, List<SysUser>> map = Maps.newHashMap();
        map.put("selected", selectedUserList);
        map.put("unselected", unselectedUserList);
        return JsonData.success(map);
    }
}
