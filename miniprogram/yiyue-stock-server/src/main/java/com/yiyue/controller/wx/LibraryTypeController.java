package com.yiyue.controller.wx;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yiyue.common.aspact.SystemPlatLog;
import com.yiyue.common.result.Result;
import com.yiyue.common.vo.ItemVO;
import com.yiyue.model.vo.system.SysMenuTreeVO;
import lombok.extern.slf4j.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.yiyue.service.wx.LibraryTypeService;
import com.yiyue.model.dto.wx.LibraryTypeDTO;
import com.yiyue.model.bean.wx.LibraryType;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
*
* author: liyun
* create date: 2022-01-12
*/
@ApiIgnore()
@Api(value="controller",tags={"操作接口"})
@Slf4j
@RestController
@RequestMapping("/wx/libraryType")
public class LibraryTypeController {

    @Autowired
    private LibraryTypeService libraryTypeService;

    /**
    * 查询列表
    * @param libraryTypeDTO
    * @return
    */
    @ApiOperation(value="查询列表",notes=" ")
    @SystemPlatLog
    @PostMapping(value = "/list",produces = "application/json;charset=utf-8")
    public Result list(@RequestBody LibraryTypeDTO libraryTypeDTO){
        IPage<LibraryType> libraryTypeList=libraryTypeService.findLibraryTypeListPageByParam(libraryTypeDTO);
        return Result.success(libraryTypeList);
    }

    @PostMapping(value = "/list_Library_name_for_box",produces = "application/json;charset=utf-8")
    public Result listLibrarynameforbox(){
        List<ItemVO> list=libraryTypeService.findLibraryName();
        return Result.success(list);
    }
    /**
    * 新增
    * @param libraryType
    * @return
    */
    @ApiOperation(value="新增",notes=" ")
    @SystemPlatLog
    @PostMapping(value = "/create",produces = "application/json;charset=utf-8")
    public Result create(@RequestBody LibraryType libraryType){
        libraryTypeService.insertLibraryType(libraryType);
        return Result.success();
    }

    /**
    * 修改
    * @param libraryType
    * @return
    */
    @ApiOperation(value="修改",notes=" ")
    @SystemPlatLog
    @PutMapping(value = "/update",produces = "application/json;charset=utf-8")
    public Result update(@RequestBody LibraryType libraryType){
        libraryTypeService.updateLibraryType(libraryType);
        return Result.success();
    }

    /**
    * 删除操作
    * @param id
    * @return
    */
    @ApiOperation(value="删除",notes=" ")
    @SystemPlatLog
    @DeleteMapping(value = "/delete/{id}",produces = "application/json;charset=utf-8")
    public Result delete(@PathVariable String id){
        libraryTypeService.deleteLibraryTypeById(id);
        return Result.success();
    }

    /**
    * 根据id查询，返回实体bean
    * @param id
    * @return
    */
    @ApiOperation(value="根据id查询",notes=" ")
    @SystemPlatLog
    @GetMapping(value = "/get_by_id",produces = "application/json;charset=utf-8")
    public Result get_by_id(@RequestParam String id){
        LibraryType libraryType=libraryTypeService.findLibraryTypeById(id);
        return Result.success(libraryType);
    }

}
