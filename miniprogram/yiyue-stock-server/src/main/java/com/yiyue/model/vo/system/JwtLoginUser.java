package com.yiyue.model.vo.system;

import lombok.Data;

@Data
public class JwtLoginUser {

    private String userId;
    private String loginName;

    private String headImgUrl;

    private String accessToken;

    private String userName;

    private String phone;

    private String accessTokenExpiresAt;

    private String refreshToken;

    private String refreshTokenExpiresAt;
}
