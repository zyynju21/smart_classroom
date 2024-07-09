package com.yiyue.controller.wx;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yiyue.common.aspact.SystemPlatLog;
import com.yiyue.common.result.Result;
import com.yiyue.mapper.wx.OrderMangeMapper;
import com.yiyue.model.bean.wx.SeatStatus;
import com.yiyue.service.wx.SeatStatusService;
import lombok.extern.slf4j.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import com.yiyue.service.wx.OrderMangeService;
import com.yiyue.model.dto.wx.OrderMangeDTO;
import com.yiyue.model.bean.wx.OrderMange;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;
import java.util.List;

/**
*
* author: liyun
* create date: 2022-01-14
*/
@ApiIgnore()
@Api(value="controller",tags={"操作接口"})
@Slf4j
@RestController
@RequestMapping("/wx/orderMange")
public class OrderMangeController {

    @Autowired
    private OrderMangeService orderMangeService;

    @Autowired
    private SeatStatusService seatStatusService;

    @Autowired
    private OrderMangeMapper orderMangeMapper;


    /**
     * 定时任务开启，每隔一分钟，查询所有预约座位是否在15分钟内确认
     */
//    @Scheduled(cron = "0 */9 * * * ?")
    public void cron() {
        orderMangeService.selectOrOrderState();
       /* System.out.println("定时任务开启："+new Date());*/
    }

    /**
    * 查询列表
    * @param orderMangeDTO
    * @return
    */
    @ApiOperation(value="查询列表",notes=" ")
    @SystemPlatLog
    @PostMapping(value = "/list",produces = "application/json;charset=utf-8")
    public Result list(@RequestBody OrderMangeDTO orderMangeDTO){
        IPage<OrderMange> orderMangeList=orderMangeService.findOrderMangeListPageByParam(orderMangeDTO);
        return Result.success(orderMangeList);
    }


    /**
     * 查询当前预约状态
     * @param orderMangeDTO
     * @return
     */
    @ApiOperation(value="查询列表",notes=" ")
    @SystemPlatLog
    @PostMapping(value = "/listPlan",produces = "application/json;charset=utf-8")
    public Result listPlan(@RequestBody OrderMangeDTO orderMangeDTO){
        OrderMange orderMange=orderMangeService.findOrderState(orderMangeDTO);
        if (orderMange==null){
            return Result.fail("未找到数据");
        }
        return Result.success(orderMange);
    }

    /**
    * 新增
    * @param orderMange
    * @return
    */
    @ApiOperation(value="新增",notes=" ")
    @SystemPlatLog
    @PostMapping(value = "/create",produces = "application/json;charset=utf-8")
    public Result create(@RequestBody OrderMange orderMange){
        int seatId= orderMange.getSeatId();
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("seat_id",seatId);
        queryWrapper.eq("order_status",0);
        int id=orderMangeMapper.selectCount(queryWrapper);
        if (id>0){
            return Result.fail("该座位已预定过！");
        }
        int exitsUser=orderMangeService.findUserIdOrOrder(orderMange.getUserId());
        if (exitsUser>0){
            return Result.fail("已预定过！");
        }
        orderMangeService.insertOrderMange(orderMange);
        SeatStatus seatStatus = new SeatStatus();
        seatStatus.setId(orderMange.getSeatId());
        seatStatus.setStatus(1);
        seatStatusService.updateSeatStatus(seatStatus);

        return Result.success();
    }

    /**
    * 修改
    * @param orderMange
    * @return
    */
    @ApiOperation(value="修改",notes=" ")
    @SystemPlatLog
    @PutMapping(value = "/update",produces = "application/json;charset=utf-8")
    public Result update(@RequestBody OrderMange orderMange){
        //取消座位预约
        /*orderMangeService.findSeatState(orderMange.getId());*/
        //修改历史订单
       /* orderMangeService.updateOrderMange(orderMange);*/
        orderMangeService.updateOrderMange(orderMange);
        return Result.success();
    }

    /**
     * 确认订单
     * @param orderMange
     * @return
     */
    @ApiOperation(value="确认订单",notes=" ")
    @SystemPlatLog
    @PutMapping(value = "/update2",produces = "application/json;charset=utf-8")
    public Result update2(@RequestBody OrderMange orderMange){
        orderMangeService.updateOrderMange(orderMange);
        return Result.success();
    }

    /**
     * 取消预约
     * @param orderMange
     * @return
     */
    @ApiOperation(value="取消预约",notes=" ")
    @SystemPlatLog
    @PutMapping(value = "/update3",produces = "application/json;charset=utf-8")
    public Result update3(@RequestBody OrderMange orderMange){
        SeatStatus seatStatus = new SeatStatus();
        seatStatus.setId(orderMange.getSeatId());
        seatStatus.setStatus(0);
        seatStatusService.updateSeatStatus(seatStatus);

        orderMangeService.deleteOrderMangeById(orderMange.getId());
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
        orderMangeService.deleteOrderMangeById(id);
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
        OrderMange orderMange=orderMangeService.findOrderMangeById(id);
        return Result.success(orderMange);
    }

}
