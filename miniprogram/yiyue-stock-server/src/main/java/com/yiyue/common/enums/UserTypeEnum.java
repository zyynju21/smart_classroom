package com.yiyue.common.enums;

import lombok.Getter;

@Getter
public enum UserTypeEnum {

    SYSTEM(0,"系统用户"),
    ORGAN(1,"组织用户");

    private Integer sysUserTypeId;
    private String sysUserType;

    UserTypeEnum(Integer sysUserTypeId,String sysUserType){
        this.sysUserTypeId=sysUserTypeId;
        this.sysUserType=sysUserType;
    }
}
