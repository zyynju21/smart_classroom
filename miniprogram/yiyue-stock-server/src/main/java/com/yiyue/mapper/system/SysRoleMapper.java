package com.yiyue.mapper.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiyue.model.bean.system.SysRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@MapperScan
public interface SysRoleMapper extends BaseMapper<SysRole> {

    @Select("SELECT s1.* from sys_role s1,sys_user_role s2,sys_user s3 where s1.role_id=s2.role_id and s2.user_id=s3.user_id and s3.user_id=#{userId}")
    Set<SysRole> findSysRolesByUserId(String userId);

    @Select("select sr.*,so.org_name" +
            " from sys_role sr inner join sys_organ so on sr.org_id=so.org_id ${ew.customSqlSegment}")
    IPage<SysRole> selectSysRoleListPageByParam(Page<Object> pagination,@Param("ew") QueryWrapper<SysRole> queryWrapper);

    @Select("select sr.*,so.org_name" +
            " from sys_role sr inner join sys_organ so on sr.org_id=so.org_id ${ew.customSqlSegment}")
    List<SysRole> selectSysRoleListByParam(@Param("ew") QueryWrapper<SysRole> queryWrapper);
}
