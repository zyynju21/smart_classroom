package com.yiyue.controller;

import org.apache.shiro.SecurityUtils;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

public class BaseControllerTest {

    protected String jwt_token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJhY2Nlc3NUb2tlblZhbGlkaXR5XCI6MTQ0MDAsXCJhZGRyZXNzXCI6XCJcIixcImhlYWRJbWdVcmxcIjpcIlwiLFwibG9naW5OYW1lXCI6XCJhZG1pblwiLFwibmFtZVwiOlwiYWRtaW5cIixcInBvc2l0aW9uXCI6XCJcIixcInJlZnJlc2hUb2tlblwiOlwiOThhNGFjOGQ1MGQyNWVlY2RhNzlmOWRhOTFkMWIzZTBcIixcInJlZnJlc2hUb2tlbkV4cGlyZXNBdFwiOlwiMjAyMS0wOS0xNSAwODozOToyN1wiLFwicmVmcmVzaFRva2VuVmFsaWRpdHlcIjoxNDQwMCxcInVzZXJJZFwiOlwiMVwifSIsImlzcyI6InNldmVuIiwiZXhwIjoxNjMxNjY2MzY3fQ.Qr9FURf8LbTo5ypuQO6A3cl5LGNQJVKBr0GeuELBfzs";
    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @Before
    public void setup() throws Exception {
        //springboot + shrio junit 测试需要加载securityManager
        SecurityUtils.setSecurityManager(wac.getBean(org.apache.shiro.mgt.SecurityManager.class));

        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(wac);

        //springboot + shrio junit 还需要加shrio的过滤器
        builder.addFilters((Filter) wac.getBean("shiroFilter"));

        mockMvc = builder.build();
    }
}
