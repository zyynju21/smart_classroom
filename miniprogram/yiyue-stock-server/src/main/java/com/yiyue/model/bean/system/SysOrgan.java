package com.yiyue.model.bean.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;


@Data
@TableName("sys_organ")
public class SysOrgan {
    @TableId(type = IdType.ASSIGN_ID)
    private String orgId;
    private String orgName;
    private String orgLogo;
    private String orgPerson;
    private String orgPhone;
    private Integer orgType;
    private Integer orgState;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
}
