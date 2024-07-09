package com.yiyue.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yiyue.model.bean.system.SysRoleMenu;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;

@Component
@MapperScan
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {
}
