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
import com.yiyue.service.basicinfo.GoodsUnitService;
import com.yiyue.model.dto.basicinfo.GoodsUnitDTO;
import com.yiyue.model.bean.basicinfo.GoodsUnit;

/**
 * 商品包装单位
 */
@Slf4j
@RestController
@RequestMapping("/basicinfo/goodsUnit")
public class GoodsUnitController {

    @Autowired
    private GoodsUnitService goodsUnitService;

    /**
    * 查询列表
    * @param goodsUnitDTO
    * @return
    */
    @SystemPlatLog
    @PostMapping(value = "/list",produces = "application/json;charset=utf-8")
    public Result list(@RequestBody GoodsUnitDTO goodsUnitDTO){
        LoginUser loginUser=SystemUserContextHolder.getLoginUser();
        SysOrgan organ=loginUser.getSysOrgan();
        if(organ.getOrgType()==0){
            goodsUnitDTO.setOrgId(null);
        }else{
            goodsUnitDTO.setOrgId(organ.getOrgId());
        }
        IPage<GoodsUnit> goodsUnitList=goodsUnitService.findGoodsUnitListPageByParam(goodsUnitDTO);
        return Result.success(goodsUnitList);
    }

    /**
    * 新增
    * @param goodsUnit
    * @return
    */
    @SystemPlatLog
    @PostMapping(value = "/create",produces = "application/json;charset=utf-8")
        public Result create(@RequestBody GoodsUnit goodsUnit){
        goodsUnitService.insertGoodsUnit(goodsUnit);
        return Result.success();
    }

    /**
    * 修改
    * @param goodsUnit
    * @return
    */
    @SystemPlatLog
    @PutMapping(value = "/update",produces = "application/json;charset=utf-8")
    public Result update(@RequestBody GoodsUnit goodsUnit){
        goodsUnitService.updateGoodsUnit(goodsUnit);
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
        goodsUnitService.deleteGoodsUnitById(id);
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
        GoodsUnit goodsUnit=goodsUnitService.findGoodsUnitById(id);
        return Result.success(goodsUnit);
    }

}
