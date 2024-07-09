package com.yiyue.service.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yiyue.common.enums.UserTypeEnum;
import com.yiyue.common.util.PageUtil;
import com.yiyue.mapper.system.SysOrganMapper;
import com.yiyue.model.bean.system.SysOrgan;
import com.yiyue.model.dto.system.SysOrganDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SysOrganService {

    @Autowired
    private SysOrganMapper sysOrganMapper;

    public IPage<SysOrgan> findSysOrganPageList(SysOrganDTO sysOrganDTO) {
        QueryWrapper<SysOrgan> queryWrapper=new QueryWrapper<>();
        if(!StringUtils.isBlank(sysOrganDTO.getOrgName())){
            queryWrapper.like("org_name","%"+sysOrganDTO.getOrgName()+"%");
        }
        if(!StringUtils.isBlank(sysOrganDTO.getOrgPerson())){
            queryWrapper.like("org_person","%"+sysOrganDTO.getOrgPerson()+"%");
        }
        if(!StringUtils.isBlank(sysOrganDTO.getOrgPhone())){
            queryWrapper.like("org_phone","%"+sysOrganDTO.getOrgPhone()+"%");
        }
        queryWrapper.eq("org_state",0);
        IPage<SysOrgan> sysOrganList=sysOrganMapper.findSysOrganPageList(PageUtil.getPagination(sysOrganDTO),queryWrapper);
        return sysOrganList;
    }

    public SysOrgan findSysOrganByOrgId(String orgId) {
        return sysOrganMapper.selectById(orgId);
    }

    public void insertSysOrgan(SysOrgan sysOrgan) {
        sysOrgan.setOrgState(0);
        sysOrgan.setCreateDate(new Date());
        sysOrgan.setOrgType(UserTypeEnum.ORGAN.getSysUserTypeId());
        sysOrganMapper.insert(sysOrgan);
    }

    public void deleteSysOrganById(String orgId) {
        SysOrgan sysOrgan=this.sysOrganMapper.selectById(orgId);
        sysOrgan.setOrgState(-1);
        this.sysOrganMapper.updateById(sysOrgan);
    }

    public void updateSysOrgan(SysOrgan sysOrgan) {
        this.sysOrganMapper.updateById(sysOrgan);
    }

    public List<SysOrgan> findSysOrganList(String orgId) {
        QueryWrapper queryWrapper=new QueryWrapper();
        if(!StringUtils.isBlank(orgId)) {
            queryWrapper.eq("org_id", orgId);
        }
        queryWrapper.ne("org_state",-1);
        return this.sysOrganMapper.selectList(queryWrapper);
    }
}
