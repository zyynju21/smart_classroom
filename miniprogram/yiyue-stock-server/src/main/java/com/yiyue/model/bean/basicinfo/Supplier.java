package com.yiyue.model.bean.basicinfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* 供应商表
* author: liyun
* create date: 2021-10-13
*/

@Data
@TableName("t_supplier")
public class Supplier implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    // 省份
    private String province;

    // 城市
    private String city;

    // 区县
    private String county;

    // 联系地址
    private String address;

    // 联系人
    private String contact;

    // 供应商名称
    private String name;

    // 联系电话
    private String phone;

    // 备注
    private String remarks;

    // 是否删除
    private int isDel;

    // 组织机构id
    private String orgId;

    // 组织机构名
    @TableField(exist = false)
    private String orgName;

}
