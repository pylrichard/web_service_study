package com.bd.imooc.security.rbac.web.controller;

import com.bd.imooc.security.rbac.dto.RoleInfo;
import com.bd.imooc.security.rbac.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 创建角色
     */
    @PostMapping
    public RoleInfo create(@RequestBody RoleInfo roleInfo) {
        return roleService.create(roleInfo);
    }

    /**
     * 修改角色信息
     */
    @PutMapping("/{id}")
    public RoleInfo update(@RequestBody RoleInfo roleInfo) {
        return roleService.update(roleInfo);
    }

    /**
     * 删除角色
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        roleService.delete(id);
    }

    /**
     * 获取角色详情
     */
    @GetMapping("/{id}")
    public RoleInfo getInfo(@PathVariable Long id) {
        return roleService.getInfo(id);
    }

    /**
     * 获取所有角色
     */
    @GetMapping
    public List<RoleInfo> findAll() {
        return roleService.findAll();
    }

    /**
     * 获取角色的所有资源
     */
    @GetMapping("/{id}/resource")
    public String[] getRoleResources(@PathVariable Long id) {
        return roleService.getRoleResources(id);
    }

    /**
     * 创建用户的资源
     */
    @PostMapping("/{id}/resource")
    public void createRoleResource(@PathVariable Long id, String ids) {
        roleService.setRoleResources(id, ids);
    }
}
