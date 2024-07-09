package com.yiyue.mapper.wx;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;
import com.yiyue.model.bean.wx.BookRead;

/**
*
* author: liyun
* create date: 2022-01-19
*/

@Component
@MapperScan
public interface BookReadMapper extends BaseMapper<BookRead> {

    @Select("select s1.*,s2.user_name,s3.book_name from book_read s1 LEFT JOIN sys_user s2 on s2.user_id=s1.user_id LEFT JOIN books_manger s3 on s3.id=s1.library_id ${ew.customSqlSegment}")
    IPage<BookRead> findBookReadPageList(Page<BookRead> pagination, @Param("ew") Wrapper<BookRead> wrapper);

}
