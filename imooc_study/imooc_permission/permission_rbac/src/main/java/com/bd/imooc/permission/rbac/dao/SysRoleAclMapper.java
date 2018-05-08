package com.bd.imooc.permission.rbac.dao;

import com.bd.imooc.permission.rbac.model.SysRoleAcl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleAclMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRoleAcl record);

    int insertSelective(SysRoleAcl record);

    SysRoleAcl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRoleAcl record);

    int updateByPrimaryKey(SysRoleAcl record);

    List<Integer> getAclIdListByRoleIdList(@Param("roleIdList") List<Integer> roleIdList);

    void deleteByRoleId(@Param("roleId") int roleId);

    /**
     * 注解@Param指定参数名字，在Mapper.xml的collection=中引用，否则会报not found错误
     */
    void batchInsert(@Param("roleAclList") List<SysRoleAcl> roleAclList);

    List<Integer> getRoleIdListByAclId(@Param("aclId") int aclId);
}