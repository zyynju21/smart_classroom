package com.yiyue.model.dto.basicinfo;

import com.yiyue.common.util.BasePageDTO;
import lombok.Data;

/**
* 供应商表
* author: liyun
* create date: 2021-10-13
*/

@Data
public class SupplierDTO extends BasePageDTO{

    // 条件查询字段，需要自己定义添加或者修改
    // 主键
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
    private String isDel;
    // 组织机构id
    private String orgId;
    // 组织机构名
    private String orgName;
}