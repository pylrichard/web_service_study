package com.bd.imooc.permission.rbac.service;

import com.bd.imooc.permission.rbac.dao.SysAclMapper;
import com.bd.imooc.permission.rbac.dao.SysAclModuleMapper;
import com.bd.imooc.permission.rbac.dao.SysDeptMapper;
import com.bd.imooc.permission.rbac.dto.AclDto;
import com.bd.imooc.permission.rbac.dto.AclModuleLevelDto;
import com.bd.imooc.permission.rbac.dto.DeptLevelDto;
import com.bd.imooc.permission.rbac.model.SysAcl;
import com.bd.imooc.permission.rbac.model.SysAclModule;
import com.bd.imooc.permission.rbac.model.SysDept;
import com.bd.imooc.permission.rbac.util.LevelUtil;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SysTreeService {
    @Resource
    private SysDeptMapper sysDeptMapper;
    @Resource
    private SysAclModuleMapper sysAclModuleMapper;
    @Resource
    private SysCoreService sysCoreService;
    @Resource
    private SysAclMapper sysAclMapper;

    /**
     * 获取用户权限树形结构
     */
    public List<AclModuleLevelDto> userAclTree(int userId) {
        List<SysAcl> userAclList = sysCoreService.getUserAclList(userId);
        List<AclDto> aclDtoList = Lists.newArrayList();
        for (SysAcl acl : userAclList) {
            AclDto dto = AclDto.adapt(acl);
            dto.setHasAcl(true);
            dto.setChecked(true);
            aclDtoList.add(dto);
        }

        return transformAclListToTree(aclDtoList);
    }

    /**
     * 获取角色权限树形结构
     */
    public List<AclModuleLevelDto> createRoleAclTree(int roleId) {
        //当前用户分配的权限点
        List<SysAcl> userAclList = sysCoreService.getCurrentUserAclList();
        //当前角色分配的权限点
        List<SysAcl> roleAclList = sysCoreService.getRoleAclList(roleId);
        List<AclDto> aclDtoList = Lists.newArrayList();
        Set<Integer> userAclIdSet = userAclList.stream().map(sysAcl -> sysAcl.getId()).collect(Collectors.toSet());
        Set<Integer> roleAclIdSet = roleAclList.stream().map(sysAcl -> sysAcl.getId()).collect(Collectors.toSet());
        //当前系统所有权限点
        List<SysAcl> allAclList = sysAclMapper.getAll();
        for (SysAcl acl : allAclList) {
            AclDto dto = AclDto.adapt(acl);
            if (userAclIdSet.contains(acl.getId())) {
                dto.setHasAcl(true);
            }
            if (roleAclIdSet.contains(acl.getId())) {
                dto.setChecked(true);
            }
            aclDtoList.add(dto);
        }

        return transformAclListToTree(aclDtoList);
    }

    public List<AclModuleLevelDto> transformAclListToTree(List<AclDto> aclDtoList) {
        if (CollectionUtils.isEmpty(aclDtoList)) {
            return Lists.newArrayList();
        }
        List<AclModuleLevelDto> aclModuleLevelList = createAclModuleTree();
        Multimap<Integer, AclDto> moduleIdAclMap = ArrayListMultimap.create();
        for (AclDto acl : aclDtoList) {
            if (acl.getStatus() == 1) {
                moduleIdAclMap.put(acl.getAclModuleId(), acl);
            }
        }
        recursiveBindAclsWithOrder(aclModuleLevelList, moduleIdAclMap);

        return aclModuleLevelList;
    }

    /**
     * 绑定权限模块和权限点
     */
    public void recursiveBindAclsWithOrder(List<AclModuleLevelDto> aclModuleLevelList,
                                           Multimap<Integer, AclDto> moduleIdAclMap) {
        if (CollectionUtils.isEmpty(aclModuleLevelList)) {
            return;
        }
        for (AclModuleLevelDto dto : aclModuleLevelList) {
            List<AclDto> aclDtoList = (List<AclDto>) moduleIdAclMap.get(dto.getId());
            if (CollectionUtils.isNotEmpty(aclDtoList)) {
                Collections.sort(aclDtoList, aclSeqComparator);
                dto.setAclList(aclDtoList);
            }
            recursiveBindAclsWithOrder(dto.getAclModuleList(), moduleIdAclMap);
        }
    }

    /**
     * 创建权限模块树形结构
     */
    public List<AclModuleLevelDto> createAclModuleTree() {
        List<SysAclModule> aclModuleList = sysAclModuleMapper.getAllAclModule();
        List<AclModuleLevelDto> dtoList = Lists.newArrayList();
        for (SysAclModule aclModule : aclModuleList) {
            dtoList.add(AclModuleLevelDto.adapt(aclModule));
        }

        return transformAclModuleListToTree(dtoList);
    }

    public List<AclModuleLevelDto> transformAclModuleListToTree(List<AclModuleLevelDto> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) {
            return Lists.newArrayList();
        }
        Multimap<String, AclModuleLevelDto> levelAclModuleMap = ArrayListMultimap.create();
        List<AclModuleLevelDto> rootList = Lists.newArrayList();
        for (AclModuleLevelDto dto : dtoList) {
            levelAclModuleMap.put(dto.getLevel(), dto);
            if (LevelUtil.ROOT.equals(dto.getLevel())) {
                rootList.add(dto);
            }
        }
        Collections.sort(rootList, aclModuleSeqComparator);
        recursiveCreateAclModuleTree(rootList, LevelUtil.ROOT, levelAclModuleMap);

        return rootList;
    }

    public void recursiveCreateAclModuleTree(List<AclModuleLevelDto> dtoList, String level,
                                             Multimap<String, AclModuleLevelDto> levelAclModuleMap) {
        for (int i = 0; i < dtoList.size(); i++) {
            AclModuleLevelDto dto = dtoList.get(i);
            String nextLevel = LevelUtil.calculateLevel(level, dto.getId());
            List<AclModuleLevelDto> aclModuleList = (List<AclModuleLevelDto>) levelAclModuleMap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(aclModuleList)) {
                Collections.sort(aclModuleList, aclModuleSeqComparator);
                dto.setAclModuleList(aclModuleList);
                recursiveCreateAclModuleTree(aclModuleList, nextLevel, levelAclModuleMap);
            }
        }
    }

    /**
     * 创建部门树形结构
     */
    public List<DeptLevelDto> createDeptTree() {
        List<SysDept> deptList = sysDeptMapper.getAllDept();
        List<DeptLevelDto> dtoList = Lists.newArrayList();
        for (SysDept dept : deptList) {
            DeptLevelDto dto = DeptLevelDto.adapt(dept);
            dtoList.add(dto);
        }

        return transformDeptListToTree(dtoList);
    }

    /**
     * 转换部门列表为树形结构
     */
    private List<DeptLevelDto> transformDeptListToTree(List<DeptLevelDto> deptLevelList) {
        if (CollectionUtils.isEmpty(deptLevelList)) {
            return Lists.newArrayList();
        }
        /*
            key为level，value为[dept1, dept2, ...]
            格式类似于Map<String, List<Object>>
         */
        Multimap<String, DeptLevelDto> levelDeptMap = ArrayListMultimap.create();
        List<DeptLevelDto> rootList = Lists.newArrayList();
        for (DeptLevelDto dto : deptLevelList) {
            levelDeptMap.put(dto.getLevel(), dto);
            //当前部门是顶层部门，见doc/使用字符串解决数据库递归查询问题/2.png
            if (LevelUtil.ROOT.equals(dto.getLevel())) {
                rootList.add(dto);
            }
        }
        //按照seq从小到大排序
        Collections.sort(rootList, deptSeqComparator);
        recursiveCreateDeptTree(rootList, LevelUtil.ROOT, levelDeptMap);

        return rootList;
    }

    /**
     * 递归生成部门树形结构
     */
    public void recursiveCreateDeptTree(List<DeptLevelDto> deptLevelList, String level,
                                        Multimap<String, DeptLevelDto> levelDeptMap) {
        //遍历当前层级部门列表
        for (int i = 0; i < deptLevelList.size(); i++) {
            DeptLevelDto deptLevelDto = deptLevelList.get(i);
            //见doc/使用字符串解决数据库递归查询问题/2.png
            String nextLevel = LevelUtil.calculateLevel(level, deptLevelDto.getId());
            //获取下一层级部门列表，比如nextLevel = 0.1，则deptList是level = 0.1的子部门列表
            List<DeptLevelDto> deptList = (List<DeptLevelDto>) levelDeptMap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(deptList)) {
                //按照seq进行排序
                Collections.sort(deptList, deptSeqComparator);
                //设置下一层级部门列表
                deptLevelDto.setDeptList(deptList);
                //递归到下一层处理
                recursiveCreateDeptTree(deptList, nextLevel, levelDeptMap);
            }
        }
    }

    private Comparator<DeptLevelDto> deptSeqComparator = new Comparator<DeptLevelDto>() {
        @Override
        public int compare(DeptLevelDto o1, DeptLevelDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };

    private Comparator<AclModuleLevelDto> aclModuleSeqComparator = new Comparator<AclModuleLevelDto>() {
        @Override
        public int compare(AclModuleLevelDto o1, AclModuleLevelDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };

    private Comparator<AclDto> aclSeqComparator = new Comparator<AclDto>() {
        @Override
        public int compare(AclDto o1, AclDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };
}
