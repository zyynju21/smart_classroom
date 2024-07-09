package com.yiyue.mapper.system;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiyue.model.bean.system.SysUser;
import com.yiyue.model.vo.system.SysUserVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;

@Component
@MapperScan
public interface SysUserMapper extends BaseMapper<SysUser> {

    @Select("select su.user_id,su.user_name,su.login_name,su.head_img_url,su.birth," +
            " su.user_sex,CONCAT(su.province,' ',su.city,' ',su.county,' ',su.address) address,su.phone," +
            " so.org_name " +
            " from sys_user su inner JOIN sys_organ so on su.org_id= so.org_id ${ew.customSqlSegment}")
    IPage<SysUserVO> findSysUserListByParam(Page<Object> pagination, @Param("ew")QueryWrapper<SysUserVO> queryWrapper);
}
