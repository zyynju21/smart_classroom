package com.yiyue.controller.wx;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yiyue.common.aspact.SystemPlatLog;
import com.yiyue.common.result.Result;
import com.yiyue.common.vo.ItemVO;
import lombok.extern.slf4j.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.yiyue.service.wx.SeatStatusService;
import com.yiyue.model.dto.wx.SeatStatusDTO;
import com.yiyue.model.bean.wx.SeatStatus;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
*
* author: liyun
* create date: 2022-01-13
*/
@ApiIgnore()
@Api(value="controller",tags={"操作接口"})
@Slf4j
@RestController
@RequestMapping("/wx/seatStatus")
public class SeatStatusController {

    @Autowired
    private SeatStatusService seatStatusService;

    /**
    * 查询列表
    * @param seatStatusDTO
    * @return
    */
    @ApiOperation(value="查询列表",notes=" ")
    @SystemPlatLog
    @PostMapping(value = "/list",produces = "application/json;charset=utf-8")
    public Result list(@RequestBody SeatStatusDTO seatStatusDTO){
        IPage<SeatStatus> seatStatusList=seatStatusService.findSeatStatusListPageByParam(seatStatusDTO);
        return Result.success(seatStatusList);
    }

    /**
     * 查询图书馆对应的座位列表
     * @param id
     * @return
     */
    @ApiOperation(value="查询列表",notes=" ")
    @SystemPlatLog
    @PostMapping(value = "/getList",produces = "application/json;charset=utf-8")
    public Result getList(@RequestParam int id){
        List<SeatStatus> list=seatStatusService.findSeatListGetLibrary(id);
        int totalYUl=0;
        for (int i = 0; i <list.size() ; i++) {
            if (list.get(i).getStatus()==1){
                totalYUl=totalYUl+1;
            }
        }

        return Result.success(list.size()-totalYUl,list);
    }

    /**
    * 新增
    * @param seatStatus
    * @return
    */
    @ApiOperation(value="新增",notes=" ")
    @SystemPlatLog
    @PostMapping(value = "/create",produces = "application/json;charset=utf-8")
    public Result create(@RequestBody SeatStatus seatStatus){
        seatStatusService.insertSeatStatus(seatStatus);
        return Result.success();
    }

    @PostMapping(value = "/list_seat_name_for_box",produces = "application/json;charset=utf-8")
    public Result listLibrarynameforbox(@RequestBody SeatStatus SeatStatus){
        List<ItemVO> list=seatStatusService.findSeatListName(SeatStatus.getLibraryType());
        return Result.success(list);
    }

    /**
    * 修改
    * @param seatStatus
    * @return
    */
    @ApiOperation(value="修改",notes=" ")
    @SystemPlatLog
    @PutMapping(value = "/update",produces = "application/json;charset=utf-8")
    public Result update(@RequestBody SeatStatus seatStatus){
        seatStatusService.updateSeatStatus(seatStatus);
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
        seatStatusService.deleteSeatStatusById(id);
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
        SeatStatus seatStatus=seatStatusService.findSeatStatusById(id);
        return Result.success(seatStatus);
    }

}
