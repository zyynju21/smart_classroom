package com.yiyue.model.vo.basicinfo;

import lombok.Data;

@Data
public class SupplierVO {

    private String id;

    // 省份
    private String province;

    // 城市
    private String city;

    // 区县
    private String county;

    // 客户地址
    private String address;

    // 合并地址
    private String fullAddress;

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
    private String orgName;
}
