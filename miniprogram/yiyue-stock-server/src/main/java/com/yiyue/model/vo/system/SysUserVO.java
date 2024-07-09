package com.yiyue.model.vo.system;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class SysUserVO {

    private String userId;
    private String userName;
    private String loginName;
    private String headImgUrl;
    private String userSex;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birth;
    private String phone;
    private String address;

    private String orgName;
}
