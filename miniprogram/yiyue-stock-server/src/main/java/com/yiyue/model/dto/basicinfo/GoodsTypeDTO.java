package com.yiyue.model.dto.basicinfo;

import com.yiyue.common.util.BasePageDTO;
import lombok.Data;

@Data
public class GoodsTypeDTO extends BasePageDTO{

    // 条件查询字段，需要自己定义添加或者修改
    // 主键
    private String id;
    // 类别名
    private String name;
    // 父级类别id
    private String parentId;
    // 节点类型
    private String isLeaf;
    // 节点图标
    private String icon;
    // 组织机构id
    private String orgId;
    private String orgName;
}