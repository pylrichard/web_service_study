package com.bd.imooc.permission.rbac.service;

import com.bd.imooc.permission.rbac.beans.CacheKeyConstants;
import com.bd.imooc.permission.rbac.common.RequestHolder;
import com.bd.imooc.permission.rbac.dao.SysAclMapper;
import com.bd.imooc.permission.rbac.dao.SysRoleAclMapper;
import com.bd.imooc.permission.rbac.dao.SysRoleUserMapper;
import com.bd.imooc.permission.rbac.model.SysAcl;
import com.bd.imooc.permission.rbac.model.SysUser;
import com.bd.imooc.permission.rbac.util.JsonMapper;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SysCoreService {
    @Resource
    private SysAclMapper sysAclMapper;
    @Resource
    private SysRoleUserMapper sysRoleUserMapper;
    @Resource
    private SysRoleAclMapper sysRoleAclMapper;
    @Resource
    private SysCacheService sysCacheService;

    /**
     * 获取当前用户拥有的权限
     */
    public List<SysAcl> getCurrentUserAclList() {
        int userId = RequestHolder.getCurrentUser().getId();

        return getUserAclList(userId);
    }

    /**
     * 获取角色拥有的权限
     */
    public List<SysAcl> getRoleAclList(int roleId) {
        List<Integer> aclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(Lists.<Integer>newArrayList(roleId));
        if (CollectionUtils.isEmpty(aclIdList)) {
            return Lists.newArrayList();
        }

        return sysAclMapper.getByIdList(aclIdList);
    }

    /**
     * 获取用户拥有的权限
     */
    public List<SysAcl> getUserAclList(int userId) {
        if (isSuperAdmin()) {
            return sysAclMapper.getAll();
        }
        //获取用户拥有的角色列表
        List<Integer> userRoleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(userRoleIdList)) {
            return Lists.newArrayList();
        }
        //根据角色列表获取权限列表
        List<Integer> userAclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(userRoleIdList);
        if (CollectionUtils.isEmpty(userAclIdList)) {
            return Lists.newArrayList();
        }

        //根据权限列表获取用户拥有的权限
        return sysAclMapper.getByIdList(userAclIdList);
    }

    public boolean isSuperAdmin() {
        //超级管理员规则可以从配置文件获取，可以指定某个用户，可以指定某个角色
        SysUser sysUser = RequestHolder.getCurrentUser();
        if (sysUser.getMail().contains("admin")) {
            return true;
        }

        return false;
    }

    /**
     * 判断是否拥有访问url的权限
     */
    public boolean hasUrlAcl(String url) {
        if (isSuperAdmin()) {
            return true;
        }
        List<SysAcl> aclList = sysAclMapper.getByUrl(url);
        if (CollectionUtils.isEmpty(aclList)) {
            return true;
        }
        List<SysAcl> userAclList = getCurrentUserAclListFromCache();
        Set<Integer> userAclIdSet = userAclList.stream().map(acl -> acl.getId()).collect(Collectors.toSet());
        //是否拥有有效权限点
        boolean hasValidAcl = false;
        /*
            只要有一个权限点有权限，就认为有访问权限
         */
        for (SysAcl acl : aclList) {
            if (acl == null || acl.getStatus() != 1) {
                //权限点无效
                continue;
            }
            hasValidAcl = true;
            //判断用户拥有的权限点是否包含要访问的有效权限点
            if (userAclIdSet.contains(acl.getId())) {
                return true;
            }
        }
        //没有有效的权限点
        if (!hasValidAcl) {
            return true;
        }

        //用户没有要访问的有效权限点的访问权限
        return false;
    }

    public List<SysAcl> getCurrentUserAclListFromCache() {
        int userId = RequestHolder.getCurrentUser().getId();
        String cacheValue = sysCacheService.getFromCache(CacheKeyConstants.USER_ACLS, String.valueOf(userId));
        if (StringUtils.isBlank(cacheValue)) {
            List<SysAcl> aclList = getCurrentUserAclList();
            if (CollectionUtils.isNotEmpty(aclList)) {
                sysCacheService.saveCache(JsonMapper.obj2String(aclList), 600,
                        CacheKeyConstants.USER_ACLS, String.valueOf(userId));
            }
            return aclList;
        }

        return JsonMapper.string2Obj(cacheValue, new TypeReference<List<SysAcl>>() {
        });
    }
}
