package com.yiyue.mapper.wx;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;
import com.yiyue.model.bean.wx.NoticeAdvise;

/**
* 
* author: liyun
* create date: 2022-01-12
*/

@Component
@MapperScan
public interface NoticeAdviseMapper extends BaseMapper<NoticeAdvise> {

    @Select("select s1.* from notice_advise s1  ${ew.customSqlSegment}")
    IPage<NoticeAdvise> findNoticeAdvisePageList(Page<NoticeAdvise> pagination, @Param("ew") Wrapper<NoticeAdvise> wrapper);

}
