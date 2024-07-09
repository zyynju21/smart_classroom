package com.yiyue.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yiyue.model.bean.system.SysUserRole;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;

@Component
@MapperScan
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
}
