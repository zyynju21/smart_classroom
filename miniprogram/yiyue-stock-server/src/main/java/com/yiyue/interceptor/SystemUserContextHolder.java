package com.yiyue.interceptor;

import com.yiyue.common.util.AbstractUserContextHolder;
import com.yiyue.model.vo.system.LoginUser;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @Author: XiaoQi
 * @Date: 2020/9/15 10:37
 * 系统后台用户信息
 */
@Component
public class SystemUserContextHolder extends AbstractUserContextHolder<LoginUser> implements InitializingBean {

    private static SystemUserContextHolder contextHolder;

    @Override
    public void afterPropertiesSet() {
        contextHolder = this;
    }

    private static SystemUserContextHolder getContextHolder() {
        return contextHolder;
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public static LoginUser getLoginUser() {
        return getContextHolder().getContext();
    }

    /**
     * 获取用户Id
     *
     * @return
     */
    public static String getUserId() {
        return getContextHolder().getContext().getUserId();
    }

}
