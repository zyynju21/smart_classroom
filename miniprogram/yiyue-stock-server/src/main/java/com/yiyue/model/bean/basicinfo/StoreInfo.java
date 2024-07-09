package com.yiyue.model.bean.basicinfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
@TableName("t_store_info")
public class StoreInfo implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    // 仓库名字
    private String storeName;

    // 负责人
    private String contact;

    // 联系电话
    private String phone;

    // 备注
    private String remarks;

    // 是否删除
    private int isDel;

    // 组织机构id
    private String orgId;

    // 组织机构名称
    @TableField(exist = false)
    private String orgName;


}
