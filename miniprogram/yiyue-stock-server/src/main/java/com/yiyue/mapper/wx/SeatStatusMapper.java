package com.yiyue.mapper.wx;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;
import com.yiyue.model.bean.wx.SeatStatus;

/**
*
* author: liyun
* create date: 2022-01-13
*/

@Component
@MapperScan
public interface SeatStatusMapper extends BaseMapper<SeatStatus> {

    @Select("select s1.*,s2.library_name from seat_status s1 LEFT JOIN library_type s2 ON s1.library_type=s2.id ${ew.customSqlSegment}")
    IPage<SeatStatus> findSeatStatusPageList(Page<SeatStatus> pagination, @Param("ew") Wrapper<SeatStatus> wrapper);

}
