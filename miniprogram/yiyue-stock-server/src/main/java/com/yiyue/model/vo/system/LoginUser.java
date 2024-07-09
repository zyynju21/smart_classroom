package com.yiyue.model.vo.system;

import com.yiyue.model.bean.system.SysOrgan;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class LoginUser {
    private String userId;
    private String loginName;
    private String userName;
    private String headImgUrl;
    private String phone;
    private String refreshToken;
    private String refreshTokenExpiresAt;
    private String orgId;
    private String userSex;
    private SysOrgan sysOrgan;
    /**
     * 访问令牌有效时间
     */
    private Integer accessTokenValidity;

    /**
     * 刷新token有效时间
     */
    private Integer refreshTokenValidity;

}
