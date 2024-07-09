package com.yiyue.model.dto.basicinfo;

import com.yiyue.common.util.BasePageDTO;
import lombok.Data;

@Data
public class StoreInfoDTO extends BasePageDTO{

    // 条件查询字段，需要自己定义添加或者修改
    // 主键
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
    private String isDel;
    // 组织机构id
    private String orgId;
    private String orgName;
}