package com.yiyue.controller.system;


import com.yiyue.common.aspact.SystemPlatLog;
import com.yiyue.common.result.Result;
import com.yiyue.interceptor.SystemUserContextHolder;
import com.yiyue.model.bean.system.SysUser;
import com.yiyue.model.vo.system.JwtLoginUser;
import com.yiyue.model.vo.system.LoginUser;
import com.yiyue.model.vo.system.SysUserVO;
import com.yiyue.model.vo.system.UserMenuNav;
import com.yiyue.service.system.SysLoginService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/system")
public class SysLoginController {

    @Autowired
    private SysLoginService sysLoginService;

    /**
     * 处理登录请求
     * @param user
     * @return
     */
    @PostMapping(value = "/login",produces = "application/json;charset=utf-8")
    public Result login(@RequestBody SysUser user) {
        if (StringUtils.isEmpty(user.getLoginName()) || StringUtils.isEmpty(user.getLoginPass())) {
            return Result.fail("请输入用户名和密码！");
        }
        //用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getLoginName(),user.getLoginPass());
        try {
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(usernamePasswordToken);
            //如果登陆成功，返回前段需要的数据
            LoginUser loginUser=sysLoginService.findLoginInfoByName(user.getLoginName());
            JwtLoginUser jwtLoginUser=sysLoginService.getJwtLoginUser(loginUser);
            return Result.success(jwtLoginUser);
        } catch (UnknownAccountException e) {
            log.error("用户名不存在！", e);
            return Result.fail("用户名不存在！");
        } catch (AuthenticationException e) {
            log.error("账号或密码错误！", e);
            return Result.fail("账号或密码错误！");
        } catch (AuthorizationException e) {
            log.error("没有权限！", e);
            return Result.fail("没有权限！");
        }
    }

    /**
     * 前端框架在登录时需要返回用户信息
     * @return
     */
    @RequestMapping(value = "/get_user_info",produces = "application/json;charset=utf-8")
    public Result<LoginUser> getUserInfo() {
        return Result.success(SystemUserContextHolder.getLoginUser());
    }

    /**
     * 前端框架在登录时需要返回用户菜单
     * @return
     */
    @RequestMapping(value = "/get_user_menu_nav",produces = "application/json;charset=utf-8")
    public Result getUserMenuNav() {
        LoginUser loginUser=SystemUserContextHolder.getLoginUser();
        List<UserMenuNav> userMenuNavs=sysLoginService.getLoginUserMenuNav(loginUser);
        return Result.success(userMenuNavs);
    }



}
