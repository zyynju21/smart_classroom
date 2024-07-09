package com.yiyue.mapper.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiyue.model.bean.system.SysOrgan;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;

@Component
@MapperScan
public interface SysOrganMapper extends BaseMapper<SysOrgan> {

    @Select("select * from sys_organ ${ew.customSqlSegment}")
    IPage<SysOrgan> findSysOrganPageList(Page<Object> pagination,@Param("ew")QueryWrapper<SysOrgan> queryWrapper);
}
