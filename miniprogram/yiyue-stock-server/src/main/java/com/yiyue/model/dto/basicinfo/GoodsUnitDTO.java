package com.yiyue.model.dto.basicinfo;

import com.yiyue.common.util.BasePageDTO;
import lombok.Data;

@Data
public class GoodsUnitDTO extends BasePageDTO{

    // 条件查询字段，需要自己定义添加或者修改
    // 主键
    private String id;
    // 单位名
    private String name;
    // 组织机构id
    private String orgId;
    // 组织机构名称
    private String orgName;
}