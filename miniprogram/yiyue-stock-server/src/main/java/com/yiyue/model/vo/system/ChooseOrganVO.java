package com.yiyue.model.vo.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yiyue.model.bean.system.SysOrgan;
import lombok.Data;


@Data
public class ChooseOrganVO {

    private String orgId;
    private String orgName;
    private Integer orgType;
    private IPage<SysOrgan> sysOrganList;
}
