package com.bd.imooc.security.rbac.service;

import com.bd.imooc.security.rbac.dto.RoleInfo;

import java.util.List;

/**
 * 角色服务
 */
public interface RoleService {
    /**
     * 创建角色
     */
    RoleInfo create(RoleInfo roleInfo);

    /**
     * 修改角色
     */
    RoleInfo update(RoleInfo roleInfo);

    /**
     * 删除角色
     */
    void delete(Long id);

    /**
     * 获取角色详细信息
     */
    RoleInfo getInfo(Long id);

    /**
     * 查询所有角色
     */
    List<RoleInfo> findAll();

    String[] getRoleResources(Long id);

    void setRoleResources(Long id, String ids);
}
