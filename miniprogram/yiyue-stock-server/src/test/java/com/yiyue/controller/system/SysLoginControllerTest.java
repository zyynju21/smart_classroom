package com.yiyue.controller.system;

import com.alibaba.fastjson.JSON;
import com.yiyue.controller.BaseControllerTest;
import com.yiyue.model.bean.system.SysUser;
import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class SysLoginControllerTest extends BaseControllerTest {

    @Test
    public void SysLoginTest() throws Exception {
        SysUser user=new SysUser();
        user.setLoginName("admin");
        user.setLoginPass("123");
        String json=JSON.toJSONString(user);
        String result=mockMvc.perform(post("/system/login").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())//返回的状态是200
                .andDo(print())//打印出请求和相应的内容
                .andReturn().getResponse().getContentAsString();
        log.debug("返回json:{}",result);
    }

    @Test
    public void SysGetUserInfoTest() throws Exception {
        String result=mockMvc.perform(get("/system/get_user_info")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Access_Token",jwt_token))
                .andExpect(status().isOk())//返回的状态是200
                .andDo(print())//打印出请求和相应的内容
                .andReturn().getResponse().getContentAsString();
        log.debug("返回json:{}",result);
    }

    @Test
    public void SysGetUserMenuNavTest() throws Exception {
        String result=mockMvc.perform(get("/system/get_user_menu_nav")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Access_Token",jwt_token))
                .andExpect(status().isOk())//返回的状态是200
                .andDo(print())//打印出请求和相应的内容
                .andReturn().getResponse().getContentAsString();
        log.debug("返回json:{}",result);
    }
}
