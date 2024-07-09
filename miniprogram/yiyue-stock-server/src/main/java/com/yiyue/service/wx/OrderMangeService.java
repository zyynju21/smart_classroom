package com.yiyue.service.wx;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yiyue.common.util.PageUtil;
import com.yiyue.mapper.wx.SeatStatusMapper;
import com.yiyue.model.bean.wx.SeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import com.yiyue.model.bean.wx.OrderMange;
import com.yiyue.model.dto.wx.OrderMangeDTO;
import com.yiyue.mapper.wx.OrderMangeMapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
*
* author: liyun
* create date: 2022-01-14
*/

@Service
@Transactional
public class OrderMangeService {

    @Autowired
    private OrderMangeMapper orderMangeMapper;

    @Autowired
    private SeatStatusMapper seatStatusMapper;

    public IPage<OrderMange> findOrderMangeListPageByParam(OrderMangeDTO orderMangeDTO) {
        // 从dto对象中获得查询条件，添加到queryWrapper对象中, 查询条件还需要视情况自行修改
        QueryWrapper<OrderMange> queryWrapper=getQueryWrapper(orderMangeDTO);

        IPage<OrderMange> orderMangeList=orderMangeMapper.findOrderMangePageList(PageUtil.getPagination(orderMangeDTO),queryWrapper);
        return orderMangeList;
    }

    private QueryWrapper getQueryWrapper(OrderMangeDTO orderMangeDTO){
        QueryWrapper<OrderMange> queryWrapper=new QueryWrapper<>();
        // 序号
        if(!StringUtils.isBlank(orderMangeDTO.getId())){
            queryWrapper.eq("s1.id",orderMangeDTO.getId());
        }
        // 订单编号
        if(!StringUtils.isBlank(orderMangeDTO.getOrderId())){
//            queryWrapper.eq("s1.order_id",orderMangeDTO.getOrderId());
            queryWrapper.eq("s1.id",orderMangeDTO.getOrderId());
        }
        // 用户
        if(!StringUtils.isBlank(orderMangeDTO.getUserId())){
            queryWrapper.eq("s1.user_id",orderMangeDTO.getUserId());
        }
        // 图书馆id
        if(!StringUtils.isBlank(orderMangeDTO.getLibraryId())){
            queryWrapper.eq("s1.library_id",orderMangeDTO.getLibraryId());
        }
        // 图书馆
        if(!StringUtils.isBlank(orderMangeDTO.getLibraryName())){
            queryWrapper.eq("s1.library_name",orderMangeDTO.getLibraryName());
        }
        // 座位id
        if(!StringUtils.isBlank(orderMangeDTO.getSeatId())){
            queryWrapper.eq("s1.seat_id",orderMangeDTO.getSeatId());
        }
        // 座位
        if(!StringUtils.isBlank(orderMangeDTO.getSeatName())){
            queryWrapper.eq("s1.seat_name",orderMangeDTO.getSeatName());
        }
        // 订单状态
        if(!StringUtils.isBlank(orderMangeDTO.getOrderStatus())){
            queryWrapper.eq("s1.order_status",orderMangeDTO.getOrderStatus());
        }
        // 预约时间
        if(!StringUtils.isBlank(orderMangeDTO.getPlanTime())){
            queryWrapper.eq("s1.plan_time",orderMangeDTO.getPlanTime());
        }
        // 创建时间
        if(!StringUtils.isBlank(orderMangeDTO.getCreateDate())){
            queryWrapper.eq("create_date",orderMangeDTO.getCreateDate());
        }
        return queryWrapper;
    }

    public void insertOrderMange(OrderMange orderMange) {
        orderMangeMapper.insert(orderMange);
    }

    public void updateOrderMange(OrderMange orderMange) {
        this.orderMangeMapper.updateById(orderMange);
    }

    public void deleteOrderMangeById(String id) {
        this.orderMangeMapper.deleteById(id);
    }

    public OrderMange findOrderMangeById(String id) {
        return orderMangeMapper.selectById(id);
    }

    public int findUserIdOrOrder(String userId) {
        QueryWrapper<OrderMange> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("order_status",0);
        return orderMangeMapper.selectCount(queryWrapper);

    }

    public OrderMange findOrderState(OrderMangeDTO orderMangeDTO) {
        QueryWrapper<OrderMange> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",orderMangeDTO.getUserId());
        queryWrapper.eq("order_status",0);
        if (orderMangeMapper.selectList(queryWrapper).size()==0){
            return null;
        }
        return orderMangeMapper.selectList(queryWrapper).get(0);
    }

    public void findSeatState(String id) {
        OrderMange orderMange = orderMangeMapper.selectById(id);
        int seatId = orderMange.getSeatId();
        SeatStatus seatStatus = new SeatStatus();
        seatStatus.setId(seatId);
        seatStatus.setStatus(0);
        seatStatusMapper.updateById(seatStatus);
    }

    public void selectOrOrderState() {
        //取消座位预约findSeatState
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       /* QueryWrapper<OrderMange> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_status","0");*/
        List<OrderMange> list = orderMangeMapper.selectList(null);
        if (list.size()==0){
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            Date planDate = new Date(list.get(i).getPlanTime().getTime() + 900000);
            if (planDate.getTime()<new Date().getTime()){
                orderMangeMapper.deleteById(list.get(i).getId());
                SeatStatus seatStatus = new SeatStatus();
                seatStatus.setId(list.get(i).getSeatId());
                seatStatus.setStatus(0);
                seatStatusMapper.updateById(seatStatus);
            }
        }
        //时间到时的确认时间
        QueryWrapper<OrderMange> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("order_status","1");
        List<OrderMange> list2 = orderMangeMapper.selectList(queryWrapper2);
        if (list.size()==0){
            return;
        }
        for (int i = 0; i < list2.size(); i++) {
            if (list2.get(i).getEndTime().getTime()<new Date().getTime()){
                orderMangeMapper.deleteById(list.get(i).getId());
                SeatStatus seatStatus = new SeatStatus();
                seatStatus.setId(list.get(i).getSeatId());
                seatStatus.setStatus(0);
                seatStatusMapper.updateById(seatStatus);
            }
        }

    }
}
