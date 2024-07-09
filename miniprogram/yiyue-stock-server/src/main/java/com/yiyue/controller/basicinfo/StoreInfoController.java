package com.yiyue.controller.basicinfo;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yiyue.common.aspact.SystemPlatLog;
import com.yiyue.common.result.Result;
import com.yiyue.interceptor.SystemUserContextHolder;
import com.yiyue.model.bean.system.SysOrgan;
import com.yiyue.model.vo.system.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.yiyue.service.basicinfo.StoreInfoService;
import com.yiyue.model.dto.basicinfo.StoreInfoDTO;
import com.yiyue.model.bean.basicinfo.StoreInfo;

/**
 * 仓库基本信息
 */
@Slf4j
@RestController
@RequestMapping("/basicinfo/storeInfo")
public class StoreInfoController {

    @Autowired
    private StoreInfoService storeInfoService;

    /**
    * 查询列表
    * @param storeInfoDTO
    * @return
    */
    @SystemPlatLog
    @PostMapping(value = "/list",produces = "application/json;charset=utf-8")
    public Result list(@RequestBody StoreInfoDTO storeInfoDTO){
        LoginUser loginUser=SystemUserContextHolder.getLoginUser();
        SysOrgan organ=loginUser.getSysOrgan();
        if(organ.getOrgType()==0){
            storeInfoDTO.setOrgId(null);
        }else{
            storeInfoDTO.setOrgId(organ.getOrgId());
        }
        IPage<StoreInfo> storeInfoList=storeInfoService.findStoreInfoListPageByParam(storeInfoDTO);
        return Result.success(storeInfoList);
    }

    /**
    * 新增
    * @param storeInfo
    * @return
    */
    @SystemPlatLog
    @PostMapping(value = "/create",produces = "application/json;charset=utf-8")
        public Result create(@RequestBody StoreInfo storeInfo){
        storeInfoService.insertStoreInfo(storeInfo);
        return Result.success();
    }

    /**
    * 修改
    * @param storeInfo
    * @return
    */
    @SystemPlatLog
    @PutMapping(value = "/update",produces = "application/json;charset=utf-8")
    public Result update(@RequestBody StoreInfo storeInfo){
        storeInfoService.updateStoreInfo(storeInfo);
        return Result.success();
    }

    /**
    * 删除操作
    * @param id
    * @return
    */
    @SystemPlatLog
    @DeleteMapping(value = "/delete/{id}",produces = "application/json;charset=utf-8")
    public Result delete(@PathVariable String id){
        storeInfoService.deleteStoreInfoById(id);
        return Result.success();
    }

    /**
    * 根据id查询，返回实体bean
    * @param id
    * @return
    */
    @SystemPlatLog
    @GetMapping(value = "/get_by_id",produces = "application/json;charset=utf-8")
    public Result get_by_id(@RequestParam String id){
        StoreInfo storeInfo=storeInfoService.findStoreInfoById(id);
        return Result.success(storeInfo);
    }

}
