package com.yiyue.service.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yiyue.common.exception.CustomException;
import com.yiyue.common.util.Assert;
import com.yiyue.common.util.PageUtil;
import com.yiyue.interceptor.SystemUserContextHolder;
import com.yiyue.mapper.system.SysOrganMapper;
import com.yiyue.mapper.system.SysUserMapper;
import com.yiyue.model.bean.system.SysOrgan;
import com.yiyue.model.bean.system.SysUser;
import com.yiyue.model.dto.system.SysUserDTO;
import com.yiyue.model.dto.system.UserPasswordDTO;
import com.yiyue.model.vo.system.SysUserVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
@Transactional
public class SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysOrganMapper sysOrganMapper;

    @Autowired
    private DefaultPasswordService passwordService;

    public IPage<SysUserVO> findSysUserList(SysUserDTO sysUserDTO) {
        QueryWrapper<SysUserVO> queryWrapper=new QueryWrapper<>();
        if(!StringUtils.isBlank(sysUserDTO.getUserName())){
            queryWrapper.like("su.user_name","%"+sysUserDTO.getUserName()+"%");
        }
        if(!StringUtils.isBlank(sysUserDTO.getLoginName())){
            queryWrapper.like("su.login_name","%"+sysUserDTO.getLoginName()+"%");
        }
        if(!StringUtils.isBlank(sysUserDTO.getPhone())){
            queryWrapper.like("su.phone","%"+sysUserDTO.getPhone()+"%");
        }
        if(!StringUtils.isBlank(sysUserDTO.getUserSex())){
            queryWrapper.eq("su.user_sex",sysUserDTO.getUserSex());
        }
        if(!StringUtils.isBlank(sysUserDTO.getOrgName())){
            queryWrapper.like("so.org_name","%"+sysUserDTO.getOrgName()+"%");
        }
        if(!StringUtils.isBlank(sysUserDTO.getOrgId())){
            queryWrapper.eq("su.org_id",sysUserDTO.getOrgId());
        }
        queryWrapper.eq("su.user_state",0);
        queryWrapper.eq("so.org_state",0);
        IPage<SysUserVO> ipage=sysUserMapper.findSysUserListByParam(PageUtil.getPagination(sysUserDTO),queryWrapper);
        return ipage;
    }

    public SysUser findSysUserByUserId(String userId) {
        SysUser sysUser=this.sysUserMapper.selectById(userId);
        SysOrgan sysOrgan=this.sysOrganMapper.selectById(sysUser.getOrgId());
        sysUser.setOrgName(sysOrgan.getOrgName());
        return sysUser;
    }


    public void insertSysUser(SysUser sysUser) {
        //验证用户登录名是否已注册过
        QueryWrapper<SysUser> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("login_name",sysUser.getLoginName());
        SysUser user=this.sysUserMapper.selectOne(queryWrapper);
        if(null!=user){
            throw new CustomException("用户登录名已被注册过，请换个登录名");
        }
        sysUser.setLoginPass(passwordService.encryptPassword(sysUser.getLoginPass()));
        sysUser.setCreateDate(new Date());
        sysUser.setUserState(0);
        this.sysUserMapper.insert(sysUser);
    }

    public void deleteSysUserById(String userId) {
        SysUser sysUser=sysUserMapper.selectById(userId);
        sysUser.setUserState(-1);
        this.sysUserMapper.updateById(sysUser);
    }

    public void updateSysUser(SysUser sysUser) {
        this.sysUserMapper.updateById(sysUser);
    }

    public void reset_user_login_pass(String userId) {
        SysUser sysUser=this.sysUserMapper.selectById(userId);
        sysUser.setLoginPass(passwordService.encryptPassword("123456"));
        this.sysUserMapper.updateById(sysUser);
    }

    public void updateLoginPassword(UserPasswordDTO userPasswordDTO) {
        SysUser sysUser = sysUserMapper.selectById(SystemUserContextHolder.getUserId());
        Assert.isBlank(userPasswordDTO.getOldPassword(), "用户旧密码不能为空");
        Assert.isMaxLength(userPasswordDTO.getOldPassword(), 20, "用户旧密码长度不能超过20位");

        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(sysUser.getLoginName(), userPasswordDTO.getOldPassword());
            subject.login(usernamePasswordToken);
        }catch (Exception e){
            throw new CustomException("用户旧密码错误");
        }
        Assert.isBlank(userPasswordDTO.getNewPassword(), "用户新密码不能为空");
        Assert.isMaxLength(userPasswordDTO.getNewPassword(), 20, "用户新密码长度不能超过20位");
        Assert.isBlank(userPasswordDTO.getConfirmPassword(), "用户确认密码不能为空");
        Assert.isMaxLength(userPasswordDTO.getConfirmPassword(), 20, "用户确认密码长度不能超过20位");
        if (!userPasswordDTO.getNewPassword().equals(userPasswordDTO.getConfirmPassword())) {
            throw new CustomException("用户新密码和确认密码不同");
        }
        sysUser.setLoginPass(passwordService.encryptPassword(userPasswordDTO.getNewPassword()));
        sysUserMapper.updateById(sysUser);
    }


    public IPage<SysUser> selectAllList(SysUserDTO sysUserDTO) {
        QueryWrapper<SysUser> queryWrapper=new QueryWrapper<>();
        if(!StringUtils.isBlank(sysUserDTO.getUserName())){
            queryWrapper.like("user_name","%"+sysUserDTO.getUserName()+"%");
        }
        if(!StringUtils.isBlank(sysUserDTO.getPhone())){
            queryWrapper.like("phone","%"+sysUserDTO.getPhone()+"%");
        }
        queryWrapper.eq("user_state",0);
        IPage<SysUser> ipage=sysUserMapper.selectPage(PageUtil.getPagination(sysUserDTO),queryWrapper);
        return ipage;
    }
}
