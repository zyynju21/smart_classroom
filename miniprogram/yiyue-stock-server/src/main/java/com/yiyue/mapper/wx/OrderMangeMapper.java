package com.yiyue.mapper.wx;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;
import com.yiyue.model.bean.wx.OrderMange;

/**
*
* author: liyun
* create date: 2022-01-14
*/

@Component
@MapperScan
public interface OrderMangeMapper extends BaseMapper<OrderMange> {

    @Select("select s1.*,b.user_name,c.library_name library_name2,d.seat_name seat_name2 FROM order_mange s1 LEFT JOIN sys_user b on s1.user_id=b.user_id LEFT JOIN library_type c on s1.library_id=c.id LEFT JOIN seat_status d on s1.seat_id=d.id  ${ew.customSqlSegment}")
    IPage<OrderMange> findOrderMangePageList(Page<OrderMange> pagination, @Param("ew") Wrapper<OrderMange> wrapper);

}
