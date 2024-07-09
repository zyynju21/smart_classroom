package com.yiyue.model.bean.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.Set;


@Data
@TableName("sys_user")
public class SysUser {

    @TableId(type = IdType.ASSIGN_ID)
    private String userId;
    private String loginName;
    private String loginPass;
    private String userName;
    private String userSex;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birth;
    private String headImgUrl;
    private String phone;
    private String province;
    private String city;
    private String county;
    private String address;
    private String job;
    private String ofArea;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    private Integer userState;
    private String saltValue;

    @TableField(exist = false)
    private Set<SysRole> roles;

    /**
     * 访问令牌有效时间
     */
    private Integer accessTokenValidity;

    /**
     * 刷新token有效时间
     */
    private Integer refreshTokenValidity;

    private String orgId;
    @TableField(exist = false)
    private String orgName;

}
