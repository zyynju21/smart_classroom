package com.yiyue.service.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yiyue.common.util.JwtUtil;
import com.yiyue.common.util.LocalDateTimeUtil;
import com.yiyue.common.util.TokenGenerator;
import com.yiyue.mapper.system.SysMenuMapper;
import com.yiyue.mapper.system.SysRoleMapper;
import com.yiyue.mapper.system.SysUserMapper;
import com.yiyue.model.bean.system.SysMenu;
import com.yiyue.model.bean.system.SysOrgan;
import com.yiyue.model.bean.system.SysUser;
import com.yiyue.model.vo.system.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class SysLoginService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysOrganService sysOrganService;
    /**
     * 用户认证，获得用户名和密码信息 返回的是sysUser 只有名字 id信息
     * @param loginName 登录用户名
     * @return  返回该登录用户的系统用户信息
     */
    public SysUser getSysUserByLoginName(String loginName) {
        QueryWrapper<SysUser> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("login_name",loginName);
        SysUser sysUser=sysUserMapper.selectOne(queryWrapper);
        return sysUser;
    }

    /**
     * 按照前端需求的格式返回权限数据 返回登录用户的信息
     * @param loginName
     * @return
     */
    public LoginUser findLoginInfoByName(String loginName) {
        SysUser sysUser=this.getSysUserByLoginName(loginName);
        //用户
        LoginUser loginUser=new LoginUser();
        loginUser.setUserId(sysUser.getUserId());
        loginUser.setLoginName(sysUser.getLoginName());
        loginUser.setUserName(sysUser.getUserName());
        loginUser.setPhone(sysUser.getPhone());
        loginUser.setHeadImgUrl(sysUser.getHeadImgUrl());
        loginUser.setOrgId(sysUser.getOrgId());
        SysOrgan sysOrgan=sysOrganService.findSysOrganByOrgId(sysUser.getOrgId());
        loginUser.setSysOrgan(sysOrgan);
        loginUser.setAccessTokenValidity(sysUser.getAccessTokenValidity());
        loginUser.setRefreshTokenValidity(sysUser.getRefreshTokenValidity());

        return loginUser;

    }


    /**
     * 返回给前端JWTUserInfo用户信息
     * @param loginUser
     * @return
     */
    public JwtLoginUser getJwtLoginUser(LoginUser loginUser) {
        // refreshTokenExpiresAt等于当前时间加上refreshTokenValidity分钟
        String refreshTokenExpiresAt = LocalDateTimeUtil.getStringByLocalDateTime(LocalDateTime.now().plusMinutes(loginUser.getRefreshTokenValidity()));
        // 生成refreshToken
        String refreshToken = TokenGenerator.generateValue();

        // 设置刷新token失效时间
        loginUser.setRefreshTokenExpiresAt(refreshTokenExpiresAt);
        // 设置刷新token
        loginUser.setRefreshToken(refreshToken);
        // accessTokenExpiresAt等于前时间加上accessTokenValidity分钟
        LocalDateTime accessTokenExpiresAt = LocalDateTime.now().plusMinutes(loginUser.getAccessTokenValidity());

        String jwtToken = JwtUtil.createToken(loginUser, LocalDateTimeUtil.convertLocalDteTimeToDate(accessTokenExpiresAt));
        log.debug("登录用户：{}获得jwt令牌：{}",loginUser,jwtToken);
        // 封装返回对象
        JwtLoginUser jwtLoginUser = new JwtLoginUser();
        jwtLoginUser.setUserId(loginUser.getUserId());
        jwtLoginUser.setAccessToken(jwtToken);
        jwtLoginUser.setLoginName(loginUser.getLoginName());
        jwtLoginUser.setHeadImgUrl(loginUser.getHeadImgUrl());
        jwtLoginUser.setUserName(loginUser.getUserName());
        jwtLoginUser.setPhone(loginUser.getPhone());
        jwtLoginUser.setRefreshToken(refreshToken);
        jwtLoginUser.setAccessTokenExpiresAt(LocalDateTimeUtil.getStringByLocalDateTime(accessTokenExpiresAt));
        jwtLoginUser.setRefreshTokenExpiresAt(refreshTokenExpiresAt);
        return jwtLoginUser;
    }

    /**
     * 查询登录用户已授权的菜单列表，按照前端数据格式要求返回菜单
     * @param loginUser
     * @return
     */
    public List<UserMenuNav> getLoginUserMenuNav(LoginUser loginUser) {
        List<SysMenu> sysMenuList=sysMenuMapper.findSysMenuByLoginUserId(loginUser.getUserId());
        List<UserMenuNav> resultList=new ArrayList<>();
        for(SysMenu menu:sysMenuList){
            UserMenuNav userMenuNav=new UserMenuNav();
            userMenuNav.setId(menu.getMenuId());
            userMenuNav.setParentId(menu.getParentMenuId());
            userMenuNav.setName(menu.getMenuName());
            userMenuNav.setComponent(menu.getMenuComponent());
            userMenuNav.setPath(menu.getMenuUrl());

            UserMenuNavMeta userMenuNavMeta=new UserMenuNavMeta();
            userMenuNavMeta.setIcon(menu.getMenuIcon());
            userMenuNavMeta.setHideChildren(menu.getHiddenChildren()==1?true:false);
            userMenuNavMeta.setShow(menu.getMenuShow()==1?true:false);
            userMenuNavMeta.setTitle(menu.getMenuName());
            if(null!=menu.getSelectMenuId()){
                String selectedKeys="";
                for(SysMenu m:sysMenuList){
                    if(menu.getSelectMenuId().equals(m.getMenuId())){
                        selectedKeys=m.getMenuUrl();
                        break;
                    }
                }
                userMenuNavMeta.setSelectedKeys(selectedKeys);
            }else{
                userMenuNavMeta.setSelectedKeys(menu.getMenuUrl());
            }
            userMenuNav.setMeta(userMenuNavMeta);
            resultList.add(userMenuNav);
        }
        return resultList;
    }


}
