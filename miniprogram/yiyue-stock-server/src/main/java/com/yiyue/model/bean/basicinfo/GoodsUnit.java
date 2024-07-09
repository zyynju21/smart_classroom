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
@TableName("t_goods_unit")
public class GoodsUnit implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    // 单位名
    private String name;

    // 组织机构id
    private String orgId;
    @TableField(exist = false)
    private String orgName;


}
