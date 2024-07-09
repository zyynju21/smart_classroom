package com.yiyue.config;


import com.yiyue.model.bean.system.SysMenu;
import com.yiyue.model.bean.system.SysRole;
import com.yiyue.model.bean.system.SysUser;
import com.yiyue.service.system.SysLoginService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import org.springframework.beans.factory.annotation.Autowired;


@Slf4j
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private SysLoginService loginService;

    /**
     * @MethodName doGetAuthorizationInfo
     * @Description 权限配置类
     * @Param [principalCollection]
     * @Return AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录用户名
        String name = (String) principalCollection.getPrimaryPrincipal();

        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        return simpleAuthorizationInfo;
    }

    /**
     * @MethodName doGetAuthenticationInfo
     * @Description 认证配置类
     * @Param [authenticationToken]
     * @Return AuthenticationInfo
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token=(UsernamePasswordToken) authenticationToken;
        //获取用户信息
        String name = token.getUsername();
        SysUser user = loginService.getSysUserByLoginName(name);

        if (user == null) {
            throw new AuthenticationException("用户不存在！");
        }
        if (user.getUserState() == -1){
            throw new AuthenticationException("用户已冻结！");
        }

        //盐值 1.2以后不要需要用户记录盐值
        //ByteSource salt = ByteSource.Util.bytes(user.getSaltValue());
        //这里验证authenticationToken和simpleAuthenticationInfo的信息
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user.getLoginName(), user.getLoginPass(),  getName());
        return simpleAuthenticationInfo;

    }
}
