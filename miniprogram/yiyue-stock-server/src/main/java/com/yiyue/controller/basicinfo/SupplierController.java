package com.yiyue.controller.basicinfo;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yiyue.common.aspact.SystemPlatLog;
import com.yiyue.common.result.Result;
import com.yiyue.interceptor.SystemUserContextHolder;
import com.yiyue.model.bean.system.SysOrgan;
import com.yiyue.model.vo.basicinfo.SupplierVO;
import com.yiyue.model.vo.system.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.yiyue.service.basicinfo.SupplierService;
import com.yiyue.model.dto.basicinfo.SupplierDTO;
import com.yiyue.model.bean.basicinfo.Supplier;

/**
* 供应商表
* author: liyun
* create date: 2021-10-13
*/
@Slf4j
@RestController
@RequestMapping("/basicinfo/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    /**
    * 查询列表
    * @param supplierDTO
    * @return
    */
    @SystemPlatLog
    @PostMapping(value = "/list",produces = "application/json;charset=utf-8")
    public Result list(@RequestBody SupplierDTO supplierDTO){
        LoginUser loginUser=SystemUserContextHolder.getLoginUser();
        SysOrgan organ=loginUser.getSysOrgan();
        if(organ.getOrgType()==0){
            supplierDTO.setOrgId(null);
        }else{
            supplierDTO.setOrgId(organ.getOrgId());
        }
        IPage<SupplierVO> supplierList=supplierService.findSupplierListPageByParam(supplierDTO);
        return Result.success(supplierList);
    }

    /**
    * 新增
    * @param supplier
    * @return
    */
    @SystemPlatLog
    @PostMapping(value = "/create",produces = "application/json;charset=utf-8")
        public Result create(@RequestBody Supplier supplier){
        supplierService.insertSupplier(supplier);
        return Result.success();
    }

    /**
    * 修改
    * @param supplier
    * @return
    */
    @SystemPlatLog
    @PutMapping(value = "/update",produces = "application/json;charset=utf-8")
    public Result update(@RequestBody Supplier supplier){
        supplierService.updateSupplier(supplier);
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
        supplierService.deleteSupplierById(id);
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
        Supplier supplier=supplierService.findSupplierById(id);
        return Result.success(supplier);
    }

}
