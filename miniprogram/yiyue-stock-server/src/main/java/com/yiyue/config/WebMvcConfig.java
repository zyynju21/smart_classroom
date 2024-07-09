package com.yiyue.config;


import com.yiyue.interceptor.SystemGetUserInterceptor;
import com.yiyue.interceptor.SystemVerifyUserInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: XiaoQi
 * @Date: 2020/9/15 10:14
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${upload.file-path}")
    private String uploadFilePath;

    private SystemVerifyUserInterceptor systemVerifyUserInterceptor;
    private SystemGetUserInterceptor systemGetUserInterceptor;

    public WebMvcConfig(SystemVerifyUserInterceptor systemVerifyUserInterceptor, SystemGetUserInterceptor systemGetUserInterceptor) {
        this.systemVerifyUserInterceptor = systemVerifyUserInterceptor;
        this.systemGetUserInterceptor = systemGetUserInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(systemVerifyUserInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/")
                .excludePathPatterns("/system/login")
                .excludePathPatterns("/system/user/create")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**")
                .excludePathPatterns("/file/**")
                .excludePathPatterns("/"+uploadFilePath+"/**")
                .order(1);
        registry.addInterceptor(systemGetUserInterceptor).order(2);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/"+uploadFilePath+"/**").addResourceLocations("file:./"+uploadFilePath+"/");
    }

}
