package com.bd.imooc.permission.rbac.dto;

import com.bd.imooc.permission.rbac.model.SysAclModule;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Getter
@Setter
@ToString
public class AclModuleLevelDto extends SysAclModule {
    /**
     * 权限子模块
     */
    private List<AclModuleLevelDto> aclModuleList = Lists.newArrayList();
    /**
     * 权限点
     */
    private List<AclDto> aclList = Lists.newArrayList();

    public static AclModuleLevelDto adapt(SysAclModule aclModule) {
        AclModuleLevelDto dto = new AclModuleLevelDto();
        BeanUtils.copyProperties(aclModule, dto);

        return dto;
    }
}
