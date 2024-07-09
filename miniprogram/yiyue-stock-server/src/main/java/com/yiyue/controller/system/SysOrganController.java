package com.yiyue.controller.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yiyue.common.aspact.SystemPlatLog;
import com.yiyue.common.result.Result;
import com.yiyue.interceptor.SystemUserContextHolder;
import com.yiyue.model.bean.system.SysOrgan;
import com.yiyue.model.dto.system.SysOrganDTO;
import com.yiyue.model.vo.system.ChooseOrganVO;
import com.yiyue.model.vo.system.LoginUser;
import com.yiyue.service.system.SysOrganService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/system/organ")
public class SysOrganController {

    @Autowired
    private SysOrganService sysOrganService;

    @SystemPlatLog
    @PostMapping(value = "/list",produces = "application/json;charset=utf-8")
    public Result list(@RequestBody SysOrganDTO sysOrganDTO){
        IPage<SysOrgan> organList=sysOrganService.findSysOrganPageList(sysOrganDTO);
        return Result.success(organList);
    }

    /**
     * 公共组件，选择组织机构对话框需要的查询接口，如果组织机构类型是0管理机构，返回可选的组织机构列表，如果是普通机构1，返回该机构的id和机构名称
     * @param sysOrganDTO
     * @return
     */
    @PostMapping(value = "/choose_organ_list",produces = "application/json;charset=utf-8")
    public Result choose_organ_list(@RequestBody SysOrganDTO sysOrganDTO){
        LoginUser loginUser=SystemUserContextHolder.getLoginUser();
        SysOrgan organ=loginUser.getSysOrgan();
        ChooseOrganVO chooseOrganVO=new ChooseOrganVO();
        chooseOrganVO.setOrgType(organ.getOrgType());
        if(organ.getOrgType()==0){
            IPage<SysOrgan> organList=sysOrganService.findSysOrganPageList(sysOrganDTO);
            chooseOrganVO.setSysOrganList(organList);
        }else{
            chooseOrganVO.setOrgId(organ.getOrgId());
            chooseOrganVO.setOrgName(organ.getOrgName());
        }
        return Result.success(chooseOrganVO);
    }

    /**
     * 返回当前登录用户组织机构类型
     * @return
     */
    @PostMapping(value = "/get_login_user_organ_type",produces = "application/json;charset=utf-8")
    public Result get_login_user_organ_type(){
        LoginUser loginUser=SystemUserContextHolder.getLoginUser();
        SysOrgan organ=loginUser.getSysOrgan();
        ChooseOrganVO chooseOrganVO=new ChooseOrganVO();
        chooseOrganVO.setOrgType(loginUser.getSysOrgan().getOrgType());
        if(organ.getOrgType()!=0){
            chooseOrganVO.setOrgId(organ.getOrgId());
            chooseOrganVO.setOrgName(organ.getOrgName());
        }
        return Result.success(chooseOrganVO);
    }



    @SystemPlatLog
    @GetMapping(value = "/get_by_id",produces = "application/json;charset=utf-8")
    public Result get_by_id(@RequestParam String orgId){
        SysOrgan sysOrgan=sysOrganService.findSysOrganByOrgId(orgId);
        return Result.success(sysOrgan);
    }

    @SystemPlatLog
    @PostMapping(value = "/create",produces = "application/json;charset=utf-8")
    public Result create(@RequestBody SysOrgan sysOrgan){
        sysOrganService.insertSysOrgan(sysOrgan);
        return Result.success();
    }

    @SystemPlatLog
    @DeleteMapping(value = "/delete/{orgId}",produces = "application/json;charset=utf-8")
    public Result delete(@PathVariable String orgId){
        sysOrganService.deleteSysOrganById(orgId);
        return Result.success();
    }

    @SystemPlatLog
    @PutMapping(value = "/update",produces = "application/json;charset=utf-8")
    public Result update(@RequestBody SysOrgan sysOrgan){
        sysOrganService.updateSysOrgan(sysOrgan);
        return Result.success();
    }
}
