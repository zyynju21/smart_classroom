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
@TableName("t_goods_type")
public class GoodsType implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    // 类别名
    private String name;

    // 父级类别id
    private String parentId;

    // 节点类型
    private int isLeaf;

    // 节点图标
    private String icon;

    // 组织机构id
    private String orgId;
    // 组织机构名称
    @TableField(exist = false)
    private String orgName;

    /**
    @TableField(exist = false)
    private String userName;
    */

}
