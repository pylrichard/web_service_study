package com.bd.imooc.security.rbac.repository.spec;

import com.bd.imooc.security.rbac.domain.Admin;
import com.bd.imooc.security.rbac.dto.AdminCondition;
import com.bd.imooc.security.rbac.repository.support.ImoocSpecification;
import com.bd.imooc.security.rbac.repository.support.QueryWrapper;

public class AdminSpec extends ImoocSpecification<Admin, AdminCondition> {
    public AdminSpec(AdminCondition condition) {
        super(condition);
    }

    @Override
    protected void addCondition(QueryWrapper<Admin> queryWrapper) {
        addLikeCondition(queryWrapper, "username");
    }
}
