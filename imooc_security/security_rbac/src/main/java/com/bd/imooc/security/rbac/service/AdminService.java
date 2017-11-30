package com.bd.imooc.security.rbac.service;

import com.bd.imooc.security.rbac.dto.AdminCondition;
import com.bd.imooc.security.rbac.dto.AdminInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 管理员服务
 */
public interface AdminService {
    /**
     * 创建管理员
     */
    AdminInfo create(AdminInfo adminInfo);

    /**
     * 修改管理员
     */
    AdminInfo update(AdminInfo adminInfo);

    /**
     * 删除管理员
     */
    void delete(Long id);

    /**
     * 获取管理员详细信息
     */
    AdminInfo getInfo(Long id);

    /**
     * 分页查询管理员
     */
    Page<AdminInfo> query(AdminCondition condition, Pageable pageable);
}
