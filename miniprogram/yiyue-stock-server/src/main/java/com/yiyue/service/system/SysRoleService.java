package com.yiyue.service.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yiyue.common.util.PageUtil;
import com.yiyue.common.vo.ItemVO;
import com.yiyue.interceptor.SystemUserContextHolder;
import com.yiyue.mapper.system.SysOrganMapper;
import com.yiyue.mapper.system.SysRoleMapper;
import com.yiyue.mapper.system.SysUserRoleMapper;
import com.yiyue.model.bean.system.SysOrgan;
import com.yiyue.model.bean.system.SysRole;
import com.yiyue.model.bean.system.SysUserRole;
import com.yiyue.model.dto.system.SysRoleDTO;
import com.yiyue.model.dto.system.SysUserRoleDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysOrganMapper sysOrganMapper;

    public IPage<SysRole> findSysRoleList(SysRoleDTO sysRoleDTO) {
        QueryWrapper<SysRole> queryWrapper=new QueryWrapper<>();
        if(!StringUtils.isBlank(sysRoleDTO.getRoleName())){
            queryWrapper.like("sr.role_name","%"+sysRoleDTO.getRoleName()+"%");
        }
        if(!StringUtils.isBlank(sysRoleDTO.getOrgName())){
            queryWrapper.like("so.org_name","%"+sysRoleDTO.getOrgName()+"%");
        }
        if(!StringUtils.isBlank(sysRoleDTO.getOrgId())){
            queryWrapper.eq("sr.org_id",sysRoleDTO.getOrgId());
        }
        queryWrapper.eq("sr.role_state",0);
        IPage<SysRole> sysRoleIPage=sysRoleMapper.selectSysRoleListPageByParam(PageUtil.getPagination(sysRoleDTO),queryWrapper);
        return sysRoleIPage;
    }

    public void insertSysRole(SysRole sysRole) {
        sysRole.setCreateDate(new Date());
        sysRole.setRoleState(0);
        sysRoleMapper.insert(sysRole);
    }

    public void deleteSysRoleById(String roleId) {
        SysRole sysRole=this.sysRoleMapper.selectById(roleId);
        sysRole.setRoleState(-1);
        sysRoleMapper.updateById(sysRole);
    }

    public SysRole findSysRoleById(String roleId) {
        SysRole sysRole=sysRoleMapper.selectById(roleId);
        SysOrgan sysOrgan=sysOrganMapper.selectById(sysRole.getOrgId());
        sysRole.setOrgName(sysOrgan.getOrgName());
        return sysRole;
    }

    public void updateSysRole(SysRole sysRole) {
        sysRoleMapper.updateById(sysRole);
    }

    /**
     * 后台管理 -> 系统管理 ->用户配置角色 ，获得所有角色列表
     * @return
     */
    public List<ItemVO> findSysRole2ItemVOList() {
        QueryWrapper<SysRole> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("role_state",0);
        SysOrgan sysOrgan=SystemUserContextHolder.getLoginUser().getSysOrgan();
        if(sysOrgan.getOrgType()!=0){
            queryWrapper.eq("sr.org_id",sysOrgan.getOrgId());
        }
        List<SysRole> list=this.sysRoleMapper.selectSysRoleListByParam(queryWrapper);
        List<ItemVO> result=new ArrayList<>();
        for(SysRole sr:list){
            ItemVO itemVO=new ItemVO();
            itemVO.setKey(sr.getRoleId());
            itemVO.setTitle(sr.getRoleName()+"("+sr.getOrgName()+")");
            result.add(itemVO);
        }
        return result;
    }

    /**
     * 后台管理 -> 系统管理 ->用户配置角色 ，获得用户已配置的角色列表
     * @return
     */
    public List<String> findUserAuthRoleList(String userId) {
        QueryWrapper<SysUserRole> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<SysUserRole> list=this.sysUserRoleMapper.selectList(queryWrapper);
        List<String> roleIds=new ArrayList<>();
        for(SysUserRole sur:list){
            roleIds.add(sur.getRoleId());
        }
        return roleIds;
    }

    /**
     * 后台管理 -> 系统管理 ->用户配置角色 ,保持用户配置角色
     * @param sysUserRoleDTO
     */
    public void saveUserAuthRole(SysUserRoleDTO sysUserRoleDTO) {
        String userId=sysUserRoleDTO.getUserId();
        List<String> roleIds=sysUserRoleDTO.getRoleIds();

        QueryWrapper<SysUserRole> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        this.sysUserRoleMapper.delete(queryWrapper);

        if(null!=roleIds&&roleIds.size()>0){
            for(String roleId:roleIds){
                SysUserRole sur=new SysUserRole();
                sur.setUserId(userId);
                sur.setRoleId(roleId);
                this.sysUserRoleMapper.insert(sur);
            }
        }
    }
}
