package com.yiyue.mapper.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiyue.model.bean.system.SystemLog;
import com.yiyue.model.dto.system.SystemLogPageDTO;
import com.yiyue.model.vo.system.SysUserVO;
import com.yiyue.model.vo.system.SystemLogPageVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;

@Component
@MapperScan
public interface SystemLogMapper extends BaseMapper<SystemLog> {

    /**
     * 分页查询系统日志
     *
     * @param page
     * @param queryWrapper
     * @return
     */
    @Select("SELECT sl.system_log_id,sl.controller_name,sl.method_name,sl.paras,sl.result," +
            " sl.log_type,sl.status,sl.response_time,sl.client_ip,sl.client_system,sl.client_browser," +
            " sl.server_ip,sl.created_gmt,su.login_name AS operator" +
            " FROM system_log sl left join sys_user su on sl.created_by=su.user_id and su.user_state=0 ${ew.customSqlSegment}")
    IPage<SystemLogPageVO> listSystemLogPage(Page<SystemLogPageVO> page,  @Param("ew")QueryWrapper<SystemLogPageDTO> queryWrapper);
}
