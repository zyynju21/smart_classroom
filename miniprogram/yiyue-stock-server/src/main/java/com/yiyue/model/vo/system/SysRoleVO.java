package com.yiyue.model.vo.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yiyue.common.vo.ItemVO;
import com.yiyue.model.bean.system.SysOrgan;
import com.yiyue.model.bean.system.SysRole;
import lombok.Data;

import java.util.List;

@Data
public class SysRoleVO {

    private List<ItemVO> sysOrganTree;
    private IPage<SysRole> sysRoleList;
    private Integer orgType;

}
