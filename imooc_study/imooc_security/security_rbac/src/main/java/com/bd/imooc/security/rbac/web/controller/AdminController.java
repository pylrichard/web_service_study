package com.bd.imooc.security.rbac.web.controller;

import com.bd.imooc.security.rbac.dto.AdminCondition;
import com.bd.imooc.security.rbac.dto.AdminInfo;
import com.bd.imooc.security.rbac.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    /**
     * 获取当前登录的管理员信息
     */
    @GetMapping("/me")
    public AdminInfo me(@AuthenticationPrincipal UserDetails user) {
        AdminInfo info = new AdminInfo();
        info.setUsername(user.getUsername());

        return info;
    }

    /**
     * 创建管理员
     */
    @PostMapping
    public AdminInfo create(@RequestBody AdminInfo adminInfo) {
        return adminService.create(adminInfo);
    }

    /**
     * 修改管理员信息
     */
    @PutMapping("/{id}")
    public AdminInfo update(@RequestBody AdminInfo adminInfo) {
        return adminService.update(adminInfo);
    }

    /**
     * 删除管理员
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        adminService.delete(id);
    }

    /**
     * 获取管理员详情
     */
    @GetMapping("/{id}")
    public AdminInfo getInfo(@PathVariable Long id) {
        return adminService.getInfo(id);
    }

    /**
     * 分页查询管理员
     */
    @GetMapping
    public Page<AdminInfo> query(AdminCondition condition, Pageable pageable) {
        return adminService.query(condition, pageable);
    }
}
