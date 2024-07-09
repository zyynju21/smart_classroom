package com.yiyue.interceptor;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.yiyue.common.util.AbstractUserContextHolder;
import com.yiyue.common.util.JsonTools;
import com.yiyue.common.util.JwtUtil;
import com.yiyue.model.vo.system.LoginUser;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @Author: XiaoQi
 * @Date: 2020/9/15 10:18
 */
@Component
public class SystemGetUserInterceptor implements HandlerInterceptor {

    @Value("${authorization.key-name}")
    private String authorizationKeyName;

    private AbstractUserContextHolder<LoginUser> abstractUserContextHolder;

    public SystemGetUserInterceptor(AbstractUserContextHolder<LoginUser> abstractUserContextHolder) {
        this.abstractUserContextHolder = abstractUserContextHolder;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) {
        LoginUser loginUser = getSystemContext(request);
        if (Objects.nonNull(loginUser) && Objects.nonNull(loginUser.getUserId())) {
            abstractUserContextHolder.setContext(loginUser);
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        abstractUserContextHolder.clearContext();
    }

    protected LoginUser getSystemContext(HttpServletRequest request) {
        LoginUser loginUser = null;
        String accessToken = request.getHeader(authorizationKeyName);
        if (!StringUtils.isBlank(accessToken)) {
            DecodedJWT decodedJwt = JwtUtil.parseToken(accessToken);
            loginUser = JsonTools.jsonToObject(decodedJwt.getSubject(), LoginUser.class);
        }
        return loginUser;
    }

}
