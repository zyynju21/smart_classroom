package com.yiyue.controller.basicinfo;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yiyue.common.aspact.SystemPlatLog;
import com.yiyue.common.result.Result;
import com.yiyue.common.vo.ItemVO;
import com.yiyue.interceptor.SystemUserContextHolder;
import com.yiyue.model.bean.system.SysOrgan;
import com.yiyue.model.vo.system.LoginUser;
import com.yiyue.model.vo.system.SysMenuTreeVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.yiyue.service.basicinfo.GoodsTypeService;
import com.yiyue.model.dto.basicinfo.GoodsTypeDTO;
import com.yiyue.model.bean.basicinfo.GoodsType;

import java.util.List;

/**
 * 商品类别
 */
@Slf4j
@RestController
@RequestMapping("/basicinfo/goodsType")
public class GoodsTypeController {

    @Autowired
    private GoodsTypeService goodsTypeService;

    /**
    * 查询列表
    * @param goodsTypeDTO
    * @return
    */
    @SystemPlatLog
    @PostMapping(value = "/list",produces = "application/json;charset=utf-8")
    public Result list(@RequestBody GoodsTypeDTO goodsTypeDTO){
        LoginUser loginUser=SystemUserContextHolder.getLoginUser();
        SysOrgan organ=loginUser.getSysOrgan();
        if(organ.getOrgType()==0){
            goodsTypeDTO.setOrgId(null);
        }else{
            goodsTypeDTO.setOrgId(organ.getOrgId());
        }
        List<ItemVO> goodsTypeTreeList=goodsTypeService.list_parent_goods_type_for_box(goodsTypeDTO);
        log.debug("loginUser：{}查询商品类型数结果：{}",loginUser,goodsTypeTreeList);
        return Result.success(goodsTypeTreeList);
    }

    /**
    * 新增
    * @param goodsType
    * @return
    */
    @SystemPlatLog
    @PostMapping(value = "/create",produces = "application/json;charset=utf-8")
        public Result create(@RequestBody GoodsType goodsType){
        if(StringUtils.isBlank(goodsType.getParentId())){
            goodsType.setParentId("0");
        }
        goodsTypeService.insertGoodsType(goodsType);
        return Result.success();
    }

    /**
    * 修改
    * @param goodsType
    * @return
    */
    @SystemPlatLog
    @PutMapping(value = "/update",produces = "application/json;charset=utf-8")
    public Result update(@RequestBody GoodsType goodsType){
        goodsTypeService.updateGoodsType(goodsType);
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
        goodsTypeService.deleteGoodsTypeById(id);
        return Result.success();
    }

    @SystemPlatLog
    @PostMapping(value = "/delete_list",produces = "application/json;charset=utf-8")
    public Result delete_list(@RequestBody String ids){
        String[] typeIds=JSON.parseObject(ids,String[].class);
        goodsTypeService.deleteGoodsTypeByIds(typeIds);
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
        GoodsType goodsType=goodsTypeService.findGoodsTypeById(id);
        return Result.success(goodsType);
    }




}
