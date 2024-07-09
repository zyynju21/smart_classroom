package com.yiyue.service.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yiyue.common.util.PageUtil;
import com.yiyue.mapper.system.SystemLogMapper;
import com.yiyue.model.bean.system.SystemLog;
import com.yiyue.model.dto.system.SystemLogPageDTO;
import com.yiyue.model.vo.system.SystemLogPageVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SystemLogService {

    @Autowired
    private SystemLogMapper systemLogMapper;

    /**
     * 分页查询系统日志
     *
     * @param systemLogPageDTO
     * @return
     *
     */
    public IPage<SystemLogPageVO> listSystemLogPage(SystemLogPageDTO systemLogPageDTO){
        QueryWrapper<SystemLogPageDTO> queryWrapper=new QueryWrapper<>();
        if(null!=systemLogPageDTO.getLogType()){
            queryWrapper.eq("sl.log_type",systemLogPageDTO.getLogType());
        }
        if(!StringUtils.isBlank(systemLogPageDTO.getStartDate())){
            queryWrapper.ge("sl.created_gmt",systemLogPageDTO.getStartDate());
        }
        if(!StringUtils.isBlank(systemLogPageDTO.getEndDate())){
            queryWrapper.lt("sl.created_gmt",systemLogPageDTO.getEndDate());
        }
        if(null!=systemLogPageDTO.getStatus()){
            if(systemLogPageDTO.getStatus()==1) {
                queryWrapper.eq("sl.status", 200);
            }else{
                queryWrapper.ne("sl.status", 200);
            }
        }
        if(!StringUtils.isBlank(systemLogPageDTO.getOperator())){
            queryWrapper.like("su.login_name","%"+systemLogPageDTO.getOperator()+"%");
        }
        return systemLogMapper.listSystemLogPage(PageUtil.getPagination(systemLogPageDTO), queryWrapper);
    }

    public void insertSystemLog(SystemLog systemLog) {
        systemLogMapper.insert(systemLog);
    }
}
