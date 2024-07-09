package com.yiyue.interceptor;


import com.yiyue.common.result.Result;
import com.yiyue.common.util.JwtUtil;
import com.yiyue.common.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: XiaoQi
 * @Date: 2020/9/15 10:17
 */
@Component
public class SystemVerifyUserInterceptor implements HandlerInterceptor {

    @Value("${authorization.key-name}")
    private String authorizationKeyName;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) {
        String accessToken = request.getHeader(authorizationKeyName);
        if (StringUtils.isBlank(accessToken)) {
            ResponseUtil.write(Result.fail("Token is required."));
            return false;
        }
        // 效验accessToken是否已经超时或失效
        boolean isValid = JwtUtil.verifyToken(accessToken);
        // 失效直接返回异常信息重新登录
        if (!isValid) {
            ResponseUtil.write(Result.fail("Token is invalid."));
            return false;
        }
        return true;
    }

}
