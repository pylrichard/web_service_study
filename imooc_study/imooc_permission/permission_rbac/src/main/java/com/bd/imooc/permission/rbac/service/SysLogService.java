package com.bd.imooc.permission.rbac.service;

import com.bd.imooc.permission.rbac.beans.LogType;
import com.bd.imooc.permission.rbac.beans.PageQuery;
import com.bd.imooc.permission.rbac.beans.PageResult;
import com.bd.imooc.permission.rbac.common.RequestHolder;
import com.bd.imooc.permission.rbac.dao.*;
import com.bd.imooc.permission.rbac.dto.SearchLogDto;
import com.bd.imooc.permission.rbac.exception.ParamException;
import com.bd.imooc.permission.rbac.model.*;
import com.bd.imooc.permission.rbac.param.SearchLogParam;
import com.bd.imooc.permission.rbac.util.BeanValidator;
import com.bd.imooc.permission.rbac.util.IpUtil;
import com.bd.imooc.permission.rbac.util.JsonMapper;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class SysLogService {
    @Resource
    private SysLogMapper sysLogMapper;
    @Resource
    private SysDeptMapper sysDeptMapper;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysAclModuleMapper sysAclModuleMapper;
    @Resource
    private SysAclMapper sysAclMapper;
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysRoleAclService sysRoleAclService;
    @Resource
    private SysRoleUserService sysRoleUserService;

    /**
     * 恢复权限操作记录
     */
    public void recover(int id) {
        SysLogWithBLOBs sysLog = sysLogMapper.selectByPrimaryKey(id);
        Preconditions.checkNotNull(sysLog, "待还原的记录不存在");
        switch (sysLog.getType()) {
            case LogType.TYPE_DEPT:
                SysDept beforeDept = sysDeptMapper.selectByPrimaryKey(sysLog.getTargetId());
                Preconditions.checkNotNull(beforeDept, "待还原的部门已经不存在了");
                SysDept afterDept = (SysDept) getRecoverLog(sysLog);
                sysDeptMapper.updateByPrimaryKeySelective(afterDept);
                saveDeptLog(beforeDept, afterDept);
                break;
            case LogType.TYPE_USER:
                SysUser beforeUser = sysUserMapper.selectByPrimaryKey(sysLog.getTargetId());
                Preconditions.checkNotNull(beforeUser, "待还原的用户已经不存在了");
                SysUser afterUser = (SysUser) getRecoverLog(sysLog);
                sysUserMapper.updateByPrimaryKeySelective(afterUser);
                saveUserLog(beforeUser, afterUser);
                break;
            case LogType.TYPE_ACL_MODULE:
                SysAclModule beforeAclModule = sysAclModuleMapper.selectByPrimaryKey(sysLog.getTargetId());
                Preconditions.checkNotNull(beforeAclModule, "待还原的权限模块已经不存在了");
                SysAclModule afterAclModule = (SysAclModule) getRecoverLog(sysLog);
                sysAclModuleMapper.updateByPrimaryKeySelective(afterAclModule);
                saveAclModuleLog(beforeAclModule, afterAclModule);
                break;
            case LogType.TYPE_ACL:
                SysAcl beforeAcl = sysAclMapper.selectByPrimaryKey(sysLog.getTargetId());
                Preconditions.checkNotNull(beforeAcl, "待还原的权限点已经不存在了");
                SysAcl afterAcl = (SysAcl) getRecoverLog(sysLog);
                sysAclMapper.updateByPrimaryKeySelective(afterAcl);
                saveAclLog(beforeAcl, afterAcl);
                break;
            case LogType.TYPE_ROLE:
                SysRole beforeRole = sysRoleMapper.selectByPrimaryKey(sysLog.getTargetId());
                Preconditions.checkNotNull(beforeRole, "待还原的角色已经不存在了");
                SysRole afterRole = (SysRole) getRecoverLog(sysLog);
                sysRoleMapper.updateByPrimaryKeySelective(afterRole);
                saveRoleLog(beforeRole, afterRole);
                break;
            case LogType.TYPE_ROLE_ACL:
                SysRole aclRole = sysRoleMapper.selectByPrimaryKey(sysLog.getTargetId());
                Preconditions.checkNotNull(aclRole, "角色已经不存在了");
                sysRoleAclService.changeRoleAcls(sysLog.getTargetId(),
                        JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<List<Integer>>() {
                        }));
                break;
            case LogType.TYPE_ROLE_USER:
                SysRole userRole = sysRoleMapper.selectByPrimaryKey(sysLog.getTargetId());
                Preconditions.checkNotNull(userRole, "角色已经不存在了");
                sysRoleUserService.changeRoleUsers(sysLog.getTargetId(),
                        JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<List<Integer>>() {
                        }));
                break;
            default:
        }
    }

    private SysBase getRecoverLog(SysLogWithBLOBs sysLog) {
        if (StringUtils.isBlank(sysLog.getNewValue()) || StringUtils.isBlank(sysLog.getOldValue())) {
            throw new ParamException("新增和删除操作不做还原");
        }
        SysBase after = JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<SysBase>() {
        });
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());

        return after;
    }

    /**
     * 获取权限操作记录分页结果
     */
    public PageResult<SysLogWithBLOBs> searchPageList(SearchLogParam param, PageQuery page) {
        BeanValidator.check(page);
        SearchLogDto dto = new SearchLogDto();
        dto.setType(param.getType());
        if (StringUtils.isNotBlank(param.getBeforeSeg())) {
            dto.setBeforeSeg("%" + param.getBeforeSeg() + "%");
        }
        if (StringUtils.isNotBlank(param.getAfterSeg())) {
            dto.setAfterSeg("%" + param.getAfterSeg() + "%");
        }
        if (StringUtils.isNotBlank(param.getOperator())) {
            dto.setOperator("%" + param.getOperator() + "%");
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (StringUtils.isNotBlank(param.getFromTime())) {
                dto.setFromTime(dateFormat.parse(param.getFromTime()));
            }
            if (StringUtils.isNotBlank(param.getToTime())) {
                dto.setToTime(dateFormat.parse(param.getToTime()));
            }
        } catch (Exception e) {
            throw new ParamException("传入的日期格式有问题，正确格式为：yyyy-MM-dd HH:mm:ss");
        }
        int count = sysLogMapper.countBySearchDto(dto);
        if (count > 0) {
            List<SysLogWithBLOBs> logList = sysLogMapper.getPageListBySearchDto(dto, page);

            return PageResult.<SysLogWithBLOBs>builder().total(count).data(logList).build();
        }

        return PageResult.<SysLogWithBLOBs>builder().build();
    }

    public void saveDeptLog(SysDept before, SysDept after) {
        saveLog(LogType.TYPE_DEPT, before, after);
    }

    public void saveUserLog(SysUser before, SysUser after) {
        saveLog(LogType.TYPE_USER, before, after);
    }

    public void saveAclModuleLog(SysAclModule before, SysAclModule after) {
        saveLog(LogType.TYPE_ACL_MODULE, before, after);
    }

    public void saveAclLog(SysAcl before, SysAcl after) {
        saveLog(LogType.TYPE_ACL, before, after);
    }

    public void saveRoleLog(SysRole before, SysRole after) {
        saveLog(LogType.TYPE_ROLE, before, after);
    }

    private void saveLog(Integer type, Object before, Object after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(type);
        sysLog.setTargetId(after == null ? ((SysBase) before).getId() : ((SysBase) after).getId());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperateTime(new Date());
        sysLog.setStatus(1);
        sysLogMapper.insertSelective(sysLog);
    }

    public void saveRoleAclLog(int roleId, List<Integer> before, List<Integer> after) {
        saveRoleLog(LogType.TYPE_ROLE_ACL, roleId, before, after);
    }

    public void saveRoleUserLog(int roleId, List<Integer> before, List<Integer> after) {
        saveRoleLog(LogType.TYPE_ROLE_USER, roleId, before, after);
    }

    private void saveRoleLog(Integer type, int roleId, List<Integer> before, List<Integer> after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(type);
        sysLog.setTargetId(roleId);
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperateTime(new Date());
        sysLog.setStatus(1);
        sysLogMapper.insertSelective(sysLog);
    }
}
