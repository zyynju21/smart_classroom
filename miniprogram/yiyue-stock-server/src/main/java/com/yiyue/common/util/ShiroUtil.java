package com.yiyue.common.util;

import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.util.UUID;

public class ShiroUtil {

    public final static int HASH_ITERATIONS = 5;

    /**
     * 生成32的随机盐值
     */
    public static String createSalt(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 加盐加密
     * @param srcPwd    原始密码
     * @param saltValue 盐值
     */
    public static String salt(Object srcPwd, String saltValue){
        return new SimpleHash("MD5", srcPwd, saltValue, HASH_ITERATIONS).toString();
    }

    public static void passwordEncode(String password){
        String salt=createSalt();
        System.out.println("盐值："+salt);
        Object result = salt(password,salt);
        System.out.println("密码："+result);
    }

    public static void defaultPasswordEncode(String password){
        DefaultPasswordService passwordService=new DefaultPasswordService();
        String pwd=passwordService.encryptPassword(password);
        System.out.println(pwd);
    }

    public static void main(String[] args) {

        defaultPasswordEncode("123");
        defaultPasswordEncode("123");
    }
}
