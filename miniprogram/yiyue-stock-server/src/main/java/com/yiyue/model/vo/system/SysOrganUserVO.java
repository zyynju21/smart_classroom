package com.yiyue.model.vo.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yiyue.common.vo.ItemVO;
import com.yiyue.model.bean.system.SysUser;
import lombok.Data;

import java.util.List;

@Data
public class SysOrganUserVO {
    private List<ItemVO> sysOrganTree;
    private IPage<SysUserVO> sysUserList;
    private IPage<SysUser> sysAllUserList;
    private Integer orgType;
}
